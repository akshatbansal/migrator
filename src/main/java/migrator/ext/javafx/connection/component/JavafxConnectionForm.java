package migrator.ext.javafx.connection.component;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import migrator.app.Container;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.connection.component.ConnectionForm;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxConnectionForm extends ViewComponent implements ConnectionForm {
    protected ConnectionService connectionsService;
    protected DatabaseDriverManager databaseDriverManager;
    protected ActiveRoute activeRoute;
    protected Connection connection;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> driver;
    @FXML protected TextField host;
    @FXML protected TextField port;
    @FXML protected TextField user;
    @FXML protected PasswordField password;

    public JavafxConnectionForm(ViewLoader viewLoader, Connection connection, Container container) {
        super(viewLoader);
        this.connectionsService = container.getConnectionService();
        this.activeRoute = container.getActiveRoute();
        this.databaseDriverManager = container.getDatabaseDriverManager();
        
        this.loadView("/layout/connection/form.fxml");

        this.setConnection(connection);
    }

    protected void setConnection(Connection connection) {
        this.connection = connection;
        if (connection == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(connection.nameProperty());
        this.host.textProperty().bindBidirectional(connection.hostProperty());
        this.port.textProperty().bindBidirectional(connection.portProperty());
        this.driver.valueProperty().bindBidirectional(connection.driverProperty());
        this.user.textProperty().bindBidirectional(connection.userProperty());
        this.password.textProperty().bindBidirectional(connection.passwordProperty());
    }

    @FXML public void initialize() {
        this.driver.getItems().setAll(this.databaseDriverManager.getDriverNames());
    }

    @FXML public void delete() {
        this.connectionsService.remove(this.connectionsService.getSelected().get());
    }

    @FXML public void close() {
        this.connectionsService.select(null);
    }

    @FXML public void connect() {
        this.connectionsService.connect(this.connection);
        this.activeRoute.changeTo("connection.view", this.connection);
    }
}