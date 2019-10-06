package migrator;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import migrator.app.Bootstrap;
import migrator.app.Gui;
import migrator.app.domain.connection.model.Connection;
import migrator.ext.javafx.JavafxGui;
import migrator.ext.mysql.MysqlExtension;
import migrator.ext.phinx.PhinxExtension;
import migrator.ext.php.PhpExtension;
import migrator.app.Container;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.BasicRouter;
import migrator.router.Router;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Bootstrap bootstrap = new Bootstrap(
            Arrays.asList(
                new PhinxExtension(),
                new MysqlExtension(),
                new PhpExtension()
            )
        );

        Container container = bootstrap.getContainer();

        // Seed data
        container.getConnectionService()
            .add(new Connection("localhost"));

        Gui gui = new JavafxGui(container);
        
        Router router = new BasicRouter();
        



        container.getActiveRoute().routeProperty()
            .addListener((obs, ol, ne) -> {
                router.show(ne.getName(), ne.getValue());
            });

        // migrator.app.Router appRouter = new migrator.app.Router(
        //     new ConnectionRoute(
        //         new ConnectionIndexRoute(gui.getConnectionKit(), )
        //     )
        // )
        MainController mainController = new MainController(container, gui, router);
        
        Parent root = (Parent) ControllerHelper.createViewNode(mainController, "/layout/main.fxml");        
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().addAll(
            getClass().getResource("/styles/layout.css").toExternalForm(),
            getClass().getResource("/styles/text.css").toExternalForm(),
            getClass().getResource("/styles/button.css").toExternalForm(),
            getClass().getResource("/styles/table.css").toExternalForm(),
            getClass().getResource("/styles/card.css").toExternalForm(),
            getClass().getResource("/styles/form.css").toExternalForm(),
            getClass().getResource("/styles/scroll.css").toExternalForm(),
            getClass().getResource("/styles/main.css").toExternalForm()
        );
        primaryStage.setTitle("Migrator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
