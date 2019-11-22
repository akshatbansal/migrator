package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table implements TableChange, ChangeListener<Object>, Modification<TableProperty> {
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected ChangeCommand changeCommand;
    protected String id;
    protected String projectId;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;

    public Table(
        String id,
        String projectId,
        TableProperty originalTable,
        TableProperty changedProperty,
        ChangeCommand changeCommand,
        ObservableList<Column> columns,
        ObservableList<Index> indexes
    ) {
        this.id = id;
        this.projectId = projectId;
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.changeCommand = changeCommand;
        this.columns = columns;
        this.indexes = indexes;

        this.changedTable.nameProperty().addListener(this);
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setColumns(ObservableList<Column> columns) {
        this.columns = columns;
    }

    public void setIndexes(ObservableList<Index> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String getUniqueKey() {
        return this.id;
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
    public TableProperty getModification() {
        return this.changedTable;
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

    @Deprecated
    @Override
    public ChangeCommand getCommand() {
        return this.getChangeCommand();
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

    @Override
    public ChangeCommand getChangeCommand() {
        return this.changeCommand;
    }

    @Override
    public void updateOriginal(TableProperty original) {
        if (!this.hasNameChanged()) {
            this.getChange().nameProperty().set(
                original.getName()
            );
        }
        this.getOriginal().nameProperty().set(
            original.getName()
        );
    }
}