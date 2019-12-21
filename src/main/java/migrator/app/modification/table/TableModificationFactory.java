package migrator.app.modification.table;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;
import migrator.app.modification.ModificationFactory;
import migrator.app.modification.SimpleModification;

public class TableModificationFactory implements ModificationFactory<TableProperty> {
    @Override
    public Modification<TableProperty> create(TableProperty original, TableProperty modification, ChangeCommand changeCommand) {
        return new SimpleModification<TableProperty>(original, modification, changeCommand);
    }

    @Override
    public Modification<TableProperty> createExisting(TableProperty original) {
        return this.create(
            original,
            new SimpleTableProperty("", original.getName()),
            new ChangeCommand("", ChangeCommand.NONE)
        );
    }

    @Override
    public Modification<TableProperty> createNew(TableProperty modification) {
        return this.create(
            null,
            modification, 
            new ChangeCommand("", ChangeCommand.CREATE)
        );
    }
}