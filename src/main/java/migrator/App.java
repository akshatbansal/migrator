package migrator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionService;
import migrator.database.service.DatabaseServerConnectionFactory;
import migrator.javafx.Container;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.BasicRouter;
import migrator.router.Router;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ListPersistance<Object> listPersistance = new ListPersistance<>();

        // ObservableList<ObservableObjectValue<Connection>> connections = FXCollections.observableArrayList((List<ObservableObjectValue<Connection>>) listPersistance.load("Migrator.model.connections", new ArrayList<>()));
        // ObservableList<Connection> connections = FXCollections.observableArrayList(new Callback<Connection,Observable[]>() {
        //     @Override
        //     public Observable[] call(Connection param) {
        //         return param.extract();
        //     }
        // });
        // ModelRegistry.register("connections.list", connections);
        // ModelRegistry.register("connections.edit", new SimpleObjectProperty<Connection>());

        // connections.addListener((Change<? extends Object> change) -> {
        //     listPersistance.store("Migrator.model.connections", Arrays.asList(connections.toArray()));
        // });

        BusinessLogic businessLogic = new BusinessLogic(
            new DatabaseServerConnectionFactory(),
            new ConnectionService(
                new Connection("localhost")
            )
        );
        Router router = new BasicRouter();
        Gui gui = new Gui(router, businessLogic);
        Container container = new Container(businessLogic, gui, router);
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
