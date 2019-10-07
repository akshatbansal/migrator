package migrator.app.domain.table.service;

import migrator.app.domain.change.service.TableChangeFactory;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableChange;
import migrator.app.migration.model.TableProperty;

public class TableFactory {
    protected TableChangeFactory tableChangeFactory;

    public TableFactory(TableChangeFactory tableChangeFactory) {
        this.tableChangeFactory = tableChangeFactory;
    }

    public Table createNotChanged(Project project, String tableName) {
        return new Table(
            project,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            this.tableChangeFactory.createNotChanged(tableName)
        );
    }

    public Table createWithCreateChange(Project project, String tableName) {

        return new Table(
            project,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            this.tableChangeFactory.createWithCreateChange(tableName)
        );
    }

    public Table create(Project project, String tableName, TableChange change) {
        TableProperty tableProperty = new SimpleTableProperty(tableName);
        if (change.getName() != null) {
            tableProperty.nameProperty().set(change.getName());
        }
        return new Table(
            project,
            new SimpleTableProperty(tableName),
            tableProperty,
            change
        );
    }
}