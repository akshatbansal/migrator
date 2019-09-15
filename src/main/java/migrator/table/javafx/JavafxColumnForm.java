package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeService;
import migrator.table.component.ColumnForm;

public class JavafxColumnForm implements ColumnForm {
    protected Node node;
    protected ChangeService changeService;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> format;

    public JavafxColumnForm(ChangeService changeService) {
        this.changeService = changeService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/column/form.fxml");
        // this.changeService.getSelected().addListener((ObservableValue<? extends Connection> observable, Connection oldValue, Connection newValue) -> {
        //     this.setConnection(newValue);
        // });
    }

    // protected void setConnection(Connection connection) {
    //     if (connection == null) {
    //         return;
    //     }
    //     this.name.textProperty().bindBidirectional(connection.nameProperty());
    //     this.format.valueProperty().bindBidirectional(connection.driverProperty());
    // }

    @FXML public void initialize() {
        this.format.getItems().setAll("string", "boolean", "integer", "smallint", "longint");
        // Connection connection = this.connectionsService.getSelected().get();
        // if (connection != null) {
        //     this.setConnection(connection);
        // }
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void delete() {
        // this.connectionsService.remove(this.connectionsService.getSelected().get());
    }

    @FXML public void close() {
        // this.connectionsService.select(null);
    }

    @FXML public void create() {
        // this.router.show("databases", this.connectionsService.getSelected().get());
    }
}