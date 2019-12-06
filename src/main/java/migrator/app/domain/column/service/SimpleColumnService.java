package migrator.app.domain.column.service;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.modelstorage.ActiveState;
import migrator.lib.diff.CompareListDiff;
import migrator.lib.diff.ListDiff;

public class SimpleColumnService implements ColumnService {
    protected TableActiveState tableActiveState;
    protected ColumnRepository columnRepository;
    protected ActiveState<Column> columnActiveState;
    protected ColumnFactory columnFactory;
    protected DatabaseDriverManager databaseDriverManager;
    protected ChangeListener<Table> onTableChangeListener;
    protected ProjectService projectService;

    public SimpleColumnService(
        ColumnRepository columnRepository,
        ActiveState<Column> columnActiveState,
        ColumnFactory columnFactory,
        TableActiveState tableActiveState,
        ProjectService projectService,
        DatabaseDriverManager databaseDriverManager
    ) {
        this.tableActiveState = tableActiveState;
        this.columnRepository = columnRepository;
        this.columnActiveState = columnActiveState;
        this.columnFactory = columnFactory;
        this.databaseDriverManager = databaseDriverManager;
        this.projectService = projectService;
        
        this.onTableChangeListener = (ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
            this.onTableSelect(newValue);
        };
    }

    @Override
    public void start() {
        this.tableActiveState.getActive().addListener(this.onTableChangeListener);   
    }

    @Override
    public void stop() {
        this.tableActiveState.getActive().removeListener(this.onTableChangeListener);
    }

    protected void onTableSelect(Table activeTable) {
        if (activeTable == null) {
            return;
        }
        Project project = this.projectService.getOpened().get();

        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(project.getDatabase());
        databaseDriver.connect();

        List<Column> dbList = databaseDriver.getColumns(activeTable);
        for (Column c : dbList) {
            c.setTableId(activeTable.getUniqueKey());
        }
        this.merge(
            dbList,
            this.columnRepository.findByTable(activeTable.getUniqueKey())
        );

        this.columnActiveState.setListAll(
            this.columnRepository.findByTable(activeTable.getUniqueKey())
        );
    }

    @Override
    public void activate(Column column) {
        this.columnActiveState.activate(column);
    }

    @Override
    public void add(Column column) {
        this.columnActiveState.add(column);
    }

    @Override
    public void addAndActivate(Column column) {
        this.add(column);
        this.activate(column);
    }

    @Override
    public ObservableList<Column> getAll() {
        return this.columnActiveState.getList();
    }

    @Override
    public void remove(Column column) {
        this.columnActiveState.remove(column);
    }

    protected void merge(List<Column> dbList, List<Column> repoList) {
        ListDiff<Column> diff = new CompareListDiff<>(dbList, repoList, (Column a, Column b) -> {
            if (a.getChangeCommand().isType(ChangeCommand.CREATE)) {
                return a.getChange().getName().equals(
                    b.getChange().getName()
                );

            }
            return a.getOriginal().getName().equals(
                b.getOriginal().getName()
            );
        });

        for (List<Column> columnPair : diff.getCommon()) {
            if (columnPair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                this.columnRepository.removeWith(columnPair.get(1));
                this.columnRepository.addWith(columnPair.get(0));
            } else {
                columnPair.get(1).updateOriginal(
                    columnPair.get(0).getOriginal()
                );
            }
        }
        for (Column column : diff.getLeftMissing()) {
            if (column.getChangeCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            this.columnRepository.removeWith(column);
        }
        for (Column column : diff.getRightMissing()) {
            this.columnRepository.addWith(column);
        }
    }
}