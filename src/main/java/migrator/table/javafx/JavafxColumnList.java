package migrator.table.javafx;

import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeService;
import migrator.router.Router;
import migrator.table.component.ColumnList;
import migrator.table.model.Column;
import migrator.table.service.ColumnService;

public class JavafxColumnList implements ColumnList {
    protected Node node;
    protected ColumnService columnService;
    protected ChangeService changeService;
    protected Router router;
    @FXML protected TableView<Column> columns;

    public JavafxColumnList(ColumnService columnService, ChangeService changeService, Router router) {
        this.columnService = columnService;
        this.changeService = changeService;
        this.router = router;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/column/index.fxml");

        this.columnService.getList().addListener((Change<? extends Column> change) -> {
            this.draw();
        });
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    protected void draw() {
        if (this.columns == null) {
            return;
        }
        List<Column> columns = this.columnService.getList();
        this.columns.setPrefHeight((40) * (columns.size() + 1));
        this.columns.getItems().setAll(columns);
    }

    @FXML
    public void initialize() {
        this.columns.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.columns.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Column selectedColumn = this.columns.getSelectionModel().getSelectedItem();
            this.router.show("column", selectedColumn);
        });
        this.draw();
    }

    @FXML
    public void addColumn() {
        Column newColumn = this.columnService.getFactory()
            .createWithCreateChange("new_column");
        this.columnService.add(newColumn);
        this.columnService.select(newColumn);
        this.columns.getSelectionModel().select(newColumn);
        this.router.show("column", newColumn);
    }
}