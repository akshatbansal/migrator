package migrator.app.gui.component.index;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import migrator.app.domain.table.model.Index;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.lib.dispatcher.SimpleEvent;

public class IndexListComponent extends SimpleComponent implements Component {
    protected ObservableList<Index> indexes;

    @FXML protected TableView<Index> indexesTable;

    public IndexListComponent() {
        super();
        this.loadFxml("/layout/table/index/index.fxml");

        this.indexesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.indexesTable.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Index selectedIndex = this.indexesTable.getSelectionModel().getSelectedItem();
            if (selectedIndex == null) {
                return;
            }
            this.output(
                new SimpleEvent<>("select", selectedIndex)
            );
        });
    }

    public void bind(ObservableList<Index> indexes) {
        this.indexes = indexes;
        this.indexesTable.setItems(indexes);
        indexes.addListener((Change<? extends Index> change) -> {
            this.fitSize(indexes.size());
        });
        this.fitSize(indexes.size());
    }

    private void fitSize(int indexRows) {
        this.indexesTable.setPrefHeight(40 * (indexRows + 1));
    }

    @FXML
    public void addIndex() {
        this.output(
            new SimpleEvent<>("create")
        );
    }

    public void deselect() {
        this.indexesTable.getSelectionModel().clearSelection();
    }
}