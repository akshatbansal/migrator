package migrator.app.migration.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import migrator.lib.repository.UniqueItem;

public interface IndexProperty extends UniqueItem {
    public StringProperty nameProperty();
    public String getName();
    public ObservableList<StringProperty> columnsProperty();
    public StringProperty columnsStringProperty();
    public void addColumn(String columnName);
}