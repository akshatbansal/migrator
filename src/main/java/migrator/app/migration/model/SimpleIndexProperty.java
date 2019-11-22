package migrator.app.migration.model;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class SimpleIndexProperty implements IndexProperty {
    protected String id;
    protected StringProperty name;
    protected ObservableList<ColumnProperty> columns;
    protected StringProperty columnsString;

    public SimpleIndexProperty(String id, String name, List<ColumnProperty> columns) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);

        this.initialize();
    }

    @Override
    public String getUniqueKey() {
        return this.id;
    }

    protected void initialize() {
        this.columnsString = new SimpleStringProperty();

        this.columns.addListener((Change<? extends ColumnProperty> change) -> {
            this.onColumnsChange();
        });
        this.onColumnsChange();
    }

    protected void onColumnsChange() {
        String newColumnString = "";
        for (ColumnProperty column : this.columns) {
            if (column == null) {
                continue;
            }
            if (!newColumnString.isEmpty()) {
                newColumnString += ", ";
            }
            newColumnString += column.getName();
        }
        if (newColumnString.isEmpty()) {
            newColumnString = null;
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
    public ObservableList<ColumnProperty> columnsProperty() {
        return this.columns;
    }

    @Override
    public StringProperty columnsStringProperty() {
        return this.columnsString;
    }

    public ColumnProperty columnProperty(int index) {
        return this.columns.get(index);
    }

    @Override
    public void addColumn(ColumnProperty column) {
        if (column != null) {
            column.nameProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
                this.onColumnsChange();
            });
        }
        this.columns.add(column);
    }

    @Override
    public void setColumnAt(int index, ColumnProperty column) {
        if (column != null) {
            column.nameProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
                this.onColumnsChange();
            });
        }
        this.columns.set(index, column);
    }
}