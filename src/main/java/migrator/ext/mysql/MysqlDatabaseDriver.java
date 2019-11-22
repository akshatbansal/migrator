package migrator.ext.mysql;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.logger.Logger;

public class MysqlDatabaseDriver implements DatabaseDriver {
    protected String url;
    protected String user;
    protected String password;
    protected TableFactory tableFactory;
    protected ColumnFactory columnFactory;
    protected IndexFactory indexFactory;
    protected ColumnRepository columnRepository;
    protected Logger logger;

    protected Connection mysql;
    protected ObservableList<Table> tables;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;
    protected String error;

    public MysqlDatabaseDriver(
        TableFactory tableFactory,
        ColumnFactory columnFactory,
        IndexFactory indexFactory,
        ColumnRepository columnRepository,
        Logger logger,
        String url,
        String user,
        String password
    ) {
        this.tableFactory = tableFactory;
        this.columnFactory = columnFactory;
        this.indexFactory = indexFactory;
        this.columnRepository = columnRepository;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.mysql = DriverManager.getConnection("jdbc:" + this.url, this.user, this.password);
            this.error = null;
        } catch (SQLException ex) {
            this.mysql = null;
            this.error = "Cannot connect to " + this.url + ". Reason: " + ex.getMessage();
            this.logger.info(this.error);
        } catch (ClassNotFoundException ex) {
            this.mysql = null;
            this.error = "Cannot connect to " + this.url + ". Reason: " + ex.getMessage();
            this.logger.error(this.error);
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
        String sql = "SHOW TABLES";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            currentTables.add(
                this.tableFactory.createNotChanged("NULL", rs.getString(1))
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

    protected void refreshColumns(Table table) throws SQLException {
        if (this.mysql == null) {
            this.columns.clear();
            return;
        }

        String tableName = table.getOriginalName();
        if (!this.tableExists(tableName)) {
            this.columns.clear();
            return;
        }

        List<Column> currentColumns = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "DESCRIBE " + tableName;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String defaultValue = rs.getString(5);
            if (defaultValue == null) {
                defaultValue = "";
            }
            String dbFormat = rs.getString(2);
            Column column = this.columnFactory.createNotChanged(
                table.getUniqueKey(),
                rs.getString(1),
                this.getFormat(dbFormat),
                defaultValue,
                rs.getString(3) == "YES" ? true : false,
                this.getLength(dbFormat),
                this.getSign(dbFormat),
                this.getPrecision(dbFormat),
                false
            );
            currentColumns.add(column);
        }
        this.columns.setAll(currentColumns);
    }

    protected void refreshIndexes(Table table) throws SQLException {
        if (this.mysql == null) {
            this.indexes.clear();
            return;
        }

        String tableName = table.getOriginalName();
        if (!this.tableExists(tableName)) {
            this.indexes.clear();
            return;
        }

        Statement statement = this.mysql.createStatement();
        String sql = "SHOW INDEX from " + tableName;
        ResultSet rs = statement.executeQuery(sql);
        Map<String, List<ColumnProperty>> indexColumnsMap = new LinkedHashMap<>();
        while (rs.next()) {
            String indexName = rs.getString(3);
            if (!indexColumnsMap.containsKey(indexName)) {
                indexColumnsMap.put(indexName, new ArrayList<>());
            }
            indexColumnsMap.get(indexName).add(
                this.getColumnPropertyByName(rs.getString(5), table.getUniqueKey())
            );
        }
        List<Index> currentIndexes = new LinkedList<>();
        Iterator<Entry<String, List<ColumnProperty>>> entryIterator = indexColumnsMap.entrySet().iterator();
        while (entryIterator.hasNext()) {
            Entry<String, List<ColumnProperty>> entry = entryIterator.next();
            currentIndexes.add(
                this.indexFactory.createNotChanged(
                    table.getUniqueKey(),
                    entry.getKey(), 
                    entry.getValue()
                )
            );
        }
        this.indexes.setAll(currentIndexes);
    }

    @Override
    public ObservableList<Column> getColumns(Table table) {
        try {
            this.refreshColumns(table);
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
    public ObservableList<Index> getIndexes(Table table) {
        try {
            this.refreshIndexes(table);
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

    protected String getLength(String dbFormat) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(dbFormat);
        if (m.find()) {
            return m.group(0);
        }
        
        return "";
    }

    protected String getPrecision(String dbFormat) {
        Pattern p = Pattern.compile(",(\\d+)");
        Matcher m = p.matcher(dbFormat);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }

    protected Boolean getSign(String dbFormat) {
        return !dbFormat.endsWith("unsigned");
    }

    protected ColumnProperty getColumnPropertyByName(String columnName, String tableId) {
        for (Column column : this.columnRepository.findByTable(tableId)) {
            if (column.getOriginal().getName().equals(columnName)) {
                return column.getChange();
            }
        }
        return null;
    }
}