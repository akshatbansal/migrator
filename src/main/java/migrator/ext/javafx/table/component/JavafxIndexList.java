package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import migrator.app.Container;
import migrator.app.domain.index.service.IndexActiveState;
import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.model.Index;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxIndexList extends ViewComponent implements IndexList {
    protected IndexActiveState indexActiveState;
    protected IndexFactory indexFactory;
    protected ActiveRoute activeRoute;

    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.indexActiveState = container.getIndexActiveState();
        this.indexFactory = container.getIndexFactory();
        this.activeRoute = container.getActiveRoute();

        this.loadView("/layout/table/index/index.fxml");

        this.indexActiveState.getList()
            .addListener((Change<? extends Index> change) -> {
                this.draw();
            });
    }

    protected void draw() {
        if (this.indexes == null) {
            return;
        }
        this.indexes.getItems().setAll(
            this.indexActiveState.getList()
        );
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
        Index newIndex = this.indexFactory.createWithCreateChange("new_index");
        this.indexActiveState.addAndActivate(newIndex);
        if (this.indexes != null) {
            this.indexes.getSelectionModel().select(newIndex);
        }
        this.activeRoute.changeTo("index.view", newIndex);
    }
}