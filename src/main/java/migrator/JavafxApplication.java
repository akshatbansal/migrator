package migrator;

import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migrator.app.Bootstrap;
import migrator.app.Gui;
import migrator.app.Router;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.ext.javafx.JavafxGui;
import migrator.ext.javafx.MainController;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.route.CommitViewRoute;
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
        container.getTableService().start();
        container.getColumnService().start();
        container.getIndexService().start();

        // Seed data
        container.getProjectService()
            .add(
                new Project(
                    new DatabaseConnection(
                        new Connection("localhost"), 
                        "ovaldo"
                    ), 
                    "project#1",
                    "phinx",
                    "/home/arksys/Documents/peto/projekty/migrator-test"
                )
            );

        ViewLoader viewLoader = new ViewLoader();
        Gui gui = new JavafxGui(container, viewLoader, primaryStage);
        MainController mainController = new MainController(viewLoader, container);

        JavafxLayout layout = new JavafxLayout(mainController.getBodyPane(), mainController.getSidePane());

        Router router = new Router(container.getActiveRoute());
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
            new CommitViewRoute(gui.getProject(), layout)
        );
        router.connect(
            "project.index",
            new ProjectIndexRoute(
                layout,
                gui.getProject(),
                container.getProjectService(),
                container.getActiveRoute()
            )
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
            getClass().getResource("/styles/toast.css").toExternalForm(),
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
