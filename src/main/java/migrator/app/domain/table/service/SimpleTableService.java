package migrator.app.domain.table.service;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.modelstorage.ActiveState;

public class SimpleTableService implements TableService {
    protected TableFactory tableFactory;
    protected ActiveState<Table> activeState;
    protected TableRepository tableRepository;
    protected DatabaseDriverManager databaseDriverManager;
    protected ProjectService projectService;
    
    protected ChangeListener<Project> changeProjectListener;

    public SimpleTableService(
        TableFactory tableFactory,
        TableRepository tableRepository,
        ActiveState<Table> activeState,
        DatabaseDriverManager databaseDriverManager,
        ProjectService projectService
    ) {
        this.tableFactory = tableFactory;
        this.activeState = activeState;
        this.tableRepository = tableRepository;
        this.databaseDriverManager = databaseDriverManager;
        this.projectService = projectService;

        this.changeProjectListener = (ObservableValue<? extends Project> observable, Project oldValue, Project newValue) -> {
            this.onProjectActivate(newValue);
        };
    }

    protected void onProjectActivate(Project project) {
        String repositryKey = project.getName();

        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(project.getDatabase());
        databaseDriver.connect();

        List<Table> tables = new ArrayList<>();
        for (String tableName : databaseDriver.getTables()) {
            Table dbValue = this.tableFactory.createNotChanged(
                project,
                tableName
            );

            tables.add(
                this.mergeTable(dbValue, this.tableRepository.get(repositryKey, dbValue.getOriginal().getName()))
            );
        }

        for (Table table : this.tableRepository.getList(repositryKey)) {
            if (!table.getCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            tables.add(table);
        }
        this.tableRepository.setList(repositryKey, tables);

        this.activeState.setListAll(
            this.tableRepository.getList(repositryKey)
        );
    }

    @Override
    public void start() {
        this.projectService.getOpened()
            .addListener(this.changeProjectListener);
    }

    @Override
    public void stop() {
        this.projectService.getOpened()
            .removeListener(this.changeProjectListener);
    }

    @Override
    public ActiveState<Table> getActiveState() {
        return this.activeState;
    }

    @Override
    public TableFactory getFactory() {
        return this.tableFactory;
    }

    @Override
    public TableRepository getRepository() {
        return this.tableRepository;
    }

    protected Table mergeTable(Table dbValue, Table repositoryValue) {
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
        return repositoryValue;
    }
}