package migrator.app.domain.column.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.modelstorage.ActiveState;
import migrator.lib.modelstorage.Repository;

public class SimpleColumnService implements ColumnService {
    protected TableService tableService;
    protected Repository<Column> columnRepository;
    protected ActiveState<Column> columnActiveState;
    protected ColumnFactory columnFactory;
    protected DatabaseDriverManager databaseDriverManager;
    protected ChangeListener<Table> onTableChangeListener;

    public SimpleColumnService(
        Repository<Column> columnRepository,
        ActiveState<Column> columnActiveState,
        ColumnFactory columnFactory,
        TableService tableService,
        DatabaseDriverManager databaseDriverManager
    ) {
        this.tableService = tableService;
        this.columnRepository = columnRepository;
        this.columnActiveState = columnActiveState;
        this.columnFactory = columnFactory;
        this.databaseDriverManager = databaseDriverManager;
        
        this.onTableChangeListener = (ObservableValue<? extends Table> observable, Table oldValue, Table newValue) -> {
            this.onTableSelect(newValue);
        };
    }

    @Override
    public void start() {
        this.tableService.getActiveState()
            .getActive().addListener(this.onTableChangeListener);
        
    }

    @Override
    public void stop() {
        this.tableService.getActiveState()
            .getActive().removeListener(this.onTableChangeListener);
    }

    protected void onTableSelect(Table activeTable) {
        String repositryKey = activeTable.getProject().getName() + "." + activeTable.getOriginalName();

        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(activeTable.getProject().getDatabase());
        databaseDriver.connect();

        List<Column> columns = new ArrayList<>();
        for (List<String> rawColumn : databaseDriver.getColumns(activeTable.getOriginalName())) {
            String defaultValue = rawColumn.get(3);
            if (defaultValue == null) {
                defaultValue = "";
            }
            Column dbValue = this.columnFactory.createNotChanged(
                rawColumn.get(0),
                rawColumn.get(1),
                defaultValue,
                rawColumn.get(2) == "YES" ? true : false
            );

            columns.add(
                this.mergeColumn(dbValue, this.columnRepository.get(repositryKey, dbValue.getOriginal().getName()))
            );
        }

        for (Column column : this.columnRepository.getList(repositryKey)) {
            if (!column.getCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            columns.add(column);
        }
        this.columnRepository.setList(repositryKey, columns);

        this.columnActiveState.setListAll(
            this.columnRepository.getList(repositryKey)
        );
    }

    @Override
    public ActiveState<Column> getActiveState() {
        return this.columnActiveState;
    }

    @Override
    public ColumnFactory getFactory() {
        return this.columnFactory;
    }

    @Override
    public Repository<Column> getRepository() {
        return this.columnRepository;
    }

    protected Column mergeColumn(Column dbValue, Column repositoryValue) {
        if (dbValue == null) {
            if (repositoryValue.getCommand().isType(ChangeCommand.CREATE)) {
                return repositoryValue;
            }
            return null;
        }

        if (repositoryValue == null) {
            return dbValue;
        }

        repositoryValue.getOriginal().nameProperty().set(dbValue.getOriginal().getName());
        repositoryValue.getOriginal().formatProperty().set(dbValue.getOriginal().getFormat());
        repositoryValue.getOriginal().defaultValueProperty().set(dbValue.getOriginal().getDefaultValue());
        repositoryValue.getOriginal().nullProperty().setValue(dbValue.getOriginal().isNullEnabled());
        return repositoryValue;
    }

    @Override
    public void add(Column column) {
        Table activeTable = this.tableService.getActiveState()
            .getActive().get();
        String repositoryKey = activeTable.getProject().getName() + "." + activeTable.getName();

        this.columnRepository.add(repositoryKey, column);
        this.columnActiveState.setListAll(
            this.columnRepository.getList(repositoryKey)
        );
    }
}