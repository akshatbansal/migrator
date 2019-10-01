package migrator.migration;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SimpleTableChange implements TableChange {
    protected String originalName;
    protected TableProperty tableProperty;
    protected ChangeCommand command;
    protected ObservableList<ColumnChange> columns;
    protected ObservableList<IndexChange> indexes;

    public SimpleTableChange(String tableName, TableProperty tableProperty, ChangeCommand command) {
        this(tableName, tableProperty, command, new ArrayList<>(), new ArrayList<>());
    }

    public SimpleTableChange(String tableName, TableProperty tableProperty, ChangeCommand command, List<ColumnChange> columns, List<IndexChange> indexes) {
        this.originalName = tableName;
        this.tableProperty = tableProperty;
        this.command = command;
        this.columns = FXCollections.observableArrayList(columns);
        this.indexes = FXCollections.observableArrayList(indexes);
    }

    @Override
    public String getOriginalName() {
        if (this.originalName == null) {
            return this.getName();
        }
        return this.originalName;
    }

    @Override
    public StringProperty nameProperty() {
        return this.tableProperty.nameProperty();
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
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

    @Override
    public ChangeCommand getCommand() {
        return this.command;
    }

    @Override
    public Boolean isNameChanged() {
        return this.getName() != null && !this.getOriginalName().equals(this.getName());
    }
}