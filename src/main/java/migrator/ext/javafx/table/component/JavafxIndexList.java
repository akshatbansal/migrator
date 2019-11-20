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
import migrator.app.domain.table.service.TableActiveState;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class JavafxIndexList extends ViewComponent implements IndexList {
    protected IndexActiveState indexActiveState;
    protected IndexFactory indexFactory;
    protected TableActiveState tableActiveState;
    protected Emitter<Index> emitter;

    @FXML protected TableView<Index> indexes;

    public JavafxIndexList(ViewLoader viewLoader, Container container) {
        super(viewLoader);
        this.indexActiveState = container.getIndexActiveState();
        this.indexFactory = container.getIndexFactory();
        this.tableActiveState = container.getTableActiveState();
        this.emitter = new EventEmitter<>();

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
            this.indexActiveState.activate(selectedIndex);
            if (selectedIndex != null) {
                this.emitter.emit("select", selectedIndex);
            } else {
                this.emitter.emit("deselect");
            }
        });
        this.draw();
    }

    @FXML
    public void addIndex() {
        Index newIndex = this.indexFactory.createWithCreateChange(this.tableActiveState.getActive().get().getUniqueKey(), "new_index");
        this.indexActiveState.add(newIndex);
        if (this.indexes != null) {
            this.indexes.getSelectionModel().select(newIndex);
        }
    }

    @Override
    public void deselect() {
        this.indexes.getSelectionModel().clearSelection();;
    }

    @Override
    public Subscription<Index> onSelect(Subscriber<Index> subscriber) {
        return this.emitter.on("select", subscriber);
    }
}