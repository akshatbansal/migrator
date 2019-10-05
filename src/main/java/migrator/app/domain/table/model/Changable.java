package migrator.app.domain.table.model;

import migrator.app.migration.model.ChangeCommand;

public interface Changable {
    public ChangeCommand getChangeCommand();
}