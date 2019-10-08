package migrator.ext.javafx.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card<T> {
    protected StringProperty name;
    protected StringProperty primary;
    protected StringProperty secondary;
    protected StringProperty changeType;
    protected T value;

    public Card(T value, StringProperty name, StringProperty primary, StringProperty secondary, StringProperty changeType) {
        this.value = value;
        this.name = name;
        this.primary = primary;
        this.secondary = secondary;
        this.changeType = changeType;
    }

    public Card(T value, StringProperty name, String primary, String secondary, StringProperty changeType) {
        this(value, name, new SimpleStringProperty(primary), new SimpleStringProperty(secondary), changeType);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public StringProperty primaryProperty() {
        return this.primary;
    }

    public StringProperty secondaProperty() {
        return this.secondary;
    }

    public StringProperty changeTypeProperty() {
        return this.changeType;
    }

    public T getValue() {
        return this.value;
    }
}