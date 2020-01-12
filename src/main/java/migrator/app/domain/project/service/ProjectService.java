package migrator.app.domain.project.service;

import java.util.Collection;

import migrator.app.boot.Container;
import migrator.app.domain.project.action.ProjectDeselectHandler;
import migrator.app.domain.project.action.ProjectNewHandler;
import migrator.app.domain.project.action.ProjectOpenHandler;
import migrator.app.domain.project.action.ProjectRemoveHandler;
import migrator.app.domain.project.action.ProjectSelectHandler;
import migrator.app.domain.project.model.Project;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.storage.Storage;

public class ProjectService implements Service {
    private EventDispatcher dispatcher;
    private Storage<Collection<Project>> projectStorage;
    private ProjectStore projectStore;

    private EventHandler projectNewHandler;
    private EventHandler projectSelectHandler;
    private EventHandler projectDeselectHandler;
    private EventHandler projectRemoveHandler;
    private EventHandler projectOpenHandler;

    public ProjectService(Container container) {
        this.dispatcher = container.dispatcher();
        this.projectStorage = container.projectStorage();
        this.projectStore = container.projectStore();

        this.projectNewHandler = new ProjectNewHandler(
            container.projectStore(),
            container.projectFactory()
        );
        this.projectSelectHandler = new ProjectSelectHandler(
            container.projectStore()
        );
        this.projectDeselectHandler = new ProjectDeselectHandler(
            container.projectStore()
        );
        this.projectRemoveHandler = new ProjectRemoveHandler(
            container.projectStore()
        );
        this.projectOpenHandler = new ProjectOpenHandler(
            container.projectStore()
        );
    }

    @Override
    public void start() {
        this.dispatcher.register("project.new", this.projectNewHandler);
        this.dispatcher.register("project.select", this.projectSelectHandler);
        this.dispatcher.register("project.remove", this.projectRemoveHandler);
        this.dispatcher.register("project.deselect", this.projectDeselectHandler);
        this.dispatcher.register("project.open", this.projectOpenHandler);

        this.projectStore.addAll(
            this.projectStorage.load()
        );
    }

    @Override
    public void stop() {
        dispatcher.unregister("project.new", this.projectNewHandler);
        dispatcher.unregister("project.select", this.projectSelectHandler);
        this.dispatcher.unregister("project.remove", this.projectRemoveHandler);
        this.dispatcher.unregister("project.deselect", this.projectDeselectHandler);
        this.dispatcher.unregister("project.open", this.projectOpenHandler);

        this.projectStorage.store(
            this.projectStore.getList()
        );
    }
}