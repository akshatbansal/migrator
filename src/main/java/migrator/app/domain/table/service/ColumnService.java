package migrator.app.domain.table.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.change.service.ColumnChangeService;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.migration.model.TableChange;

public class ColumnService {
    protected ChangeService changeService;
    protected ColumnChangeService columnChangeService;
    protected DatabaseDriverManager databaseDriverManager;
    protected ObjectProperty<Table> activeTable;
    protected ColumnFactory columnFactory;
    protected ObservableList<Column> list;
    protected ObjectProperty<Column> selected;

    public ColumnService(ColumnFactory columnFactory, ChangeService changeService, ObjectProperty<Table> activeTable, DatabaseDriverManager databaseDriverManager, ColumnChangeService columnChangeService) {
        this.columnFactory = columnFactory;
        this.changeService = changeService;
        this.activeTable = activeTable;
        this.databaseDriverManager = databaseDriverManager;
        this.columnChangeService = columnChangeService;
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
        if (column.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.list.remove(column);
        } else {
            column.delete();
        }
    }

    public void add(Column column) {
        this.list.add(column);
    }

    public void setAll(Collection<Column> columns) {
        this.list.setAll(columns);
    }

    public ColumnFactory getFactory() {
        return this.columnFactory;
    }

    protected void register(Column column, Table table) {
        this.add(column);
        TableChange tableChange = this.changeService.getTableChange(
            table.getProject().getName(),
            table.getOriginalName()
        );
        tableChange.getColumnsChanges().add(column.getChange());
    }

    public void register(Column column) {
        this.register(column, this.activeTable.get());
    }

    protected void unregister(Column column, Table table) {
        TableChange tableChange = this.changeService.getTableChange(
            table.getProject().getName(),
            table.getOriginalName()
        );
        tableChange.getColumnsChanges().remove(column.getChange());
        this.remove(column);
    }

    public void unregister(Column column) {
        this.unregister(column, this.activeTable.get());
    }

    public ObservableList<Column> loadAll(Table table) {
        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(table.getProject().getDatabase());
        databaseDriver.connect();

        List<Column> columns = new ArrayList<>();
        for (List<String> rawColumn : databaseDriver.getColumns(table.getOriginalName())) {
            String defaultValue = rawColumn.get(3);
            if (defaultValue == null) {
                defaultValue = "";
            }
            ColumnProperty columnProperty = new SimpleColumnProperty(
                rawColumn.get(0),
                rawColumn.get(1),
                defaultValue,
                rawColumn.get(2) == "YES" ? true : false
            );
            ColumnChange columnChange = this.columnChangeService.getOrCreate(table, columnProperty);
            columns.add(
                this.columnFactory.create(
                    columnProperty.getName(),
                    columnProperty.getFormat(),
                    columnProperty.getDefaultValue(),
                    columnProperty.isNullEnabled(),
                    columnChange
                )
            );
        }
        // List<TableChange> createdTableChanges = this.changeService.getCreatedTableChanges(project.getName());
        // for (TableChange tableChange : createdTableChanges) {
        //     tables.add(
        //         tableFactory.create(
        //             project,
        //             tableChange.getOriginalName(),
        //             tableChange
        //         )
        //     );
        // }
        this.setAll(columns);
        return this.getList();
    }
}