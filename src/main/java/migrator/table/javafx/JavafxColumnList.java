package migrator.table.javafx;

import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.ControllerHelper;
import migrator.table.component.ColumnList;
import migrator.table.model.Column;
import migrator.table.service.ColumnService;

public class JavafxColumnList implements ColumnList {
    protected Node node;
    protected ColumnService columnService;
    @FXML protected TableView<Column> columns;

    public JavafxColumnList(ColumnService columnService) {
        this.columnService = columnService;
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
        this.columns.getItems().addAll(this.columnService.getList());
        this.columns.setPrefHeight(this.columns.getFixedCellSize() * (this.columns.getItems().size() + 1.01));
    }

    @FXML
    public void initialize() {
        this.columns.setFixedCellSize(35.0);
        this.draw();
    }

    @FXML
    public void addColumn() {
        System.out.println("ADD COUMN");
    }
}