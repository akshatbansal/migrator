package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.table.component.ColumnForm;
import migrator.table.model.Column;
import migrator.table.service.ColumnService;

public class JavafxColumnForm implements ColumnForm {
    protected Node node;
    protected ColumnService columnService;
    protected Column column;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> format;

    public JavafxColumnForm(ColumnService columnService) {
        this.columnService = columnService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/column/form.fxml");
    }

    public void setColumn(Column column) {
        this.column = column;
        if (column == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(column.nameProperty());
        this.format.valueProperty().bindBidirectional(column.formatProperty());
    }

    @FXML public void initialize() {
        this.format.getItems().setAll("string", "boolean", "integer", "smallint", "longint");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void delete() {
        if (this.column.getChange().getCommand().isType(ChangeCommand.CREATE)) {
            this.columnService.remove(this.column);
            return;
        }
        this.column.getChange().getCommand().setType(ChangeCommand.DELETE);
    }

    @FXML public void close() {
        // this.connectionsService.select(null);
    }

    @FXML public void create() {
        // this.router.show("databases", this.connectionsService.getSelected().get());
    }
}