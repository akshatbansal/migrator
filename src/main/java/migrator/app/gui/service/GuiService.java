package migrator.app.gui.service;

import javafx.stage.Stage;
import migrator.app.boot.Container;
import migrator.app.gui.GuiContainer;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.app.gui.service.route.RouteStore;
import migrator.app.gui.service.route.SimpleRouteStore;
import migrator.app.gui.service.toast.PermanentToastStore;
import migrator.app.gui.service.toast.ToastStore;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;

public class GuiService implements Service {
    private ToastService toastService;
    private WindowService windowService;
    private RouteService routeService;

    public GuiService(Container container, Stage primaryStage) {
        GuiContainer guiContainer = new GuiContainer(
            container,
            primaryStage
        );

        EventDispatcher dispatcher = container.dispatcher();
        RouteStore routeStore = new SimpleRouteStore();
        ToastStore toastStore = new PermanentToastStore();
        
        this.toastService = new ToastService(dispatcher, toastStore);
        this.windowService = new WindowService(
            dispatcher,
            primaryStage,
            toastStore.getList(),
            routeStore.getActive()
        );
        this.routeService = new RouteService(dispatcher, routeStore);

        routeStore.addRoute("projects", guiContainer.viewFactories().createProjects());
        routeStore.addRoute("project", guiContainer.viewFactories().createProject());
        routeStore.addRoute("table", guiContainer.viewFactories().createTable());

        container.projectStore().getOpened().addListener((observable, oldValue, newValue) -> {
            dispatcher.dispatch(
                new RouteChangeEvent("project")
            );
        });
        container.tableContainer().tableStore().getSelected().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            dispatcher.dispatch(
                new RouteChangeEvent("table")
            );
        });
    }

    @Override
    public void start() {
        this.toastService.start();
        this.routeService.start();
        this.windowService.start();
    }

    @Override
    public void stop() {
        this.toastService.stop();
        this.routeService.stop();
        this.windowService.stop();
    }
}