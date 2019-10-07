package migrator.ext.javafx.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Card<T> {
    protected StringProperty name;
    protected StringProperty primary;
    protected StringProperty secondary;
    protected T value;

    public Card(T value, StringProperty name, StringProperty primary, StringProperty secondary) {
        this.value = value;
        this.name = name;
        this.primary = primary;
        this.secondary = secondary;
    }

    public Card(T value, StringProperty name, String primary, String secondary) {
        this(value, name, new SimpleStringProperty(primary), new SimpleStringProperty(secondary));
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

    public T getValue() {
        return this.value;
    }
}