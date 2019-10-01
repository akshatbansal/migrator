package migrator.table.javafx;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.table.component.ColumnForm;
import migrator.table.model.Column;
import migrator.table.service.ColumnService;

public class JavafxColumnForm implements ColumnForm {
    protected Node node;
    protected ColumnService columnService;
    protected Column column;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> format;
    @FXML protected TextField defaultText;
    @FXML protected CheckBox nullCheckbox;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxColumnForm(ColumnService columnService) {
        this.columnService = columnService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/column/form.fxml");

        this.removeButton = new Button("Remove");
        this.removeButton.getStyleClass().addAll("btn-danger");
        this.removeButton.setOnAction((event) -> {
            this.delete();
        });

        this.restoreButton = new Button("Restore");
        this.restoreButton.getStyleClass().addAll("btn-secondary");
        this.restoreButton.setOnAction((event) -> {
            this.restore();
        });
    }

    public void setColumn(Column column) {
        this.column = column;
        if (column == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(column.nameProperty());
        this.format.valueProperty().bindBidirectional(column.formatProperty());
        this.defaultText.textProperty().bindBidirectional(column.defaultValueProperty());
        this.nullCheckbox.selectedProperty().bindBidirectional(column.enableNullProperty());
        this.column.getChangeCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        });
        this.onChangeTypeChange(column.getChangeCommand().getType());
    }

    protected void onChangeTypeChange(String changeType) {
        this.manageBox.getChildren().clear();
        if (changeType != ChangeCommand.DELETE) {
            this.manageBox.getChildren().add(this.removeButton);
        }
        if (changeType == ChangeCommand.DELETE || changeType == ChangeCommand.UPDATE) {
            this.manageBox.getChildren().add(this.restoreButton);
        }
    }

    @FXML public void initialize() {
        this.format.getItems().setAll(
            "string",
            "boolean",
            "integer",
            "smallint",
            "longint",
            "datetime"
        );
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    public void delete() {
        if (this.column.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.columnService.remove(this.column);
            return;
        }
        this.column.delete();
    }

    public void restore() {
        this.column.restore();
    }

    @FXML public void close() {
        // this.connectionsService.select(null);
    }
}