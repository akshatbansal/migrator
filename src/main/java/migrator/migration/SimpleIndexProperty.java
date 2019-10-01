package migrator.migration;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class SimpleIndexProperty implements IndexProperty {
    protected StringProperty name;
    protected ObservableList<StringProperty> columns;
    protected StringProperty columnsString;

    public SimpleIndexProperty(String name, List<StringProperty> columns) {
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);
        this.columnsString = new SimpleStringProperty();

        this.columns.addListener((Change<? extends StringProperty> change) -> {
            this.onColumnsChange();
        });
        this.onColumnsChange();
    }

    protected void onColumnsChange() {
        String newColumnString = "";
        for (StringProperty column : this.columns) {
            if (column.get() == null || column.get().isEmpty()) {
                continue;
            }
            if (!newColumnString.isEmpty()) {
                newColumnString += ", ";
            }
            newColumnString += column.get();
        }
        this.columnsString.set(newColumnString);
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public ObservableList<StringProperty> columnsProperty() {
        return this.columns;
    }

    @Override
    public StringProperty columnsStringProperty() {
        return this.columnsString;
    }

    public StringProperty columnProperty(int index) {
        return this.columns.get(index);
    }
}