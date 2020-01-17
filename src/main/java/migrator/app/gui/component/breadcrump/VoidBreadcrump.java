package migrator.app.gui.component.breadcrump;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VoidBreadcrump implements Breadcrump {
    protected StringProperty name;

    public VoidBreadcrump(String name) {
        this(new SimpleStringProperty(name));
    }

    public VoidBreadcrump(StringProperty name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public void link() {}
}