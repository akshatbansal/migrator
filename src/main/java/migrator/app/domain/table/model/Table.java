package migrator.app.domain.table.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table implements TableChange, ChangeListener<Object>, Serializable {
    private static final long serialVersionUID = 7299905871196456955L;
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected ChangeCommand changeCommand;
    protected transient ObservableList<Column> columns;
    protected transient ObservableList<Index> indexes;

    public Table(TableProperty originalTable, TableProperty changedProperty, ChangeCommand changeCommand, ObservableList<Column> columns, ObservableList<Index> indexes) {
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.changeCommand = changeCommand;
        this.columns = columns;
        this.indexes = indexes;

        this.initialize();
    }

    public void initialize() {
        this.changedTable.nameProperty().addListener(this);
    }

    public void setColumns(ObservableList<Column> columns) {
        this.columns = columns;
    }

    public void setIndexes(ObservableList<Index> indexes) {
        this.indexes = indexes;
    }

    public String getId() {
        return this.getName();
    }

    public String getName() {
        return this.changedTable.getName();
    }

    public void setName(String value) {
        this.changedTable.nameProperty().set(value);
    }

    public StringProperty nameProperty() {
        return this.changedTable.nameProperty();
    }

    public String getOriginalName() {
        if (this.originalTable.getName().isEmpty()) {
            return this.getName();
        }
        return this.originalTable.getName();
    }

    public String toString() {
        return this.getName();
    }

    public TableChange getChange() {
        return this;
    }

    @Override
    public void restore() {
        this.changedTable.nameProperty().set(this.originalTable.getName());
        this.changeCommand.setType(ChangeCommand.NONE);
    }

    @Override
    public ObservableList<? extends ColumnChange> getColumnsChanges() {
        return this.columns;
    }

    @Override
    public ChangeCommand getCommand() {
        return this.changeCommand;
    }

    @Override
    public ObservableList<? extends IndexChange> getIndexesChanges() {
        return this.indexes;
    }

    @Override
    public Boolean hasNameChanged() {
        return !this.getOriginalName().equals(this.getName());
    }

    @Override
    public TableProperty getOriginal() {
        return this.originalTable;
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (this.changeCommand.isType(ChangeCommand.DELETE)) {
            return;
        }
        if (this.changeCommand.isType(ChangeCommand.CREATE)) {
            return;
        }
        
        if (this.hasNameChanged()) {
            this.changeCommand.typeProperty().set(ChangeCommand.UPDATE);
            return;
        }
        this.changeCommand.typeProperty().set(ChangeCommand.NONE);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        this.indexes = FXCollections.observableArrayList();
        this.columns = FXCollections.observableArrayList();
        
        this.initialize();
    }
}