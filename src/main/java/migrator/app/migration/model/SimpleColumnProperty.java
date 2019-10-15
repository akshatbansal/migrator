package migrator.app.migration.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleColumnProperty implements ColumnProperty, Serializable {
    private static final long serialVersionUID = 5618191683285049699L;
    protected transient StringProperty name;
    protected transient StringProperty format;
    protected transient StringProperty defaultValue;
    protected transient Property<Boolean> enableNull;
    protected transient StringProperty length;
    protected transient StringProperty precision;
    protected transient Property<Boolean> sign;

    public SimpleColumnProperty(String name, String format, String defaultValue, Boolean enableNull, String length, Boolean sign, String precision) {
        this.name = new SimpleStringProperty(name);
        this.format = new SimpleStringProperty(format);
        this.defaultValue = new SimpleStringProperty(defaultValue);
        this.enableNull = new SimpleObjectProperty<>(enableNull);
        this.length = new SimpleStringProperty(length);
        this.precision = new SimpleStringProperty(precision);
        this.sign = new SimpleObjectProperty<>(sign);
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeUTF(this.name.get());
        s.writeUTF(this.format.get());
        s.writeUTF(this.defaultValue.get());
        s.writeBoolean(this.enableNull.getValue());
        s.writeUTF(this.length.get());
        s.writeUTF(this.precision.get());
        s.writeBoolean(this.sign.getValue());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        this.name = new SimpleStringProperty(s.readUTF());
        this.format = new SimpleStringProperty(s.readUTF());
        this.defaultValue = new SimpleStringProperty(s.readUTF());
        this.enableNull = new SimpleObjectProperty<>(s.readBoolean());
        this.length = new SimpleStringProperty(s.readUTF());
        this.precision = new SimpleStringProperty(s.readUTF());
        this.sign = new SimpleObjectProperty<>(s.readBoolean());
    }
}