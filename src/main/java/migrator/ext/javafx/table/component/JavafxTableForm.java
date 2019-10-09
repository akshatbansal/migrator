package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.Container;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableForm extends ViewComponent implements TableForm {
    protected TableService tableService;
    protected ActiveRoute activeRoute;
    protected Table table;

    @FXML protected TextField name;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxTableForm(Table table, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.tableService = container.getTableService();
        this.activeRoute = container.getActiveRoute();
        this.table = table;

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

        this.loadView("/layout/table/form.fxml");
    }

    @FXML public void initialize() {
        this.name.textProperty().bindBidirectional(this.table.nameProperty());

        this.table.getChange().getCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        });
        this.onChangeTypeChange(this.table.getChange().getCommand().getType());
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

    @FXML public void delete() {
        if (this.table.getChange().getCommand().isType(ChangeCommand.CREATE)) {
            this.tableService.getActiveState().remove(this.table);
            this.activeRoute.changeTo("table.index");
            return;
        }
        this.table.getChange().getCommand().setType(ChangeCommand.DELETE);
    }

    public void restore() {
        this.table.restore();
    }
}