package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.gui.column.format.ColumnFormat;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.migration.model.ChangeCommand;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxColumnForm extends ViewComponent implements ColumnForm {
    protected ColumnActiveState columnActiveState;
    protected TableActiveState tableActiveState;
    protected Column column;
    protected ColumnFormatCollection columnFormatCollection;

    @FXML protected TextField name;
    @FXML protected ComboBox<ColumnFormat> format;
    @FXML protected TextField defaultText;
    @FXML protected TextField length;
    @FXML protected TextField precision;
    @FXML protected CheckBox sign;
    @FXML protected CheckBox autoIncrement;
    @FXML protected CheckBox nullCheckbox;
    @FXML protected VBox paramsBox;
    @FXML protected HBox manageBox;
    @FXML protected HBox lengthRow;
    @FXML protected HBox precisionRow;
    @FXML protected HBox signRow;
    @FXML protected HBox autoIncrementRow;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxColumnForm(Column column, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.columnActiveState = container.getColumnActiveState();
        this.tableActiveState = container.getTableActiveState();
        this.columnFormatCollection = container.getGuiContainer().getColumnFormatCollection();

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

        this.format.getItems().setAll(
            this.columnFormatCollection.getObservable()
        );
        this.format.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            newValue.updateModel(this.column);
            this.column.formatProperty().set(newValue.getName());
        });

        this.setColumn(column);
    }

    public void setColumn(Column column) {
        this.column = column;
        if (column == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(column.nameProperty());
        this.defaultText.textProperty().bindBidirectional(column.defaultValueProperty());
        this.nullCheckbox.selectedProperty().bindBidirectional(column.enableNullProperty());
        this.length.textProperty().bindBidirectional(column.lengthProperty());
        this.precision.textProperty().bindBidirectional(column.precisionProperty());
        this.sign.selectedProperty().bindBidirectional(column.signProperty());
        this.autoIncrement.selectedProperty().bindBidirectional(column.autoIncrementProperty());

        this.lengthRow.managedProperty().bind(this.column.attribute("length"));
        this.lengthRow.visibleProperty().bind(this.column.attribute("length"));
        this.precisionRow.managedProperty().bind(this.column.attribute("precision"));
        this.precisionRow.visibleProperty().bind(this.column.attribute("precision"));
        this.signRow.managedProperty().bind(this.column.attribute("sign"));
        this.signRow.visibleProperty().bind(this.column.attribute("sign"));
        this.autoIncrementRow.managedProperty().bind(this.column.attribute("autoIncrement"));
        this.autoIncrementRow.visibleProperty().bind(this.column.attribute("autoIncrement"));

        this.column.formatProperty().addListener((obs, ol, ne) -> {
            this.onFormatChange(ne);
        });
        this.onFormatChange(this.column.formatProperty().get());

        this.column.getChangeCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        });
        this.onChangeTypeChange(column.getChangeCommand().getType());
    }

    protected void onFormatChange(String columnFormat) {
        for (ColumnFormat format : this.columnFormatCollection.getObservable()) {
            if (format.getName().equals(columnFormat)) {
                this.format.getSelectionModel().select(format);
            }
        }
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

    public void delete() {
        if (this.column.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.columnActiveState.remove(this.column);
            return;
        }
        this.column.delete();
    }

    public void restore() {
        this.column.restore();
    }

    @FXML public void close() {
        this.columnActiveState.deactivate();
    }
}