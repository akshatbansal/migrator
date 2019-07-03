package migrator.database.service;

import java.util.List;

import javafx.collections.ObservableList;

public interface ServerConnection {
    public void connect();
    public void disconnect();
    public ObservableList<String> getDatabases();
    public ObservableList<String> getTables();
    public ObservableList<List<String>> getColumns(String tableName);
    public ObservableList<List<String>> getIndexes(String tableName);
}