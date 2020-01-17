package migrator.app.domain.project.service;

import java.util.Collection;

import javafx.beans.value.ChangeListener;
import migrator.app.boot.Container;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.action.ProjectCloseHandler;
import migrator.app.domain.project.action.ProjectDeselectHandler;
import migrator.app.domain.project.action.ProjectNewHandler;
import migrator.app.domain.project.action.ProjectOpenHandler;
import migrator.app.domain.project.action.ProjectRefreshHandler;
import migrator.app.domain.project.action.ProjectRemoveHandler;
import migrator.app.domain.project.action.ProjectSelectHandler;
import migrator.app.domain.project.model.Project;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;
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
    private EventHandler projectCloseHandler;
    private EventHandler projectRefreshHandler;
    private ChangeListener<ProjectContainer> openedProjectListener;

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
        this.projectCloseHandler = new ProjectCloseHandler(
            container.projectStore()
        );
        this.projectRefreshHandler = new ProjectRefreshHandler(
            container.tableContainer()
        );

        this.openedProjectListener = (observablr, oldValue, newValue) -> {
            this.dispatcher.dispatch(
                new SimpleEvent<>("project.refresh", newValue)
            );
        };
    }

    @Override
    public void start() {
        this.dispatcher.register("project.new", this.projectNewHandler);
        this.dispatcher.register("project.select", this.projectSelectHandler);
        this.dispatcher.register("project.remove", this.projectRemoveHandler);
        this.dispatcher.register("project.deselect", this.projectDeselectHandler);
        this.dispatcher.register("project.open", this.projectOpenHandler);
        this.dispatcher.register("project.close", this.projectCloseHandler);
        this.dispatcher.register("project.refresh", this.projectRefreshHandler);
        this.projectStore.getOpened().addListener(this.openedProjectListener);

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
        this.dispatcher.unregister("project.close", this.projectCloseHandler);
        this.dispatcher.unregister("project.refresh", this.projectRefreshHandler);
        this.projectStore.getOpened().removeListener(this.openedProjectListener);

        this.projectStorage.store(
            this.projectStore.getList()
        );
    }
}