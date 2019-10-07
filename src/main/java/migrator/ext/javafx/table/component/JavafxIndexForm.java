package migrator.ext.javafx.table.component;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.Container;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.ColumnService;
import migrator.app.domain.table.service.IndexService;
import migrator.app.domain.table.service.TableService;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxIndexForm extends ViewComponent implements IndexForm {
    protected IndexService indexService;
    protected ColumnService columnService;
    protected TableService tableService;
    protected ActiveRoute activeRoute;
    protected Index index;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> column1;
    @FXML protected ComboBox<String> column2;
    @FXML protected ComboBox<String> column3;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxIndexForm(Index index, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.indexService = container.getIndexService();
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

        this.loadView("/layout/table/index/form.fxml");
        this.setIndex(index);
    }

    public void setIndex(Index index) {
        this.index = index;
        if (index == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(index.nameProperty());
        this.column1.valueProperty().bindBidirectional(index.columnPropertyOrCreate(0));
        this.column2.valueProperty().bindBidirectional(index.columnPropertyOrCreate(1));
        this.column3.valueProperty().bindBidirectional(index.columnPropertyOrCreate(2));

        if (this.index.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.name.disableProperty().set(false);
            this.column1.disableProperty().set(false);
            this.column2.disableProperty().set(false);
            this.column3.disableProperty().set(false);
        }

        this.index.getChangeCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        });
        this.onChangeTypeChange(this.index.getChangeCommand().getType());
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
        List<String> columnNames = new ArrayList<>();
        columnNames.add("");
        for (Column column : this.columnService.getList()) {
            columnNames.add(column.getName());
        }
        this.column1.getItems().setAll(columnNames);
        this.column2.getItems().setAll(columnNames);
        this.column3.getItems().setAll(columnNames);
    }

    @FXML public void delete() {
        if (this.index.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.indexService.remove(this.index);
            this.close();
            return;
        }
        this.index.delete();
    }

    @FXML public void close() {
        this.activeRoute.changeTo("table.view", this.tableService.getSelected().get());
    }

    @FXML public void restore() {
        this.index.restore();
    }
}