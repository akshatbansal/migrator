package migrator.app.breadcrumps;

import javafx.beans.property.StringProperty;
// TODO: remove
public interface Breadcrump {
    public String getName();
    public StringProperty nameProperty();
    public void link();
}