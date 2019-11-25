package migrator.app.domain.index.service;

import java.util.ArrayList;
import java.util.List;

import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.SimpleIndexProperty;

public class IndexBuilder {
    protected String id;
    protected String tableId;
    protected IndexProperty original;
    protected IndexProperty change;
    protected ChangeCommand changeCommand;

    public IndexBuilder() {
        this.id = "1";
        this.tableId = "1";
        this.original = new SimpleIndexProperty("1", "", new ArrayList<>());
        this.change = new SimpleIndexProperty("1", "", new ArrayList<>());
        this.changeCommand = new ChangeCommand("1", "");
    }

    public IndexBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public IndexBuilder withTableId(String tableId) {
        this.tableId = tableId;
        return this;
    }

    public IndexBuilder withOriginalName(String name) {
        this.original.nameProperty().set(name);
        return this;
    }

    public IndexBuilder withChangeName(String name) {
        this.change.nameProperty().set(name);
        return this;
    }

    public IndexBuilder withChange(String change) {
        this.changeCommand.setType(change);
        return this;
    }

    public IndexBuilder withOriginalColumns(List<ColumnProperty> columns) {
        this.original.columnsProperty().setAll(columns);
        return this;
    }

    public IndexBuilder withChangeColumns(List<ColumnProperty> columns) {
        this.change.columnsProperty().setAll(columns);
        return this;
    }

    public Index build() {
        return new Index(
            this.id,
            this.tableId,
            this.original,
            this.change,
            this.changeCommand
        );
    }
}