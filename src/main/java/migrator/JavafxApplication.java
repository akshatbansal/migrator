package migrator;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migrator.app.Bootstrap;
import migrator.app.BusinessLogic;
import migrator.app.Gui;
import migrator.app.Router;
import migrator.app.domain.connection.model.Connection;
import migrator.ext.javafx.JavafxGui;
import migrator.ext.javafx.MainController;
import migrator.ext.javafx.change.route.CommitViewRoute;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.connection.route.ConnectionIndexRoute;
import migrator.ext.javafx.connection.route.ConnectionViewRoute;
import migrator.ext.javafx.database.route.DatabaseIndexRoute;
import migrator.ext.javafx.project.route.ProjectIndexRoute;
import migrator.ext.javafx.project.route.ProjectViewRoute;
import migrator.ext.javafx.table.route.ColumnViewRoute;
import migrator.ext.javafx.table.route.IndexViewRoute;
import migrator.ext.javafx.table.route.TableIndexRoute;
import migrator.ext.javafx.table.route.TableViewRoute;
import migrator.ext.mysql.MysqlExtension;
import migrator.ext.phinx.PhinxExtension;
import migrator.ext.php.PhpExtension;
import migrator.app.Container;

public class JavafxApplication extends Application {
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
        new BusinessLogic(container);

        // Seed data
        container.getConnectionService()
            .add(new Connection("localhost"));

        ViewLoader viewLoader = new ViewLoader();
        Gui gui = new JavafxGui(container, viewLoader);
        MainController mainController = new MainController(viewLoader);

        JavafxLayout layout = new JavafxLayout(mainController.getBodyPane(), mainController.getSidePane());

        Router router = new Router(container.getActiveRoute());
        router.connect(
            "connection.index",
            new ConnectionIndexRoute(gui.getConnectionKit(), layout)
        );
        router.connect(
            "connection.view",
            new ConnectionViewRoute(gui.getConnectionKit(), layout)
        );
        router.connect(
            "database.index",
            new DatabaseIndexRoute(gui.getDatabaseKit(), layout)
        );
        router.connect(
            "table.index",
            new TableIndexRoute(gui.getTableKit(), layout)
        );
        router.connect(
            "table.view",
            new TableViewRoute(gui.getTableKit(), layout)
        );
        router.connect(
            "column.view",
            new ColumnViewRoute(gui.getTableKit(), layout)
        );
        router.connect(
            "index.view",
            new IndexViewRoute(gui.getTableKit(), layout)
        );
        router.connect(
            "commit.view",
            new CommitViewRoute(gui.getChangeKit(), layout)
        );
        router.connect(
            "project.index",
            new ProjectIndexRoute(layout, gui.getProject())
        );
        router.connect(
            "project.view",
            new ProjectViewRoute(layout, gui.getProject())
        );

        container.getActiveRoute().changeTo("project.index");

        Scene scene = new Scene((Pane) mainController.getContent(), 1280, 720);
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
