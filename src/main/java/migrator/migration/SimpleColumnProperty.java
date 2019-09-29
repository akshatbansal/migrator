package migrator.migration;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleColumnProperty implements ColumnProperty {
    protected StringProperty name;
    protected StringProperty format;
    protected StringProperty defaultValue;
    protected Property<Boolean> enableNull;

    public SimpleColumnProperty(String name, String format, String defaultValue, Boolean enableNull) {
        this.name = new SimpleStringProperty(name);
        this.format = new SimpleStringProperty(format);
        this.defaultValue = new SimpleStringProperty(defaultValue);
        this.enableNull = new SimpleObjectProperty<>(enableNull);
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public StringProperty formatProperty() {
        return this.format;
    }

    @Override
    public String getFormat() {
        return this.formatProperty().get();
    }

    @Override
    public StringProperty defaultValueProperty() {
        return this.defaultValue;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValueProperty().get();
    }

    @Override
    public Property<Boolean> nullProperty() {
        return this.enableNull;
    }

    @Override
    public Boolean isNullEnabled() {
        return this.nullProperty().getValue();
    }
}