package migrator.app.domain.column;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import migrator.app.database.column.format.ApplicationColumnFormat;
import migrator.app.domain.table.model.Column;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.diff.Diff;
import migrator.lib.diff.ListDiff;

public class ColumnPropertyDiff {
    protected ColumnProperty original;
    protected ColumnProperty modification;
    protected List<Boolean> formatSettings;
    protected List<String> changes;

    public ColumnPropertyDiff(
        ColumnProperty original,
        ColumnProperty modification,
        List<Boolean> formatSettings
    ) {
        this.original = original;
        this.modification = modification;
        this.formatSettings = formatSettings;
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

    public ColumnPropertyDiff(
        ColumnProperty original,
        ColumnProperty modification,
        ApplicationColumnFormat modificationFormat
    ) {
        this(original, modification, Arrays.asList(
            modificationFormat.hasLength(),
            modificationFormat.hasPrecision(),
            modificationFormat.hasSign(),
            modificationFormat.hasAutoIncrement()
        ));
    }

    public ColumnPropertyDiff(Column column) {
        this(column.getOriginal(), column, Arrays.asList(
            column.attribute("length").get(),
            column.attribute("precision").get(),
            column.attribute("sign").get(),
            column.attribute("autoIncrement").get()
        ));
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
        return this.formatSettings.get(0) && 
        !this.original.getLength().equals(
            this.modification.getLength()
        );
    }

    private boolean precisionChanged() {
        return this.formatSettings.get(1) && 
        !this.original.getPrecision().equals(
            this.modification.getPrecision()
        );
    }

    private boolean signChanged() {
        return this.formatSettings.get(2) && 
        !this.original.isSigned().equals(
            this.modification.isSigned()
        );
    }

    private boolean autoIncrementChanged() {
        return this.formatSettings.get(3) && 
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

    public boolean hasChangedExcept(String name) {
        ListDiff<String> listDiff = Diff.getSimpleListDiff(this.changes, Arrays.asList(name));
        return !listDiff.getRightMissing().isEmpty();
    }
}