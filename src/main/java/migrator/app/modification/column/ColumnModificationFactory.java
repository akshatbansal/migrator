package migrator.app.modification.column;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.modification.ModificationFactory;
import migrator.app.modification.SimpleModification;

public class ColumnModificationFactory implements ModificationFactory<ColumnProperty> {
    @Override
    public Modification<ColumnProperty> create(ColumnProperty original, ColumnProperty modification,
            ChangeCommand changeCommand) {
        return new SimpleModification<ColumnProperty>(original, modification, changeCommand);
    }

    @Override
    public Modification<ColumnProperty> createExisting(ColumnProperty original) {
        return this.create(
            original,
            new SimpleColumnProperty(
                "",
                original.getName(),
                original.getFormat(),
                original.getDefaultValue(),
                original.isNullEnabled(),
                original.getLength(),
                original.isSigned(),
                original.getPrecision(),
                original.isAutoIncrement()
            ),
            new ChangeCommand("", ChangeCommand.NONE)
        );
    }

    @Override
    public Modification<ColumnProperty> createNew(ColumnProperty modification) {
        return this.create(
            null,
            modification,
            new ChangeCommand("", ChangeCommand.CREATE)
        );
    }
}