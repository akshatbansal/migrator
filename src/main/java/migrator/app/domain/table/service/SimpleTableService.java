package migrator.app.domain.table.service;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import migrator.app.database.driver.DatabaseDriver;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.TableRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.ActiveState;
import migrator.lib.diff.CompareListDiff;
import migrator.lib.diff.ListDiff;

public class SimpleTableService implements TableService {
    protected TableFactory tableFactory;
    protected ActiveState<Table> activeState;
    protected TableRepository tableRepository;
    protected DatabaseDriverManager databaseDriverManager;
    protected ProjectService projectService;
    protected ActiveRoute activeRoute;
    
    protected ChangeListener<Project> changeProjectListener;

    public SimpleTableService(
        TableFactory tableFactory,
        TableRepository tableRepository,
        ActiveState<Table> activeState,
        DatabaseDriverManager databaseDriverManager,
        ProjectService projectService,
        ActiveRoute activeRoute
    ) {
        this.tableFactory = tableFactory;
        this.activeState = activeState;
        this.tableRepository = tableRepository;
        this.databaseDriverManager = databaseDriverManager;
        this.projectService = projectService;
        this.activeRoute = activeRoute;

        this.changeProjectListener = (ObservableValue<? extends Project> observable, Project oldValue, Project newValue) -> {
            this.onProjectActivate(newValue);
        };
    }

    protected void onProjectActivate(Project project) {
        if (project == null) {
            this.activeState.deactivate();
            return;
        }

        DatabaseDriver databaseDriver  = this.databaseDriverManager
            .createDriver(project.getDatabase());
        databaseDriver.connect();

        List<Table> dbList = databaseDriver.getTables();
        for (Table t : dbList) {
            t.setProjectId(project.getId());
        }

        this.merge(
            dbList,
            this.tableRepository.findByProject(project.getId())
        );

        this.activeState.setListAll(
            this.tableRepository.findByProject(project.getId())
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
    public void add(Table table) {
        this.activeState.add(table);
    }

    @Override
    public void activate(Table table) {
        this.activeState.activate(table);
    }

    @Override
    public void addAndActivate(Table table) {
        this.add(table);
        this.activate(table);
    }

    @Override
    public void remove(Table table) {
        this.activeState.remove(table);
    }

    @Override
    public ObservableList<Table> getAll() {
        return this.activeState.getList();
    }

    protected void merge(List<Table> dbList, List<Table> repoList) {
        ListDiff<Table> diff = new CompareListDiff<>(dbList, repoList, (Table a, Table b) -> {
            if (a.getChangeCommand().isType(ChangeCommand.CREATE)) {
                return a.getChange().getName().equals(
                    b.getChange().getName()
                );
            }
            return a.getOriginal().getName().equals(
                b.getOriginal().getName()
            );
        });
        for (List<Table> tablePair : diff.getCommon()) {
            if (tablePair.get(1).getChangeCommand().isType(ChangeCommand.CREATE)) {
                this.tableRepository.removeWith(tablePair.get(1));
                this.tableRepository.addWith(tablePair.get(0));
            } else {
                tablePair.get(1).updateOriginal(
                    tablePair.get(0).getOriginal()
                );
            }
        }
        for (Table table : diff.getLeftMissing()) {
            if (table.getChangeCommand().isType(ChangeCommand.CREATE)) {
                continue;
            }
            this.tableRepository.removeWith(table);
        }
        for (Table table : diff.getRightMissing()) {
            this.tableRepository.addWith(table);
        }
    }
}