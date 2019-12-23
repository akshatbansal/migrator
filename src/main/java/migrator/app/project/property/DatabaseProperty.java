package migrator.app.project.property;

import javafx.beans.property.StringProperty;

public interface DatabaseProperty {
    public StringProperty driverProperty();
    public StringProperty urlProperty();
    public StringProperty userProperty();
    public StringProperty passwordProperty();
}