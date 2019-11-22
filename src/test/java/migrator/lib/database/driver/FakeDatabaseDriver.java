package migrator.lib.database.driver;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;

public class FakeDatabaseDriver implements DatabaseDriver {
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;
    protected ObservableList<Table> tables;

    public FakeDatabaseDriver(List<Table> tables, List<Column> columns, List<Index> indexes) {
        this.tables = FXCollections.observableArrayList(tables);
        this.columns = FXCollections.observableArrayList(columns);
        this.indexes = FXCollections.observableArrayList(indexes);
    }

    @Override
    public void connect() {}

    @Override
    public void disconnect() {}

    @Override
    public ObservableList<Column> getColumns(Table table) {
        return this.columns;
    }

    @Override
    public ObservableList<Index> getIndexes(Table table) {
        return this.indexes;
    }

    @Override
    public ObservableList<Table> getTables() {
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