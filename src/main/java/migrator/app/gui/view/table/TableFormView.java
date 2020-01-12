package migrator.app.gui.view.table;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.migration.model.ChangeCommand;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class TableFormView extends SimpleView implements View {
    private EventDispatcher dispatcher;
    protected ObjectProperty<Table> tableBinded;
    protected ChangeListener<String> changeCommandListener;

    @FXML protected TextField name;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public TableFormView(
        EventDispatcher dispatcher
    ) {
        super();
        this.dispatcher = dispatcher;
        this.changeCommandListener = (ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
            this.onChangeTypeChange(newValue);
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

        this.loadFxml("/layout/table/form.fxml");
    }

    public void bind(ObjectProperty<Table> tableProperty) {
        this.tableBinded = tableProperty;
        tableProperty.addListener((observable, oldValue, newValue) -> {
            this.onTableChange(oldValue, newValue);
        });
    }

    private void onTableChange(Table oldTable, Table newTable) {
        if (oldTable != null) {
            this.name.textProperty().unbindBidirectional(oldTable.nameProperty());
            oldTable.getChangeCommand().typeProperty().removeListener(
                this.changeCommandListener
            );
        }
        if (newTable == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(newTable.nameProperty());
        newTable.getChangeCommand().typeProperty().addListener(
            this.changeCommandListener
        );
        this.onChangeTypeChange(
            newTable.getChangeCommand().getType()
        );
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

    @FXML public void delete() {
        this.dispatcher.dispatch(
            new SimpleEvent<>("table.remove", this.tableBinded.get())
        );
    }

    public void restore() {
        this.dispatcher.dispatch(
            new SimpleEvent<>("table.restore", this.tableBinded.get())
        );
        // Table table = this.tableBinded.get();
        // table.restore();
    }
}