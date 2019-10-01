package migrator.migration;

import javafx.beans.property.StringProperty;

public interface TableProperty {
    public StringProperty nameProperty();
    public String getName();
}