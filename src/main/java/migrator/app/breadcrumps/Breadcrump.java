package migrator.app.breadcrumps;

import javafx.beans.property.StringProperty;

public interface Breadcrump {
    public String getName();
    public StringProperty nameProperty();
    public void link();
}