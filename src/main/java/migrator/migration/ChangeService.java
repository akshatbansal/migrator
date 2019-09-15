package migrator.migration;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChangeService {
    protected ObservableList<Object> list;
    protected ObjectProperty<Object> selectedColumn;

    public ChangeService() {
        this.selectedColumn = new SimpleObjectProperty<>();
        this.list = FXCollections.observableArrayList();
    }

    public ObjectProperty<Object> getSelectedColumn() {
        return this.selectedColumn;
    }

    public void selectColumn(Object o) {
        this.selectedColumn.set(o);
    }
}