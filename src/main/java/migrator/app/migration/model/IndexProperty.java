package migrator.app.migration.model;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import migrator.lib.repository.UniqueItem;

public interface IndexProperty extends UniqueItem {
    public StringProperty nameProperty();
    public String getName();
    public ObservableList<ColumnProperty> columnsProperty();
    public StringProperty columnsStringProperty();
    public void addColumn(ColumnProperty column);
    public void setColumnAt(int index, ColumnProperty column);
}