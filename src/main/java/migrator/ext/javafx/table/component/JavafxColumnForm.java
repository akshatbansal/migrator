package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.Container;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxColumnForm extends ViewComponent implements ColumnForm {
    protected ColumnService columnService;
    protected ActiveRoute activeRoute;
    protected TableService tableService;
    protected Column column;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> format;
    @FXML protected TextField defaultText;
    @FXML protected CheckBox nullCheckbox;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxColumnForm(Column column, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.columnService = container.getColumnService();
        this.tableService = container.getTableService();
        this.activeRoute = container.getActiveRoute();

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

        this.loadView("/layout/table/column/form.fxml");
        this.setColumn(column);
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

    public void delete() {
        if (this.column.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.columnService.unregister(this.column);
            this.close();
            return;
        }
        this.column.delete();
    }

    public void restore() {
        this.column.restore();
    }

    @FXML public void close() {
        this.activeRoute.changeTo("table.view", this.tableService.getSelected().get());
    }
}