package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.table.component.IndexForm;
import migrator.table.model.Index;
import migrator.table.service.IndexService;

public class JavafxIndexForm implements IndexForm {
    protected Node node;
    protected IndexService indexService;
    protected Index index;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> column1;
    @FXML protected ComboBox<String> column2;
    @FXML protected ComboBox<String> column3;

    public JavafxIndexForm(IndexService indexService) {
        this.indexService = indexService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/index/form.fxml");
    }

    public void setIndex(Index index) {
        this.index = index;
        if (index == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(index.nameProperty());
        // this.format.valueProperty().bindBidirectional(column.formatProperty());
    }

    @FXML public void initialize() {
        // this.format.getItems().setAll("string", "boolean", "integer", "smallint", "longint");
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML public void delete() {
        if (this.index.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.indexService.remove(this.index);
            return;
        }
        this.index.getChangeCommand().setType(ChangeCommand.DELETE);
    }

    @FXML public void close() {
        // this.connectionsService.select(null);
    }
}