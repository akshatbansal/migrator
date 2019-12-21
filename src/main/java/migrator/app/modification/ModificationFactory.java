package migrator.app.modification;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.Modification;

public interface ModificationFactory<T> {
    public Modification<T> create(T original, T modification, ChangeCommand changeCommand);
    public Modification<T> createNew(T modification);
    public Modification<T> createExisting(T original);
}