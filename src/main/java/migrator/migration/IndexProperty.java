package migrator.migration;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public interface IndexProperty {
    public StringProperty nameProperty();
    public String getName();
    public ObservableList<StringProperty> columnsProperty();
    public StringProperty columnsStringProperty();
    public void addColumn(String columnName);
}