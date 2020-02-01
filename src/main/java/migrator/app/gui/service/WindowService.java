package migrator.app.gui.service;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.view.main.MainView;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.persistantsystem.Persistantsystem;

public class WindowService implements Service {
    private Stage primaryStage;
    private MainView mainView;
    private Persistantsystem persistantsystem;

    public WindowService(
        EventDispatcher dispatcher,
        Stage primaryStage,
        ObservableList<Toast> toasts,
        ObjectProperty<RouteView> routerView,
        Persistantsystem persistantsystem
    ) {
        this.primaryStage = primaryStage;
        this.mainView = new MainView(dispatcher, routerView);
        this.mainView.bindToasts(toasts);
        this.persistantsystem = persistantsystem;
    }

    @Override
    public void stop() {
        this.persistantsystem.putDouble("window.width", this.primaryStage.getWidth());
        this.persistantsystem.putDouble("window.height", this.primaryStage.getHeight());
        this.persistantsystem.putDouble("window.x", this.primaryStage.getX());
        this.persistantsystem.putDouble("window.y", this.primaryStage.getY());
        this.persistantsystem.putBoolean("window.fullScreen", this.primaryStage.isFullScreen());
    }

    @Override
    public void start() {
        Scene scene = new Scene((Pane) this.mainView.getNode());

        this.primaryStage.setWidth(
            this.persistantsystem.getDouble("window.width", 1280)
        );
        this.primaryStage.setHeight(
            this.persistantsystem.getDouble("window.height", 720)
        );
        this.primaryStage.setX(
            this.persistantsystem.getDouble("window.x", 100)
        );
        this.primaryStage.setY(
            this.persistantsystem.getDouble("window.y", 100)
        );
        this.primaryStage.setFullScreen(
            this.persistantsystem.getBoolean("window.fullScreen", false)
        );

        scene.getStylesheets().addAll(
            getClass().getResource("/styles/layout.css").toExternalForm(),
            getClass().getResource("/styles/text.css").toExternalForm(),
            getClass().getResource("/styles/button.css").toExternalForm(),
            getClass().getResource("/styles/table.css").toExternalForm(),
            getClass().getResource("/styles/card.css").toExternalForm(),
            getClass().getResource("/styles/toast.css").toExternalForm(),
            getClass().getResource("/styles/form.css").toExternalForm(),
            getClass().getResource("/styles/scroll.css").toExternalForm(),
            getClass().getResource("/styles/main.css").toExternalForm()
        );
        
        this.primaryStage.setTitle("Migrator");
        this.primaryStage.getIcons().add(
            new Image(getClass().getResourceAsStream("/images/logo.png"))
        );
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}