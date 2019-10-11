package migrator.app.domain.table.service;

import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Table;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class TableActiveState extends SimpleActiveState<Table> {
    protected TableRepository repository;
    protected ProjectService projectService;
    protected ActiveRoute activeRoute;

    public TableActiveState(TableRepository repository, ProjectService projectService, ActiveRoute activeRoute) {
        super();
        this.repository = repository;
        this.projectService = projectService;
        this.activeRoute = activeRoute;
    }

    protected String getRepositoryKey() {
        return this.projectService.getOpened().get().getName();
    }

    @Override
    public void add(Table item) {
        this.repository.add(this.getRepositoryKey(), item);
        super.add(item);
    }

    @Override
    public void remove(Table item) {
        this.repository.remove(this.getRepositoryKey(), item);
        super.remove(item);
    }

    @Override
    public void activate(Table value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("table.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.index");
    }
}