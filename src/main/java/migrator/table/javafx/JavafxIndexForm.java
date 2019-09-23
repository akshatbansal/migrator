package migrator.table.javafx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.table.component.IndexForm;
import migrator.table.model.Column;
import migrator.table.model.Index;
import migrator.table.service.ColumnService;
import migrator.table.service.IndexService;

public class JavafxIndexForm implements IndexForm {
    protected Node node;
    protected IndexService indexService;
    protected ColumnService columnService;
    protected Index index;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> column1;
    @FXML protected ComboBox<String> column2;
    @FXML protected ComboBox<String> column3;

    public JavafxIndexForm(IndexService indexService, ColumnService columnService) {
        this.indexService = indexService;
        this.columnService = columnService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/index/form.fxml");
    }

    public void setIndex(Index index) {
        this.index = index;
        if (index == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(index.nameProperty());
        this.column1.valueProperty().bindBidirectional(index.columnProperty(0));
        this.column2.valueProperty().bindBidirectional(index.columnProperty(1));
        this.column3.valueProperty().bindBidirectional(index.columnProperty(2));
    }

    @FXML public void initialize() {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("");
        for (Column column : this.columnService.getList()) {
            columnNames.add(column.getName());
        }
        this.column1.getItems().setAll(columnNames);
        this.column2.getItems().setAll(columnNames);
        this.column3.getItems().setAll(columnNames);
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