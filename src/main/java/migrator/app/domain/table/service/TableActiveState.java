package migrator.app.domain.table.service;

import migrator.app.domain.project.service.ProjectService;
import migrator.app.domain.table.model.Table;
import migrator.lib.modelstorage.SimpleActiveState;

public class TableActiveState extends SimpleActiveState<Table> {
    protected TableRepository repository;
    protected ProjectService projectService;

    public TableActiveState(TableRepository repository, ProjectService projectService) {
        super();
        this.repository = repository;
        this.projectService = projectService;
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
}