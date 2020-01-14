package migrator.app.gui.service;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.view.View;
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
    public void stop() {}

    @Override
    public void start() {
        Scene scene = new Scene((Pane) this.mainView.getNode(), 1280, 720);

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

        // scene.getRoot().setOnKeyPressed((e) -> {
        //     this.container.getHotkeyService()
        //         .keyPressed(
        //             container.getHotkeyFactory().create(e.getCode().getCode(), e.isControlDown(), e.isShiftDown())
        //         );
        // });
        
        this.primaryStage.setTitle("Migrator");
        this.primaryStage.getIcons().add(
            new Image(getClass().getResourceAsStream("/images/logo.png"))
        );
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
}