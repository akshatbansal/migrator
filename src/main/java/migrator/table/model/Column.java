package migrator.table.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.migration.ColumnChange;

public class Column {
    protected StringProperty name;
    protected StringProperty format;
    protected StringProperty defaultValue;
    protected BooleanProperty enableNull;
    protected ColumnChange change;

    public Column(String name) {
        this(name, null);
    }

    public Column(String name, ColumnChange columnChange) {
        this(name, "varchar(255)", "", false, columnChange);
    }

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

    public StringProperty defaultValueProperty() {
        return this.defaultValue;
    }

    public BooleanProperty enableNullProperty() {
        return this.enableNull;
    }

    public ColumnChange getChange() {
        return this.change;
    }
}