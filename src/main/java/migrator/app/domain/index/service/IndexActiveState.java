package migrator.app.domain.index.service;

import javafx.beans.property.ObjectProperty;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class IndexActiveState extends SimpleActiveState<Index> {
    protected IndexRepository repository;
    protected TableActiveState tableActiveState;
    protected ActiveRoute activeRoute;
    protected ObjectProperty<Project> openedProject;

    public IndexActiveState(
        IndexRepository repository,
        TableActiveState tableActiveState,
        ActiveRoute activeRoute,
        ProjectService projectService
    ) {
        super();
        this.repository = repository;
        this.tableActiveState = tableActiveState;
        this.activeRoute = activeRoute;
        this.openedProject = projectService.getOpened();
    }

    protected String getRepositoryKey() {
        Table table = this.tableActiveState.getActive().get();
        return this.openedProject.get().getId() + "." + table.getId();
    }

    @Override
    public void add(Index item) {
        this.repository.add(this.getRepositoryKey(), item);
        super.add(item);
    }

    @Override
    public void remove(Index item) {
        this.repository.remove(this.getRepositoryKey(), item);
        super.remove(item);
    }

    @Override
    public void activate(Index value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("index.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.view", this.tableActiveState.getActive().get());
    }

    @Override
    protected void applyFilter() {
        this.list.setAll(this.fullList);
    }
}