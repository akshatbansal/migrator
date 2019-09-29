package migrator.migration;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

public interface ColumnProperty {
    public StringProperty nameProperty();
    public String getName();
    public StringProperty formatProperty();
    public String getFormat();
    public StringProperty defaultValueProperty();
    public String getDefaultValue();
    public BooleanProperty nullProperty();
    public Boolean isNullEnabled();
}