package migrator.table.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;

public class Column implements Changable {
    protected StringProperty name;
    protected StringProperty format;
    protected StringProperty defaultValue;
    protected BooleanProperty enableNull;
    protected ColumnChange change;

    public Column(String name, String format, String defaultValue, boolean enableNull, ColumnChange columnChange) {
        this.name = new SimpleStringProperty(name);
        this.format = new SimpleStringProperty(format);
        this.defaultValue = new SimpleStringProperty(defaultValue);
        this.enableNull = new SimpleBooleanProperty(enableNull);
        this.change = columnChange;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return this.name.get();
    }

    public StringProperty formatProperty() {
        return this.format;
    }

    public String getFormat() {
        return this.format.get();
    }

    public StringProperty defaultValueProperty() {
        return this.defaultValue;
    }

    public String getDefaultValue() {
        return this.defaultValue.get();
    }

    public BooleanProperty enableNullProperty() {
        return this.enableNull;
    }

    public Boolean isNullEnabled() {
        return this.enableNull.get();
    }

    public ColumnChange getChange() {
        return this.change;
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }
}