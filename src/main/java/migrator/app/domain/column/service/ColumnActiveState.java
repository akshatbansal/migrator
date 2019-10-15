package migrator.app.domain.column.service;

import javafx.beans.property.ObjectProperty;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.router.ActiveRoute;
import migrator.lib.modelstorage.SimpleActiveState;

public class ColumnActiveState extends SimpleActiveState<Column> {
    protected ColumnRepository repository;
    protected TableActiveState tableActiveState;
    protected ActiveRoute activeRoute;
    protected ObjectProperty<Project> openedProject;

    public ColumnActiveState(
        ColumnRepository repository,
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
    public void add(Column item) {
        this.repository.add(this.getRepositoryKey(), item);
        super.add(item);
    }

    @Override
    public void remove(Column item) {
        this.repository.remove(this.getRepositoryKey(), item);
        super.remove(item);
    }

    @Override
    public void activate(Column value) {
        super.activate(value);
        if (value != null) {
            this.activeRoute.changeTo("column.view", value);
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.activeRoute.changeTo("table.view", this.tableActiveState.getActive().get());
    }
}