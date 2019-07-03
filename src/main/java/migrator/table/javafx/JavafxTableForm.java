package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import migrator.javafx.helpers.ControllerHelper;
import migrator.table.component.TableForm;
import migrator.table.model.Table;
import migrator.table.service.TableService;

public class JavafxTableForm implements TableForm {
    protected Node node;
    protected TableService tableService;

    @FXML protected TextField name;

    public JavafxTableForm(TableService tableService) {
        this.tableService = tableService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/form.fxml");
    }

    @FXML public void initialize() {
        Table table = this.tableService.getSelected().get();
        this.name.textProperty().bindBidirectional(table.nameProperty());
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void remove() {
        // this.connectionsService.remove(this.connectionsService.getSelected().get());
    }
}