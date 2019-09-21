package migrator.table.javafx;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.migration.IndexChange;
import migrator.router.Router;
import migrator.table.component.IndexList;
import migrator.table.model.Index;
import migrator.table.service.IndexService;

public class JavafxIndexList implements IndexList {
    protected Node node;
    protected IndexService indexService;
    protected Router router;
    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(IndexService indexService, Router router) {
        this.indexService = indexService;
        this.router = router;
        this.node = ControllerHelper.createViewNode(this, "/layout/table/index/index.fxml");

        this.indexService.getList().addListener((Change<? extends Index> change) -> {
            this.draw();
        });
    }

    protected void draw() {
        this.indexes.getItems().setAll(this.indexService.getList());
        this.indexes.setPrefHeight(40 * (this.indexes.getItems().size() + 1));
    }

    @FXML
    public void initialize() {
        this.indexes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.indexes.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Index selectedIndex = this.indexes.getSelectionModel().getSelectedItem();
            this.router.show("index", selectedIndex);
        });
        this.draw();
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML
    public void addIndex() {
        Index newIndex = new Index("new_index", new IndexChange("new_index", new ChangeCommand("create")));
        this.indexService.add(newIndex);
        this.indexService.select(newIndex);
        this.router.show("index", newIndex);
    }
}