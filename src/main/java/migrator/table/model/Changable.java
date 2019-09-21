package migrator.table.model;

import migrator.migration.ChangeCommand;

public interface Changable {
    public ChangeCommand getChangeCommand();
}