package migrator.table.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.migration.IndexChange;

public class Index {
    protected StringProperty name;
    protected ObservableList<String> columns;
    protected StringProperty columnsString;
    protected IndexChange change;

    public Index(String name) {
        this(name, new ArrayList<>(), null);
    }

    public Index(String name, IndexChange indexChange) {
        this(name, new ArrayList<>(), indexChange);
    }

    public Index(String name, List<String> columns, IndexChange indexChange) {
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);
        this.columnsString = new SimpleStringProperty();
        this.change = indexChange;

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

    public String getName() {
        return this.name.get();
    }

    public ObservableList<String> columnsProperty() {
        return this.columns;
    }

    public StringProperty columnsStringProperty() {
        return this.columnsString;
    }
}