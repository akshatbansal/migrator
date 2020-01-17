package migrator.app.gui.view.column;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import migrator.app.domain.table.model.Column;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.gui.column.format.ColumnFormatOption;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ColumnFormView extends SimpleView implements View {
    protected ObjectProperty<Column> columnProperty;
    protected ColumnFormatCollection columnFormatCollection;
    protected ChangeListener<String> changeCommandListener;
    protected ChangeListener<String> formatListener;
    private EventDispatcher dispatcher;

    @FXML protected TextField name;
    @FXML protected ComboBox<ColumnFormatOption> format;
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

    public ColumnFormView(
        EventDispatcher dispatcher,
        ColumnFormatCollection columnFormatCollection
    ) {
        super();
        this.dispatcher = dispatcher;
        this.columnFormatCollection = columnFormatCollection;
        this.changeCommandListener = (ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        };
        this.formatListener = (obs, ol, ne) -> {
            this.onFormatChange(ne);
        };

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

        this.loadFxml("/layout/table/column/form.fxml");
        
        this.format.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Column column = this.columnProperty.get();
            if (column == null) {
                return;
            }
            newValue.updateModel(column);
            column.formatProperty().set(newValue.toString());
        });
    }

    public void bind(ObjectProperty<Column> columnProperty) {
        this.columnProperty = columnProperty;
        columnProperty.addListener((observable, oldValue, newValue) -> {
            this.unbindForm(oldValue);
            this.bindForm(newValue);
        });
        this.bindForm(columnProperty.get());
    }

    public void bindFormats(ObservableList<ColumnFormatOption> formats) {
        this.format.getItems().setAll(formats);
    }

    private void unbindForm(Column column) {
        if (column == null) {
            return;
        }

        this.name.textProperty().unbindBidirectional(
            column.nameProperty()
        );
        this.defaultText.textProperty().unbindBidirectional(
            column.defaultValueProperty()
        );
        this.nullCheckbox.selectedProperty().unbindBidirectional(
            column.enableNullProperty()
        );
        this.length.textProperty().unbindBidirectional(
            column.lengthProperty()
        );
        this.precision.textProperty().unbindBidirectional(
            column.precisionProperty()
        );
        this.sign.selectedProperty().unbindBidirectional(
            column.signProperty()
        );
        this.autoIncrement.selectedProperty().unbindBidirectional(
            column.autoIncrementProperty()
        );

        this.lengthRow.managedProperty().unbind();
        this.lengthRow.visibleProperty().unbind();
        this.precisionRow.managedProperty().unbind();
        this.precisionRow.visibleProperty().unbind();
        this.signRow.managedProperty().unbind();
        this.signRow.visibleProperty().unbind();
        this.autoIncrementRow.managedProperty().unbind();
        this.autoIncrementRow.visibleProperty().unbind();

        column.formatProperty().removeListener(this.formatListener);
        column.getChangeCommand().typeProperty().removeListener(this.changeCommandListener);
    }

    private void bindForm(Column column) {
        if (column == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(
            column.nameProperty()
        );
        this.defaultText.textProperty().bindBidirectional(
            column.defaultValueProperty()
        );
        this.nullCheckbox.selectedProperty().bindBidirectional(
            column.enableNullProperty()
        );
        this.length.textProperty().bindBidirectional(
            column.lengthProperty()
        );
        this.precision.textProperty().bindBidirectional(
            column.precisionProperty()
        );
        this.sign.selectedProperty().bindBidirectional(
            column.signProperty()
        );
        this.autoIncrement.selectedProperty().bindBidirectional(
            column.autoIncrementProperty()
        );

        this.lengthRow.managedProperty().bind(column.attribute("length"));
        this.lengthRow.visibleProperty().bind(column.attribute("length"));
        this.precisionRow.managedProperty().bind(column.attribute("precision"));
        this.precisionRow.visibleProperty().bind(column.attribute("precision"));
        this.signRow.managedProperty().bind(column.attribute("sign"));
        this.signRow.visibleProperty().bind(column.attribute("sign"));
        this.autoIncrementRow.managedProperty().bind(column.attribute("autoIncrement"));
        this.autoIncrementRow.visibleProperty().bind(column.attribute("autoIncrement"));

        column.formatProperty().addListener(this.formatListener);
        this.onFormatChange(column.formatProperty().get());

        column.getChangeCommand().typeProperty().addListener(this.changeCommandListener);
        this.onChangeTypeChange(column.getChangeCommand().getType());
    }

    protected void onFormatChange(String columnFormat) {
        ColumnFormatOption format = this.columnFormatCollection.getFormatByName(columnFormat);
        this.format.getSelectionModel().select(format);
    }

    protected void onChangeTypeChange(String changeType) {
        this.manageBox.getChildren().clear();
        if (!changeType.equals(ChangeCommand.DELETE)) {
            this.manageBox.getChildren().add(this.removeButton);
        }
        if (changeType.equals(ChangeCommand.DELETE) || changeType.equals(ChangeCommand.UPDATE)) {
            this.manageBox.getChildren().add(this.restoreButton);
        }
    }

    public void delete() {
        Column column = this.columnProperty.get();
        if (column == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.remove", column)
        );
    }

    public void restore() {
        Column column = this.columnProperty.get();
        if (column == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.restore", column)
        );
    }

    @FXML public void close() {
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.deselect")
        );
    }
}