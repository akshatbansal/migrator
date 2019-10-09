package migrator.app.domain.table.service;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.SimpleTableProperty;

public class TableFactory {
    public TableFactory() {
        
    }

    public Table createNotChanged(Project project, String tableName) {
        return new Table(
            project,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            new ChangeCommand(ChangeCommand.NONE)
        );
    }

    public Table createWithCreateChange(Project project, String tableName) {

        return new Table(
            project,
            new SimpleTableProperty(tableName), // original
            new SimpleTableProperty(tableName), // changed
            new ChangeCommand(ChangeCommand.CREATE)
        );
    }
}