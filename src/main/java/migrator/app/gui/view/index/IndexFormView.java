package migrator.app.gui.view.index;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.domain.table.model.Index;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class IndexFormView extends SimpleView implements View {
    private EventDispatcher dispatcher;
    protected ObjectProperty<Index> indexProperty;
    protected ChangeListener<String> changeCommandListener;
    protected BooleanProperty formDisabled;

    @FXML protected TextField name;
    @FXML protected ComboBox<ColumnProperty> column1;
    @FXML protected ComboBox<ColumnProperty> column2;
    @FXML protected ComboBox<ColumnProperty> column3;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public IndexFormView(
        EventDispatcher dispatcher
    ) {
        super();
        this.dispatcher = dispatcher;
        this.changeCommandListener = (ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
        };
        this.formDisabled = new SimpleBooleanProperty(true);

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

        this.loadFxml("/layout/table/index/form.fxml");
    }

    public void bind(ObjectProperty<Index> indexProperty) {
        Index oldIndex = null;
        if (this.indexProperty != null) {
            oldIndex = this.indexProperty.get();
        }
        this.indexProperty = indexProperty;
        indexProperty.addListener((observable, oldValue, newValue) -> {
            this.onIndexChange(oldValue, newValue);
        });
        this.onIndexChange(oldIndex, this.indexProperty.get());
    }

    public void bindColumns(ObservableList<ColumnProperty> columns) {
        this.column1.setItems(columns);
        this.column2.setItems(columns);
        this.column3.setItems(columns);
    }

    public void onIndexChange(Index oldIndex, Index newIndex) {
        if (oldIndex != null) {
            this.name.textProperty().unbindBidirectional(oldIndex.nameProperty());
            oldIndex.getChangeCommand().typeProperty().removeListener(this.changeCommandListener);
        }

        if (newIndex == null) {
            return;
        }

        this.name.textProperty().bindBidirectional(newIndex.nameProperty());

        this.column1.valueProperty().set(newIndex.columnPropertyOrCreate(0));
        this.column2.valueProperty().set(newIndex.columnPropertyOrCreate(1));
        this.column3.valueProperty().set(newIndex.columnPropertyOrCreate(2));

        newIndex.getChangeCommand().typeProperty().addListener(
            this.changeCommandListener
        );
        this.onChangeTypeChange(newIndex.getChangeCommand().getType());
    }

    protected void onChangeTypeChange(String changeType) {
        this.formDisabled.set(!changeType.equals(ChangeCommand.CREATE));

        this.manageBox.getChildren().clear();
        if (changeType != ChangeCommand.DELETE) {
            this.manageBox.getChildren().add(this.removeButton);
        }
        if (changeType.equals(ChangeCommand.DELETE) || changeType.equals(ChangeCommand.UPDATE)) {
            this.manageBox.getChildren().add(this.restoreButton);
        }
    }

    private void setColumnAt(int index, ColumnProperty columnProperty) {
        if (this.indexProperty.get() == null) {
            return;
        }
        this.indexProperty.get().setColumnAt(index, columnProperty);
    }

    @FXML public void initialize() {
        this.name.disableProperty().bind(this.formDisabled);
        this.column1.disableProperty().bind(this.formDisabled);
        this.column2.disableProperty().bind(this.formDisabled);
        this.column3.disableProperty().bind(this.formDisabled);

        this.column1.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setColumnAt(0, newValue);
        });
        this.column2.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setColumnAt(1, newValue);
        });
        this.column3.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.setColumnAt(2, newValue);
        });
    }

    @FXML public void delete() {
        Index index = this.indexProperty.get();
        if (index == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.remove", index)
        );
    }

    @FXML public void close() {
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.deselect")
        );
    }

    @FXML public void restore() {
        Index index = this.indexProperty.get();
        if (index == null) {
            return;
        }
        this.dispatcher.dispatch(
            new SimpleEvent<>("index.restore", index)
        );
    }
}