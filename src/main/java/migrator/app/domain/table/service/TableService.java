package migrator.app.domain.table.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.TableChange;

public class TableService {
    protected TableFactory tableFactory;
    protected ChangeService changeService;
    protected DatabaseDriverManager databaseDriverManager;
    protected ObservableList<Table> list;
    protected ObjectProperty<Table> selected;

    public TableService(ChangeService changeService, TableFactory tableFactory, DatabaseDriverManager databaseDriverManager) {
        this.tableFactory = tableFactory;
        this.changeService = changeService;
        this.databaseDriverManager = databaseDriverManager;
        this.list = FXCollections.observableArrayList();
        this.selected = new SimpleObjectProperty<>();

        this.list.addListener((Change<? extends Table> c) -> {
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

    public ObservableList<Table> getList() {
        return this.list;
    }

    public ObjectProperty<Table> getSelected() {
        return this.selected;
    }

    public void select(Table table) {
        this.selected.set(table);
    }

    public void remove(Table table) {
        this.list.remove(table);
    }

    public void add(Table table) {
        this.list.add(table);
    }

    public void register(Table table) {
        this.list.add(table);
        this.changeService.addTableChange(
            table.getProject().getName(),
            table.getChange()
        );
    }

    public void setAll(List<Table> tables) {
        this.list.setAll(tables);
    }

    public TableFactory getFactory() {
        return this.tableFactory;
    }

    public ObservableList<Table> loadAll(Project project) {
        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(project.getDatabase());
        databaseDriver.connect();

        List<Table> tables = new ArrayList<>();
        for (String tableName : databaseDriver.getTables()) {
            TableChange tableChange = this.changeService.getOrCreateTableChange(project.getName(), tableName);
            tables.add(
                this.tableFactory.create(
                    project,
                    tableName,
                    tableChange
                )
            );
        }
        List<TableChange> createdTableChanges = this.changeService.getCreatedTableChanges(project.getName());
        for (TableChange tableChange : createdTableChanges) {
            tables.add(
                tableFactory.create(
                    project,
                    tableChange.getOriginalName(),
                    tableChange
                )
            );
        }
        this.setAll(tables);
        return this.getList();
    }
}