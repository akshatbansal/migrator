package migrator.ext.postgresql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;
import migrator.lib.logger.Logger;

public class PostgresqlDatabaseDriver implements DatabaseDriver {
    protected String url;
    protected String user;
    protected String password;
    protected TableFactory tableFactory;
    protected ColumnFactory columnFactory;
    protected IndexFactory indexFactory;
    protected Logger logger;

    protected Connection mysql;
    protected ObservableList<Table> tables;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;
    protected String error;

    public PostgresqlDatabaseDriver(
        TableFactory tableFactory,
        ColumnFactory columnFactory,
        IndexFactory indexFactory,
        Logger logger,
        String url,
        String user,
        String password
    ) {
        this.tableFactory = tableFactory;
        this.columnFactory = columnFactory;
        this.indexFactory = indexFactory;
        this.logger = logger;
        this.url = url;
        this.user = user;
        this.password = password;

        this.tables = FXCollections.observableArrayList();
        this.columns = FXCollections.observableArrayList();
        this.indexes = FXCollections.observableArrayList();
    }

    @Override
    public Boolean isConnected() {
        return this.mysql != null;
    }

    @Override
    public void connect() {
        try {
            this.mysql = DriverManager.getConnection("jdbc:" + this.url, this.user, this.password);
            this.error = null;
        } catch (SQLException ex) {
            this.mysql = null;
            this.error = "Cannot connect to " + this.url + ". Reason: " + ex.getMessage();
            this.logger.info(this.error);
        }
    }

    public void disconnect() {
        if (this.mysql == null) {
            return;
        }
        try {
            this.mysql.close();
            this.mysql = null;
        } catch (SQLException ex) {
            this.logger.error(ex);
        }
    }

    protected void refreshTables() throws SQLException {
        if (this.mysql == null) {
            this.tables.clear();
            return;
        }

        List<Table> currentTables = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "SELECT * FROM pg_catalog.pg_tables WHERE schemaname != 'pg_catalog' AND schemaname != 'information_schema';";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            currentTables.add(
                this.tableFactory.createNotChanged("NULL", rs.getString(2))
            );
        }
        this.tables.setAll(currentTables);
    }

    public ObservableList<Table> getTables() {
        try {
            this.refreshTables();
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.tables.clear();
        }

        return this.tables;
    }

    protected void refreshColumns(String tableName) throws SQLException {
        if (this.mysql == null) {
            this.columns.clear();
            return;
        }

        if (!this.tableExists(tableName)) {
            this.columns.clear();
            return;
        }

        List<Column> currentColumns = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "SELECT column_name, column_default, is_nullable, data_type, character_maximum_length, numeric_precision, numeric_scale FROM information_schema.COLUMNS WHERE TABLE_NAME = '" + tableName + "'";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String defaultValue = rs.getString(2);
            if (defaultValue == null) {
                defaultValue = "";
            }
            String dbFormat = rs.getString(4);
            Column column = this.columnFactory.createNotChanged(
                "NULL",
                rs.getString(1),
                this.getFormat(dbFormat),
                defaultValue,
                rs.getString(3) == "YES" ? true : false,
                this.getLength(rs.getString(5), rs.getString(6)),
                false,
                this.getPrecision(rs.getString(7)),
                false
            );
            currentColumns.add(column);
        }
        this.columns.setAll(currentColumns);
    }

    protected void refreshIndexes(String tableName) throws SQLException {
        if (this.mysql == null) {
            this.indexes.clear();
            return;
        }

        if (!this.tableExists(tableName)) {
            this.indexes.clear();
            return;
        }

        Statement statement = this.mysql.createStatement();
        String sql = "select i.relname as index_name, a.attname as column_name from pg_class t, pg_class i, pg_index ix, pg_attribute a where t.oid = ix.indrelid and i.oid = ix.indexrelid and a.attrelid = t.oid and a.attnum = ANY(ix.indkey) and t.relkind = 'r' and t.relname like '" + tableName + "' order by t.relname, i.relname;";
        ResultSet rs = statement.executeQuery(sql);
        Map<String, List<String>> indexColumnsMap = new LinkedHashMap<>();
        while (rs.next()) {
            String indexName = rs.getString(1);
            if (!indexColumnsMap.containsKey(indexName)) {
                indexColumnsMap.put(indexName, new ArrayList<>());
            }
            indexColumnsMap.get(indexName).add(rs.getString(2));
        }
        List<Index> currentIndexes = new LinkedList<>();
        Iterator<Entry<String, List<String>>> entryIterator = indexColumnsMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, List<String>> entry = entryIterator.next();
            currentIndexes.add(
                this.indexFactory.createNotChanged(
                    "NULL",
                    entry.getKey(), 
                    entry.getValue()
                )
            );
        }
        this.indexes.setAll(currentIndexes);
    }

    @Override
    public ObservableList<Column> getColumns(String tableName) {
        try {
            this.refreshColumns(tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.columns.clear();
        }
        
        return this.columns;
    }

    protected boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = this.mysql.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName, new String[] {"TABLE"});
        return result.next();
    }

    @Override
    public ObservableList<Index> getIndexes(String tableName) {
        try {
            this.refreshIndexes(tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.indexes.clear();
        }
        
        return this.indexes;
    }

    @Override
    public String getError() {
        return this.error;
    }

    protected String getFormat(String dbFormat) {
        if (dbFormat.startsWith("varchar")) {
            return "string";
        }
        if (dbFormat.startsWith("int")) {
            return "integer";
        }
        if (dbFormat.startsWith("tinyint")) {
            return "boolean";
        }
        if (dbFormat.startsWith("char")) {
            return "char";
        }
        if (dbFormat.startsWith("double")) {
            return "decimal";
        }
        return dbFormat;
    }

    protected String getLength(String stringLengthInString, String intLengthInString) {
        if (stringLengthInString == null && intLengthInString == null) {
            return "";
        }
        int stringLength = 0;
        if (stringLengthInString != null) {
            stringLength = Integer.parseInt(stringLengthInString);
        }
        int intLength = 0;
        if (intLengthInString != null) {
            intLength = Integer.parseInt(intLengthInString);
        }
        return Integer.toString(Math.max(stringLength, intLength));
    }

    protected String getPrecision(String precision) {
        if (precision == null) {
            return "";
        }
        return Integer.toString(Math.max(Integer.parseInt(precision), 0));
    }
}