package migrator.table.javafx;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.ControllerHelper;
import migrator.table.component.IndexList;
import migrator.table.model.Index;
import migrator.table.service.IndexService;

public class JavafxIndexList implements IndexList {
    protected Node node;
    protected IndexService indexService;
    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(IndexService indexService) {
        this.indexService = indexService;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/index/index.fxml");
    }

    protected void draw() {
        this.indexes.getItems().setAll(this.indexService.getList());
        this.indexes.setPrefHeight(this.indexes.getFixedCellSize() * (this.indexes.getItems().size() + 1));
    }

    @FXML
    public void initialize() {
        this.indexes.setFixedCellSize(35.0);
        this.indexes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.draw();
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML
    public void addIndex() {
        System.out.println("ADD INDEX");
    }
}