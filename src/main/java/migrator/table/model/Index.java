package migrator.table.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;

public class Index {
    protected StringProperty name;
    protected ObservableList<String> columns;
    protected StringProperty columnsString;

    public Index(String name) {
        this(name, new ArrayList<>());
    }

    public Index(String name, List<String> columns) {
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);
        this.columnsString = new SimpleStringProperty();

        this.columns.addListener((Change<? extends String> change) -> {
            this.onColumnsChange();
        });
        this.onColumnsChange();
    }

    protected void onColumnsChange() {
        this.columnsString.set(String.join(", ", this.columns));
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public ObservableList<String> columnsProperty() {
        return this.columns;
    }

    public StringProperty columnsStringProperty() {
        return this.columnsString;
    }
}