package migrator.app.gui.service;

import javafx.stage.Stage;
import migrator.app.boot.Container;
import migrator.app.gui.GuiContainer;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.service.route.RouteStore;
import migrator.app.gui.service.route.SimpleRouteStore;
import migrator.app.gui.service.toast.PermanentToastStore;
import migrator.app.gui.service.toast.ToastStore;
import migrator.app.service.Service;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class GuiService implements Service {
    private ToastService toastService;
    private WindowService windowService;
    private RouteService routeService;
    private HotkeyService hotkeyService;

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
            routeStore.getActive(),
            container.persistantsystem()
        );
        this.hotkeyService = new HotkeyService(guiContainer, primaryStage);
        guiContainer.hotkeyContainer().hotkeysService()
            .connectKeyboard("find", "CTRL+F");
        guiContainer.hotkeyContainer().hotkeysService()
            .connectKeyboard("cancel", "ESCAPE");

        this.routeService = new RouteService(dispatcher, routeStore);
        routeStore.addRoute("projects", (RouteView) guiContainer.viewFactories().createProjects());
        routeStore.addRoute("project", (RouteView) guiContainer.viewFactories().createProject());
        routeStore.addRoute("table", (RouteView) guiContainer.viewFactories().createTable());
        routeStore.addRoute("commit", (RouteView) guiContainer.viewFactories().createCommit());

        container.projectStore().getOpened().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                dispatcher.dispatch(new RouteChangeEvent("projects"));
                dispatcher.dispatch(new SimpleEvent<>("table.deselect"));
                return;
            }
            dispatcher.dispatch(new RouteChangeEvent("project"));
        });
        container.tableContainer().tableStore().getSelected().addListener((observable, oldValue, newValue) -> {
            if (container.projectStore().getOpened().get() == null) {
                return;
            }
            Event<?> event = null;
            if (newValue == null) {
                event = new RouteChangeEvent("project");
            } else {
                event = new RouteChangeEvent("table");
            }
            dispatcher.dispatch(event);
        });
    }

    @Override
    public void start() {
        this.toastService.start();
        this.routeService.start();
        this.windowService.start();
        this.hotkeyService.start();
    }

    @Override
    public void stop() {
        this.toastService.stop();
        this.routeService.stop();
        this.windowService.stop();
        this.hotkeyService.stop();
    }
}