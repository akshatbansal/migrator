package migrator;

import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Callback;
import migrator.connection.service.ConnectionService;
import migrator.database.service.DatabaseService;
import migrator.javafx.Container;
import migrator.javafx.helpers.ControllerHelper;
import migrator.persistance.ListPersistance;
import migrator.router.BasicRouter;
import migrator.router.Router;
import migrator.table.service.TableService;

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

        BusinessLogic businessLogic = new BusinessLogic();
        Router router = new BasicRouter();
        Gui gui = new Gui(router, businessLogic);
        Container container = new Container(businessLogic, gui, router);
        MainController mainController = new MainController(container);
        
        Parent root = (Parent) ControllerHelper.createViewNode(mainController, "/layout/main.fxml");        
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.setTitle("Migrator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
