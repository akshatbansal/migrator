package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.domain.project.model.Project;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table implements TableChange, ChangeListener<Object> {
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected Project project;
    protected ChangeCommand changeCommand;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;

    public Table(Project project, TableProperty originalTable, TableProperty changedProperty, ChangeCommand changeCommand, ObservableList<Column> columns, ObservableList<Index> indexes) {
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.project = project;
        this.changeCommand = changeCommand;
        this.columns = columns;
        this.indexes = indexes;

        this.changedTable.nameProperty().addListener(this);
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

    public Project getProject() {
        return this.project;
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
}