package migrator.ext.javafx.table.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.service.SimpleStore;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxTableForm extends ViewComponent implements TableForm {
    protected SimpleStore<Table> tableService;
    protected ObjectProperty<Table> tableBinded;
    protected ChangeListener<String> changeCommandListener;

    @FXML protected TextField name;
    @FXML protected HBox manageBox;
    protected Button removeButton;
    protected Button restoreButton;

    public JavafxTableForm() {
        super(new ViewLoader());
        // this.tableService = container.getTableService();
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

        this.loadView("/layout/table/form.fxml");
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
        if (this.tableBinded.get() == null) {
            return;
        }
        Table table = this.tableBinded.get();
        if (table.getChange().getCommand().isType(ChangeCommand.CREATE)) {
            // this.tableService.remove(table);
            return;
        }
        table.getChange().getCommand().setType(ChangeCommand.DELETE);
    }

    public void restore() {
        if (this.tableBinded.get() == null) {
            return;
        }
        Table table = this.tableBinded.get();
        table.restore();
    }
}