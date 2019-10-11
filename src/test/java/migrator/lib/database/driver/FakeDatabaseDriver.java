package migrator.lib.database.driver;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;

public class FakeDatabaseDriver implements DatabaseDriver {
    protected ObservableList<List<String>> columns;
    protected ObservableList<String> databases;
    protected ObservableList<List<String>> indexes;
    protected ObservableList<String> tables;

    public FakeDatabaseDriver(List<String> databases, List<String> tables, List<List<String>> columns, List<List<String>> indexes) {
        this.databases = FXCollections.observableArrayList(databases);
        this.tables = FXCollections.observableArrayList(tables);
        this.columns = FXCollections.observableArrayList(columns);
        this.indexes = FXCollections.observableArrayList(indexes);
    }

    @Override
    public void connect() {}

    @Override
    public void disconnect() {}

    @Override
    public ObservableList<List<String>> getColumns(String tableName) {
        return this.columns;
    }

    @Override
    public ObservableList<String> getDatabases() {
        return this.databases;
    }

    @Override
    public ObservableList<List<String>> getIndexes(String tableName) {
        return this.indexes;
    }

    @Override
    public ObservableList<String> getTables() {
        return this.tables;
    }

    @Override
    public Boolean isConnected() {
        return true;
    }

    @Override
    public String getError() {
        return null;
    }
}