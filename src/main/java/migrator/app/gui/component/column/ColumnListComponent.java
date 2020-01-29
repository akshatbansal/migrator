package migrator.app.gui.component.column;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import migrator.app.domain.table.model.Column;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.lib.dispatcher.SimpleEvent;

public class ColumnListComponent extends SimpleComponent implements Component {
    protected ObservableList<Column> columns;
    protected ObjectProperty<Column> selected;

    @FXML protected TableView<Column> columnsTable;

    public ColumnListComponent() {
        super();
        this.loadFxml("/layout/table/column/index.fxml");
        
        this.columnsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.columnsTable.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Column selectedColumn = this.columnsTable.getSelectionModel().getSelectedItem();
            if (selectedColumn == null) {
                return;
            }
            this.output(new SimpleEvent<>("select", selectedColumn));
        });
    }

    public void bind(ObservableList<Column> columns) {
        this.columns = columns;
        this.columnsTable.setItems(this.columns);
        this.columns.addListener((Change<? extends Column> change) -> {
            this.fitSize(columns.size());
        });
        this.fitSize(columns.size());
    }

    public void bindSelected(ObjectProperty<Column> selected) {
        this.selected = selected;
        this.selected.addListener((observable, oldValue, newValue) -> {
            if (this.columnsTable.getSelectionModel().getSelectedItem() == newValue) {
                return;
            }
            this.columnsTable.getSelectionModel().select(newValue);
        });
    }

    private void fitSize(int countRows) {
        this.columnsTable.setPrefHeight((40) * (countRows + 1));
    }

    @FXML
    public void addColumn() {
        this.output(new SimpleEvent<>("add"));
    }

    public void deselect() {
        this.columnsTable.getSelectionModel().clearSelection();
    }
}