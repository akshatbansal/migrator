package migrator.ext.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;

public class MysqlDatabaseDriver implements DatabaseDriver {
    protected String url;
    protected String user;
    protected String password;
    protected Connection mysql;
    protected ObservableList<String> databases;
    protected ObservableList<String> tables;
    protected ObservableList<List<String>> columns;
    protected ObservableList<List<String>> indexes;

    public MysqlDatabaseDriver(String url, String user, String password) {        
        this.url = url;
        this.user = user;
        this.password = password;
        this.connect();
        this.databases = FXCollections.observableArrayList();
        this.tables = FXCollections.observableArrayList();
        this.columns = FXCollections.observableArrayList();
        this.indexes = FXCollections.observableArrayList();
    }

    @Override
    public void connect() {
        try {
            this.mysql = DriverManager.getConnection("jdbc:" + this.url, this.user, this.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.mysql = null;
        }
    }

    public void disconnect() {
        if (this.mysql == null) {
            return;
        }
        try {
            this.mysql.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void refreshDatabases() throws SQLException {
        if (this.mysql == null) {
            this.databases.clear();
            return;
        }
        List<String> currentDatabases = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "SHOW DATABASES";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            currentDatabases.add(rs.getString(1));
        }
        this.databases.setAll(currentDatabases);
    }

    public ObservableList<String> getDatabases() {
        try {
            this.refreshDatabases();
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.databases.clear();
        }
        
        return this.databases;
    }

    protected void refreshTables() throws SQLException {
        if (this.mysql == null) {
            this.databases.clear();
            return;
        }

        List<String> currentTables = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "SHOW TABLES";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            currentTables.add(rs.getString(1));
        }
        this.tables.setAll(currentTables);
    }

    public ObservableList<String> getTables() {
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

        List<List<String>> currentColumns = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "DESCRIBE " + tableName;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            List<String> values = new LinkedList<>();
            values.add(rs.getString(1));
            values.add(rs.getString(2));
            values.add(rs.getString(3));
            values.add(rs.getString(5));
            currentColumns.add(values);
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

        List<List<String>> currentIndexes = new LinkedList<>();
        Statement statement = this.mysql.createStatement();
        String sql = "SHOW INDEX from " + tableName;
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            List<String> values = new LinkedList<>();
            values.add(rs.getString(3));
            values.add(rs.getString(5));
            currentIndexes.add(values);
        }
        this.indexes.setAll(currentIndexes);
    }

    @Override
    public ObservableList<List<String>> getColumns(String tableName) {
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
    public ObservableList<List<String>> getIndexes(String tableName) {
        try {
            this.refreshIndexes(tableName);
        } catch (SQLException ex) {
            ex.printStackTrace();
            this.indexes.clear();
        }
        
        return this.indexes;
    }
}