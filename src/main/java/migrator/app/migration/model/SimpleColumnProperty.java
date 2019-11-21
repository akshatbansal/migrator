package migrator.app.migration.model;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleColumnProperty implements ColumnProperty {
    protected String id;
    protected transient StringProperty name;
    protected transient StringProperty format;
    protected transient StringProperty defaultValue;
    protected transient Property<Boolean> enableNull;
    protected transient StringProperty length;
    protected transient StringProperty precision;
    protected transient Property<Boolean> sign;
    protected transient Property<Boolean> autoIncrement;

    public SimpleColumnProperty(String id, String name, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision, Boolean autoIncrement) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.format = new SimpleStringProperty(format);
        this.defaultValue = new SimpleStringProperty(defaultValue);
        this.enableNull = new SimpleObjectProperty<>(enableNull);
        this.length = new SimpleStringProperty(length);
        this.precision = new SimpleStringProperty(precision);
        this.sign = new SimpleObjectProperty<>(sign);
        this.autoIncrement = new SimpleObjectProperty<>(autoIncrement);
    }

    @Override
    public String getUniqueKey() {
        return this.id;
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

    @Override
    public String getLength() {
        return this.lengthProperty().getValue();
    }

    @Override
    public StringProperty lengthProperty() {
        return this.length;
    }

    @Override
    public String getPrecision() {
        return this.precisionProperty().getValue();
    }

    @Override
    public StringProperty precisionProperty() {
        return this.precision;
    }

    @Override
    public Boolean isSigned() {
        return this.signProperty().getValue();
    }

    @Override
    public Property<Boolean> signProperty() {
        return this.sign;
    }

    @Override
    public Property<Boolean> autoIncrementProperty() {
        return this.autoIncrement;
    }

    @Override
    public Boolean isAutoIncrement() {
        return this.autoIncrementProperty().getValue();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}