package migrator.table.service;

import java.util.Collection;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;
import migrator.table.model.Column;

public class ColumnService {
    protected ObservableList<Column> list;
    protected ObjectProperty<Column> selected;

    public ColumnService() {
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Column> c) -> {
            this.onListChange();
        });
    }

    protected void onListChange() {
        if (this.selected.get() == null) {
            return;
        }
        if (this.list.contains(this.selected.get())) {
            return;
        }
        this.select(null);
    }

    public ObservableList<Column> getList() {
        return this.list;
    }

    public ObjectProperty<Column> getSelected() {
        return this.selected;
    }

    public void select(Column column) {
        this.selected.set(column);
    }

    public void remove(Column column) {
        this.list.remove(column);
    }

    public void add(Column column) {
        this.list.add(column);
    }

    public void setAll(Collection<Column> columns) {
        this.list.setAll(columns);
    }

    public Column create(String name) {
        return this.create(
            name,
            new ColumnChange(name, new ChangeCommand())
        );
    }    

    public Column create(String name, String format, String defaultValue, boolean enableNull) {
        return this.create(
            name,
            format,
            defaultValue,
            enableNull,
            new ColumnChange(name, new ChangeCommand())
        );
    }

    public Column create(String name, ColumnChange change) {
        Column column = this.create(
            name,
            "string",
            "",
            false,
            change
        );
        change.getCommand().argumentProperty("format").set(column.getFormat());
        change.getCommand().argumentProperty("default").set(column.getDefaultValue());
        change.getCommand().argumentProperty("null").set(column.isNullEnabled());

        return column;
    }

    public Column create(String name, String format, String defaultValue, boolean enableNull, ColumnChange change) {
        return new Column(
            name,
            format,
            defaultValue,
            enableNull,
            change
        );
    }
}