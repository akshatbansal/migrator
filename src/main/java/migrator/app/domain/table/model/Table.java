package migrator.app.domain.table.model;

import javafx.beans.property.StringProperty;
import migrator.app.domain.project.model.Project;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class Table {
    protected TableProperty originalTable;
    protected TableProperty changedTable;
    protected Project project;
    protected TableChange change;

    public Table(Project project, TableProperty originalTable, TableProperty changedProperty, TableChange tableChange) {
        this.originalTable = originalTable;
        this.changedTable = changedProperty;
        this.project = project;
        this.change = tableChange;

        this.changedTable.nameProperty().addListener(
            new ChangeStringPropertyListener(
                this.originalTable.nameProperty(),
                this.change.nameProperty()
            )
        );
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
        return this.change;
    }

    public void restore() {
        this.changedTable.nameProperty().set(this.originalTable.getName());
        this.change.clear();
    }
}