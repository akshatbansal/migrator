package migrator.app.migration.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleTableProperty implements TableProperty {
    protected StringProperty name;

    public SimpleTableProperty(String name) {
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String getName() {
        return this.nameProperty().get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }
}