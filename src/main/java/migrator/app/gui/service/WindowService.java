package migrator.app.gui.service;

import java.util.prefs.Preferences;

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

public class WindowService implements Service {
    private Stage primaryStage;
    private MainView mainView;

    public WindowService(
        EventDispatcher dispatcher,
        Stage primaryStage,
        ObservableList<Toast> toasts,
        ObjectProperty<RouteView> routerView
    ) {
        this.primaryStage = primaryStage;
        this.mainView = new MainView(dispatcher, routerView);
        this.mainView.bindToasts(toasts);
    }

    @Override
    public void stop() {
        Preferences.userRoot().putDouble("window.width", this.primaryStage.getWidth());
        Preferences.userRoot().putDouble("window.height", this.primaryStage.getHeight());
        Preferences.userRoot().putDouble("window.x", this.primaryStage.getX());
        Preferences.userRoot().putDouble("window.y", this.primaryStage.getY());
        Preferences.userRoot().putBoolean("window.fullScreen", this.primaryStage.isFullScreen());
    }

    @Override
    public void start() {
        Scene scene = new Scene((Pane) this.mainView.getNode());

        this.primaryStage.setWidth(
            Preferences.userRoot().getDouble("window.width", 1280)
        );
        this.primaryStage.setHeight(
            Preferences.userRoot().getDouble("window.height", 720)
        );
        this.primaryStage.setX(
            Preferences.userRoot().getDouble("window.x", 100)
        );
        this.primaryStage.setY(
            Preferences.userRoot().getDouble("window.y", 100)
        );
        this.primaryStage.setFullScreen(
            Preferences.userRoot().getBoolean("window.fullScreen", false)
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