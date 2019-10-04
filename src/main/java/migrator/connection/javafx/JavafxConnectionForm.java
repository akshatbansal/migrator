package migrator.connection.javafx;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.connection.component.ConnectionForm;
import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionService;
import migrator.javafx.helpers.ControllerHelper;
import migrator.router.Router;

public class JavafxConnectionForm implements ConnectionForm {
    protected Node node;
    protected ConnectionService connectionsService;
    protected DatabaseDriverManager databaseDriverManager;
    protected Router router;
    protected Connection connection;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> driver;
    @FXML protected TextField host;
    @FXML protected TextField port;
    @FXML protected TextField user;
    @FXML protected PasswordField password;

    public JavafxConnectionForm(ConnectionService connectionService, Router router, DatabaseDriverManager databaseDriverManager) {
        this.connectionsService = connectionService;
        this.router = router;
        this.databaseDriverManager = databaseDriverManager;
        this.node = ControllerHelper.createViewNode(this, "/layout/connection/form.fxml");
        this.connectionsService.getSelected().addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
            this.setConnection(newValue);
        });
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
        Connection connection = this.connectionsService.getSelected().get();
        if (connection != null) {
            this.setConnection(connection);
        }
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void delete() {
        this.connectionsService.remove(this.connectionsService.getSelected().get());
    }

    @FXML public void close() {
        this.connectionsService.select(null);
    }

    @FXML public void connect() {
        this.connectionsService.connect(this.connection);
        this.router.show("databases", this.connection);
    }
}