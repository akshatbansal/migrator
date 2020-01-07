package migrator.ext.javafx.table.component;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import migrator.app.Container;
import migrator.app.domain.column.service.ColumnActiveState;
import migrator.app.domain.column.service.ColumnFactory;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableActiveState;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class JavafxColumnList extends ViewComponent implements ColumnList {
    protected ColumnActiveState columnActiveState;
    protected TableActiveState tableActiveState;
    protected ColumnFactory columnFactory;
    protected Emitter<Column> selectEmitter;

    @FXML protected TableView<Column> columns;

    public JavafxColumnList(Container container) {
        super(new ViewLoader());
        this.columnActiveState = container.getColumnActiveState();
        this.columnFactory = container.getColumnFactory();
        this.tableActiveState = container.getTableActiveState();
        this.selectEmitter = new EventEmitter<>();
        
        this.loadView("/layout/table/column/index.fxml");        
    }

    public void bind(ObservableList<Column> columns) {
        this.columns.setItems(columns);
        this.columnActiveState.getList()
            .addListener((Change<? extends Column> change) -> {
                this.fitSize(columns.size());
            });
        this.fitSize(columns.size());
    }

    private void fitSize(int countRows) {
        this.columns.setPrefHeight((40) * (countRows + 1));
    }

    @FXML
    public void initialize() {
        this.columns.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.columns.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> obs, Number oldSelection, Number newSelection) -> {
            Column selectedColumn = this.columns.getSelectionModel().getSelectedItem();
            this.columnActiveState.activate(selectedColumn);
            if (selectedColumn != null) {
                this.selectEmitter.emit("select", selectedColumn);
            } else {
                this.selectEmitter.emit("deselect");
            }
        });
    }

    @FXML
    public void addColumn() {
        Column newColumn = this.columnFactory.createWithCreateChange(this.tableActiveState.getActive().get().getUniqueKey(), "new_column");
        this.columnActiveState.add(newColumn);
        this.columns.getSelectionModel().select(newColumn);
    }

    public Subscription<Column> onSelect(Subscriber<Column> subscriber) {
        return this.selectEmitter.on("select", subscriber);
    }

    @Override
    public void deselect() {
        this.columns.getSelectionModel().clearSelection();
    }
}