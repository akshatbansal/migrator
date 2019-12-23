package migrator.app.modification.index;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.app.modification.ModificationFactory;
import migrator.app.modification.SimpleModification;

public class IndexModificationFactory implements ModificationFactory<IndexProperty> {
    @Override
    public Modification<IndexProperty> create(IndexProperty original, IndexProperty modification,
            ChangeCommand changeCommand) {
        return new SimpleModification<IndexProperty>(original, modification, changeCommand);
    }

    @Override
    public Modification<IndexProperty> createExisting(IndexProperty original) {
        return this.create(
            original,
            new SimpleIndexProperty("", original.getName(), original.columnsProperty()),
            new ChangeCommand("", ChangeCommand.NONE)
        );
    }

    @Override
    public Modification<IndexProperty> createNew(IndexProperty modification) {
        return this.create(
            null,
            modification,
            new ChangeCommand("", ChangeCommand.CREATE)
        );
    }
}