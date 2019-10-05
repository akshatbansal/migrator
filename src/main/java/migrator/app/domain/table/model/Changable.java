package migrator.app.domain.table.model;

import migrator.migration.ChangeCommand;

public interface Changable {
    public ChangeCommand getChangeCommand();
}