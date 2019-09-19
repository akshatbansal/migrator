package migrator.table.javafx;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import migrator.javafx.helpers.ControllerHelper;
import migrator.migration.ChangeCommand;
import migrator.migration.ChangeService;
import migrator.migration.ColumnChange;
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
        List<Column> columns = this.columnService.getList();
        this.columns.setPrefHeight((this.columns.getFixedCellSize()) * (columns.size() + 1));
        this.columns.getItems().setAll(columns);
    }

    @FXML
    public void initialize() {
        // this.columns.setRowFactory(tv -> {
        //     TableRow<Column> row = new TableRow<>();
        //     row.itemProperty().addListener((obs, oldItem, newItem) -> {
        //         if (newItem == null) {
        //             if (oldItem != null) {
        //                 row.getStyleClass().
        //                 row.getStyleClass().remove("row--" + oldItem.getChange().getCommand().getType());
        //                 oldItem.getChange().getCommand().typeProperty().removeListener(listener);
        //             }
        //             return;
        //         }
        //         newItem.getChange().getCommand().typeProperty().addListener((ObservableValue<? extends String> observableValue, String oldValue, String newValue) -> {
        //             row.getStyleClass().remove("row--" + oldValue);
        //             row.getStyleClass().add("row--" + newValue);
        //         });
        //         row.getStyleClass().add("row--" + newItem.getChange().getCommand().getType());
        //     });
        //     return row ;
        // });

        this.columns.setFixedCellSize(35.0);
        this.columns.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.columns.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Column selectedColumn = this.columns.getSelectionModel().getSelectedItem();
            this.router.show("column", selectedColumn);
        });
        this.draw();
    }

    @FXML
    public void addColumn() {
        Column newColumn = new Column("new_column", new ColumnChange("new_column", new ChangeCommand("create")));
        this.columnService.add(newColumn);
        this.columnService.select(newColumn);
        this.router.show("column", newColumn);
    }

    private class ColumnTypeChangeListener implements ChangeListener<String> {
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            
        }
    }
}