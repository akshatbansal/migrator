package migrator;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import migrator.app.BusinessLogic;
import migrator.app.Gui;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.extension.ConfigContainer;
import migrator.app.extension.Extension;
import migrator.app.migration.Migration;
import migrator.ext.javafx.JavafxGui;
import migrator.ext.mysql.MysqlExtension;
import migrator.ext.phinx.PhinxExtension;
import migrator.app.Container;
import migrator.javafx.helpers.ControllerHelper;
import migrator.javafx.helpers.ResourceView;
import migrator.router.BasicRouter;
import migrator.router.Router;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigContainer configContainer = new ConfigContainer();

        List<Extension> extensions = new ArrayList<>();
        extensions.add(new PhinxExtension());
        extensions.add(new MysqlExtension());
        for (Extension extension : extensions) {
            extension.load(configContainer);
        }

        Migration migration = new Migration(configContainer.getMigrationConfig());
        DatabaseDriverManager databaseDriverManager = new DatabaseDriverManager(configContainer.getDatabaseDriverConfig());
        BusinessLogic businessLogic = new BusinessLogic(
            databaseDriverManager,
            new ConnectionService(
                new Connection("localhost")
            )
        );
        Router router = new BasicRouter();
        Gui gui = new JavafxGui(
            new ResourceView(),
            router,
            businessLogic,
            migration,
            databaseDriverManager
        );
        Container container = new Container(
            businessLogic,
            gui,
            router,
            migration,
            databaseDriverManager
        );
        MainController mainController = new MainController(container);
        
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
