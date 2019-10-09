package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import migrator.app.domain.project.model.Project;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table implements TableChange {
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected Project project;
    protected ChangeCommand changeCommand;
    protected ObservableList<Column> columns;
    protected ObservableList<Index> indexes;

    public Table(Project project, TableProperty originalTable, TableProperty changedProperty, ChangeCommand changeCommand) {
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.project = project;
        this.changeCommand = changeCommand;

        // this.changedTable.nameProperty().addListener(
        //     new ChangeStringPropertyListener(
        //         this.originalTable.nameProperty(),
        //         this.change.nameProperty()
        //     )
        // );
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
}