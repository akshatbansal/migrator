package migrator.table.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.migration.ChangeCommand;
import migrator.migration.IndexChange;

public class Index implements Changable {
    protected StringProperty name;
    protected ObservableList<StringProperty> columns;
    protected StringProperty columnsString;
    protected IndexChange change;

    public Index(String name) {
        this(name, new ArrayList<>(), null);
    }

    public Index(String name, IndexChange indexChange) {
        this(name, new ArrayList<>(), indexChange);
    }

    public Index(String name, List<StringProperty> columns, IndexChange indexChange) {
        this.name = new SimpleStringProperty(name);
        this.columns = FXCollections.observableArrayList(columns);
        this.columnsString = new SimpleStringProperty();
        this.change = indexChange;

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
        System.out.println(newColumnString);
        this.columnsString.set(newColumnString);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return this.name.get();
    }

    public ObservableList<StringProperty> columnsProperty() {
        return this.columns;
    }

    public StringProperty columnsStringProperty() {
        return this.columnsString;
    }

    public StringProperty columnProperty(int index) {
        this.fillColumnsTo(index);
        return this.columns.get(index);
    }

    @Override
    public ChangeCommand getChangeCommand() {
        return this.change.getCommand();
    }

    public IndexChange getChange() {
        return this.change;
    }

    protected void fillColumnsTo(int index) {
        while (this.columns.size() <= index) {
            this.columns.add(this.addColumn(""));
        }
    }

    public StringProperty addColumn(String column) {
        StringProperty newColumn = new SimpleStringProperty(column);
        newColumn.addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onColumnsChange();
        });
        return newColumn;
    }
}