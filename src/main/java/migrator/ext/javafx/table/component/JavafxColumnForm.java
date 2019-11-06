package migrator.ext.javafx.table.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.database.format.ColumnFormat;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableActiveState;
import migrator.app.migration.model.ChangeCommand;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.form.JavafxCheckbox;
import migrator.ext.javafx.component.form.JavafxTextInput;

public class JavafxColumnForm extends ViewComponent implements ColumnForm {
    protected ColumnActiveState columnActiveState;
    protected TableActiveState tableActiveState;
    protected ColumnFormatManager columnFormatManager;
    protected Column column;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> format;
    @FXML protected TextField defaultText;
    @FXML protected CheckBox nullCheckbox;
    @FXML protected VBox paramsBox;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxColumnForm(Column column, ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.columnActiveState = container.getColumnActiveState();
        this.tableActiveState = container.getTableActiveState();
        this.columnFormatManager = container.getColumnFormatManager();

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

        this.onFormatChange(
            this.column.getFormat()
        );
        
        this.column.formatProperty().addListener((obs, oldValue, newValue) -> {
            this.onFormatChange(newValue);
        });

        this.column.getChangeCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        });
        this.onChangeTypeChange(column.getChangeCommand().getType());
    }

    protected void onFormatChange(String format) {
        ColumnFormat columnFormat = this.columnFormatManager.getFormat(format);
        this.paramsBox.getChildren().clear();
        if (columnFormat != null) {
            if (columnFormat.hasLength()) {
                JavafxTextInput length = new JavafxTextInput(this.viewLoader, this.column.lengthProperty(), "length");
                this.paramsBox.getChildren().add(
                    (Node) length.getContent()
                );
            }
            if (columnFormat.hasPrecision()) {
                JavafxTextInput precision = new JavafxTextInput(this.viewLoader, this.column.precisionProperty(), "precision");
                this.paramsBox.getChildren().add(
                    (Node) precision.getContent()
                );
            }
            if (columnFormat.isSigned()) {
                JavafxCheckbox signed = new JavafxCheckbox(this.viewLoader, this.column.signProperty(), "signed");
                this.paramsBox.getChildren().add(
                    (Node) signed.getContent()
                );
            }
            if (columnFormat.hasAutoIncrement()) {
                JavafxCheckbox autoIncrement = new JavafxCheckbox(this.viewLoader, this.column.autoIncrementProperty(), "auto increment");
                this.paramsBox.getChildren().add(
                    (Node) autoIncrement.getContent()
                );
            }
        }
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
        Collection<ColumnFormat> formats = this.columnFormatManager.getFormats();
        List<String> formatNames = new ArrayList<>();
        for (ColumnFormat columnFormat : formats) {
            formatNames.add(columnFormat.getName());
        }
        this.format.getItems().setAll(
            formatNames
        );
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