package migrator.app.migration.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class SimpleIndexProperty implements IndexProperty, Serializable {
    private static final long serialVersionUID = -7211629526189915672L;
    protected transient StringProperty name;
    protected transient ObservableList<StringProperty> columns;
    protected transient StringProperty columnsString;

    public SimpleIndexProperty(String name, List<StringProperty> columns) {
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);

        this.initialize();
    }

    protected void initialize() {
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

    @Override
    public void addColumn(String columnName) {
        StringProperty newColumn = new SimpleStringProperty(columnName);
        newColumn.addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onColumnsChange();
        });
        this.columns.add(newColumn);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();

        s.writeUTF(this.name.get());
        List<String> columns = new ArrayList<>();
        for (StringProperty column : this.columns) {
            columns.add(column.get());
        }
        s.writeObject(columns);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();

        this.name = new SimpleStringProperty(s.readUTF());
        this.columns = FXCollections.observableArrayList();

        List<String> columns = (List<String>) s.readObject();
        for (String column : columns) {
            this.columns.add(
                new SimpleStringProperty(column)
            );
        }

        this.initialize();
    }
}