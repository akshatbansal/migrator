package migrator.table.javafx;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.View;
import migrator.router.Router;
import migrator.table.component.IndexList;
import migrator.table.model.Index;
import migrator.table.service.IndexService;

public class JavafxIndexList implements IndexList {
    protected Node node;
    protected IndexService indexService;
    protected Router router;
    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(View view, IndexService indexService, Router router) {
        this.indexService = indexService;
        this.router = router;
        this.node = view.createFromFxml(this, "/layout/table/index/index.fxml");

        this.indexService.getList().addListener((Change<? extends Index> change) -> {
            this.draw();
        });
    }

    protected void draw() {
        if (this.indexes == null) {
            return;
        }
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
        Index newIndex = this.indexService.getFactory()
            .createWithCreateChange("new_index");
        this.indexService.add(newIndex);
        this.indexService.select(newIndex);
        if (this.indexes != null) {
            this.indexes.getSelectionModel().select(newIndex);
        }
        this.router.show("index", newIndex);
    }
}