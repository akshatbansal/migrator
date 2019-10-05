package migrator.app.migration.model;

import javafx.beans.property.StringProperty;

public interface TableProperty {
    public StringProperty nameProperty();
    public String getName();
}