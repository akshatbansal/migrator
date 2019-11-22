package migrator.app.migration.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleTableProperty implements TableProperty {
    protected StringProperty name;
    protected String id;

    public SimpleTableProperty(String id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String getUniqueKey() {
        return this.id;
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