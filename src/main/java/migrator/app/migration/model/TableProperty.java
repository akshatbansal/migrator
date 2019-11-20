package migrator.app.migration.model;

import javafx.beans.property.StringProperty;
import migrator.lib.repository.UniqueItem;

public interface TableProperty extends UniqueItem {
    public StringProperty nameProperty();
    public String getName();
}