package migrator.app.gui.view.main;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.route.RouterComponent;
import migrator.app.gui.component.toast.ToastListComponent;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.lib.dispatcher.EventDispatcher;

public class MainView extends SimpleView implements View {
    protected ToastListComponent toastListComponent;
    protected RouterComponent routerComponent;

    @FXML protected VBox routerPane;
    @FXML protected VBox toastPane;

    public MainView(EventDispatcher dispatcher, ObjectProperty<View> routerView) {
        super();

        this.toastListComponent = new ToastListComponent();
        this.toastListComponent.outputs().addListener((observable, oldValue, newValue) -> {
            dispatcher.dispatch(newValue);
        });
        
        this.routerComponent = new RouterComponent();
        this.routerComponent.bind(routerView);

        this.loadFxml("/layout/main.fxml");

        this.toastPane.getChildren().setAll(
            this.toastListComponent.getNode()
        );

        this.routerPane.getChildren().setAll(
            this.routerComponent.getNode()
        );
    }

    public void bindToasts(ObservableList<Toast> toasts) {
        this.toastListComponent.bind(toasts);
    }
}