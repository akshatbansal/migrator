package migrator.app.domain.table.service;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;

public class TableBuilder {
    protected String tableId;
    protected String projectId;
    protected TableProperty original;
    protected TableProperty change;
    protected ChangeCommand changeCommand;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;

    public TableBuilder() {
        this.tableId = "1";
        this.projectId = "1";
        this.original = new SimpleTableProperty("1", "");
        this.change = new SimpleTableProperty("2", "");
        this.changeCommand = new ChangeCommand("1", "");
        this.columns = FXCollections.observableArrayList();
        this.indexes = FXCollections.observableArrayList();
    }

    public TableBuilder withChange(String change) {
        this.changeCommand.setType(change);
        return this;
    }

    public TableBuilder withTableId(String id) {
        this.tableId = id;
        return this;
    }

    public TableBuilder withProjectId(String id) {
        this.projectId = id;
        return this;
    }

    public TableBuilder withChangeName(String name) {
        this.change.nameProperty().set(name);
        return this;
    }

    public TableBuilder withOriginalName(String name) {
        this.original.nameProperty().set(name);
        return this;
    }

    public TableBuilder withColumns(Column ... columns) {
        this.columns.setAll(columns);
        return this;
    }

    public TableBuilder withIndexes(Index ... indexes) {
        this.indexes.setAll(indexes);
        return this;
    }

    public Table build() {
        return new Table(
            this.tableId,
            this.projectId,
            this.original,
            this.change,
            this.changeCommand,
            this.columns,
            this.indexes
        );
    } 
}