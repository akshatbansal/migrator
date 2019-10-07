package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import migrator.app.Container;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.service.IndexService;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxIndexList extends ViewComponent implements IndexList {
    protected IndexService indexService;
    protected ActiveRoute activeRoute;

    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.indexService = container.getIndexService();
        this.activeRoute = container.getActiveRoute();

        this.loadView("/layout/table/index/index.fxml");

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
            this.activeRoute.changeTo("index.view", selectedIndex);
        });
        this.draw();
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
        this.activeRoute.changeTo("index.view", newIndex);
    }
}