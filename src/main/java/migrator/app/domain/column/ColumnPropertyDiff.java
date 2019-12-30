package migrator.app.domain.column;

import java.util.LinkedList;
import java.util.List;

import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.migration.model.ColumnProperty;

public class ColumnPropertyDiff {
    protected ColumnProperty original;
    protected ColumnProperty modification;
    protected ApplicationColumnFormat modificationFormat;
    protected List<String> changes;

    public ColumnPropertyDiff(
        ColumnProperty original,
        ColumnProperty modification,
        ApplicationColumnFormat modificationFormat
    ) {
        this.original = original;
        this.modification = modification;
        this.modificationFormat = modificationFormat;
        this.changes = new LinkedList<>();
        
        this.addChangeId("name", this.nameChanged());
        this.addChangeId("format", this.formatChanged());
        this.addChangeId("defaultValue", this.defaultValueChanged());
        this.addChangeId("nullEnabled", this.nullEnabledChanged());
        this.addChangeId("length", this.lengthChanged());
        this.addChangeId("preciosion", this.precisionChanged());
        this.addChangeId("sign", this.signChanged());
        this.addChangeId("autoIncrement", this.autoIncrementChanged());
    }

    private boolean nameChanged() {
        return !this.original.getName().equals(
            this.modification.getName()
        );
    }

    private boolean formatChanged() {
        return !this.original.getFormat().equals(
            this.modification.getFormat()
        );
    }

    private boolean defaultValueChanged() {
        return !this.original.getDefaultValue().equals(
            this.modification.getDefaultValue()
        );
    }

    private boolean nullEnabledChanged() {
        return !this.original.isNullEnabled().equals(
            this.modification.isNullEnabled()
        );
    }

    private boolean lengthChanged() {
        return this.modificationFormat.hasLength() && 
        !this.original.getLength().equals(
            this.modification.getLength()
        );
    }

    private boolean precisionChanged() {
        return this.modificationFormat.hasPrecision() && 
        !this.original.getPrecision().equals(
            this.modification.getPrecision()
        );
    }

    private boolean signChanged() {
        return this.modificationFormat.hasSign() && 
        !this.original.isSigned().equals(
            this.modification.isSigned()
        );
    }

    private boolean autoIncrementChanged() {
        return this.modificationFormat.hasAutoIncrement() && 
        !this.original.isAutoIncrement().equals(
            this.modification.isAutoIncrement()
        );
    }

    private void addChangeId(String name, boolean condition) {
        if (!condition) {
            return;
        }
        this.addChange(name);
    }

    private void addChange(String name) {
        if (this.changes.contains(name)) {
            return;
        }
        this.changes.add(name);
    }

    public List<String> getChanges() {
        return this.changes;
    }

    public boolean hasChanged(String name) {
        return this.changes.contains(name);
    }
}