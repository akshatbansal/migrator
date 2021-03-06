package migrator.app.migration.model;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import migrator.lib.repository.UniqueItem;

public interface ColumnProperty extends UniqueItem {
    public StringProperty nameProperty();
    public String getName();
    public StringProperty formatProperty();
    public String getFormat();
    public StringProperty defaultValueProperty();
    public String getDefaultValue();
    public Property<Boolean> nullProperty();
    public Boolean isNullEnabled();
    public String getLength();
    public StringProperty lengthProperty();
    public String getPrecision();
    public StringProperty precisionProperty();
    public Boolean isSigned();
    public Property<Boolean> signProperty();
    public Boolean isAutoIncrement();
    public Property<Boolean> autoIncrementProperty();
}