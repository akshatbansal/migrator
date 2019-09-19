package migrator.migration;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TableChange {
    protected String name;
    protected ChangeCommand command;
    protected Map<String, Object> arguments;
    protected ObservableList<ColumnChange> columns;
    protected ObservableList<IndexChange> indexes;

    public TableChange(String name) {
        this(name, new ChangeCommand(ChangeCommand.UPDATE));
    }

    public TableChange(String name, ChangeCommand command) {
        this(name, command, new ArrayList<>());
    }

    public TableChange(String name, ChangeCommand command, List<ColumnChange> columns) {
        this(name, command, columns, new ArrayList<>());
    }

    public TableChange(String name, ChangeCommand command, List<ColumnChange> columns, List<IndexChange> indexes) {
        this.name = name;
        this.command = command;
        this.columns = FXCollections.observableArrayList(columns);
        this.indexes = FXCollections.observableArrayList(indexes);
    }

    public String getName() {
        return this.name;
    }

    public ObservableList<ColumnChange> getColumnsChanges() {
        return this.columns;
    }

    public ObservableList<IndexChange> getIndexesChanges() {
        return this.indexes;
    }

    public void addColumnChange(ColumnChange columnChange) {
        this.columns.add(columnChange);
    }

    public void addIndexChange(IndexChange indexChange) {
        this.indexes.add(indexChange);
    }

    public ChangeCommand getCommand() {
        return this.command;
    }

    public Boolean hasCommand() {
        return this.command != null && !this.command.isType(null);
    }
}