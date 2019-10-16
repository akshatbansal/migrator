package migrator;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import migrator.app.Bootstrap;
import migrator.app.Gui;
import migrator.app.Router;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
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
import migrator.ext.sentry.SentryExtension;
import migrator.lib.persistance.ListPersistance;
import migrator.lib.persistance.Persistance;
import migrator.app.Container;
import migrator.app.EnviromentConfig;

public class JavafxApplication extends Application {
    protected Persistance<List<Project>> projectsPersistance;
    protected Container container;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.projectsPersistance = new ListPersistance<>("project.list");

        String env = "production";
        if (System.getenv("MIGRATOR_ENV") != null) {
            env = System.getenv("MIGRATOR_ENV");
        }
        EnviromentConfig enviromentConfig = new EnviromentConfig(env);

        Bootstrap bootstrap = new Bootstrap(
            Arrays.asList(
                new PhinxExtension(),
                new MysqlExtension(),
                new PhpExtension(),
                new SentryExtension(enviromentConfig.getProperties("sentry"))
            )
        );
        this.container = bootstrap.getContainer();
        this.container.getTableService().start();
        this.container.getColumnService().start();
        this.container.getIndexService().start();

        // Seed data from persistance
        this.seed();

        ViewLoader viewLoader = new ViewLoader();
        Gui gui = new JavafxGui(this.container, viewLoader, primaryStage);
        MainController mainController = new MainController(viewLoader, this.container);

        JavafxLayout layout = new JavafxLayout(mainController.getBodyPane(), mainController.getSidePane());

        Router router = new Router(this.container.getActiveRoute());
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

    protected void seed() {
        this.container.getProjectService().getList()
            .setAll(
                this.projectsPersistance.load(new ArrayList<>())
            );
        
        for (Project project : this.container.getProjectService().getList()) {
            ListPersistance<Table> tableListPersistance = new ListPersistance<>("tables." + project.getId());
            this.container.getTableRepository().setList(project.getId(), tableListPersistance.load(new ArrayList<>()));
            for (Table table : this.container.getTableRepository().getList(project.getId())) {
                ListPersistance<Column> columnListPersistance = new ListPersistance<>("columns." + project.getId() + "." + table.getId());
                List<Column> columns = columnListPersistance.load(new ArrayList<>());
                for (Column column : columns) {
                    column.setColumnFormatManager(
                        this.container.getColumnFormatManager()
                    );
                }
                this.container.getColumnRepository().setList(project.getId() + "." + table.getId(), columns);

                ListPersistance<Index> indexListPersistance = new ListPersistance<>("indexes." + project.getId() + "." + table.getId());
                this.container.getIndexRepository().setList(project.getId() + "." + table.getId(), indexListPersistance.load(new ArrayList<>()));

                table.setColumns(
                    this.container.getColumnRepository().getList(project.getId() + "." + table.getId())
                );
                table.setIndexes(
                    this.container.getIndexRepository().getList(project.getId() + "." + table.getId())
                );
            }
        }
    }

    @Override
    public void stop() throws Exception {
        this.projectsPersistance.store(
            this.container.getProjectService().getList()
        );
        for (Project project : this.container.getProjectService().getList()) {
            ListPersistance<Table> listPersistance = new ListPersistance<>("tables." + project.getId());
            listPersistance.store(
                this.container.getTableRepository().getList(project.getId())
            );
            for (Table table : this.container.getTableRepository().getList(project.getId())) {
                ListPersistance<Column> columnListPersistance = new ListPersistance<>("columns." + project.getId() + "." + table.getId());
                columnListPersistance.store(
                    this.container.getColumnRepository().getList(project.getId() + "." + table.getId())
                );
            }
            for (Table table : this.container.getTableRepository().getList(project.getId())) {
                ListPersistance<Index> indexListPersistance = new ListPersistance<>("indexes." + project.getId() + "." + table.getId());
                indexListPersistance.store(
                    this.container.getIndexRepository().getList(project.getId() + "." + table.getId())  
                );
            }
        }
        super.stop();
    }
}
