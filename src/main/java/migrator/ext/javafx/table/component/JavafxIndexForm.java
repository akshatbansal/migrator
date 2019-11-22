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
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxIndexForm extends ViewComponent implements IndexForm {
    protected ColumnActiveState columnActiveState;
    protected TableActiveState tableActiveState;
    protected IndexActiveState indexActiveState;
    protected Index index;

    @FXML protected TextField name;
    @FXML protected ComboBox<ColumnProperty> column1;
    @FXML protected ComboBox<ColumnProperty> column2;
    @FXML protected ComboBox<ColumnProperty> column3;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxIndexForm(Index index, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.columnActiveState = container.getColumnActiveState();
        this.tableActiveState = container.getTableActiveState();
        this.indexActiveState = container.getIndexActiveState();

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

        this.column1.valueProperty().addListener((observable, oldValue, newValue) -> {
            index.setColumnAt(0, newValue);
        });
        this.column2.valueProperty().addListener((observable, oldValue, newValue) -> {
            index.setColumnAt(1, newValue);
        });
        this.column3.valueProperty().addListener((observable, oldValue, newValue) -> {
            index.setColumnAt(2, newValue);
        });
        this.column1.valueProperty().set(index.columnPropertyOrCreate(0));
        this.column2.valueProperty().set(index.columnPropertyOrCreate(1));
        this.column3.valueProperty().set(index.columnPropertyOrCreate(2));

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
        if (changeType.equals(ChangeCommand.DELETE) || changeType.equals(ChangeCommand.UPDATE)) {
            this.manageBox.getChildren().add(this.restoreButton);
        }
    }

    @FXML public void initialize() {
        List<ColumnProperty> columnOptions = new ArrayList<>();
        columnOptions.add(null);
        for (Column column : this.columnActiveState.getList()) {
            columnOptions.add(column.getChange());
        }
        this.column1.getItems().setAll(columnOptions);
        this.column2.getItems().setAll(columnOptions);
        this.column3.getItems().setAll(columnOptions);
    }

    @FXML public void delete() {
        if (this.index.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.indexActiveState.remove(this.index);
            return;
        }
        this.index.delete();
    }

    @FXML public void close() {
        this.indexActiveState.deactivate();
    }

    @FXML public void restore() {
        this.index.restore();
    }
}