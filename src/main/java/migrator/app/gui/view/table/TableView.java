package migrator.app.gui.view.table;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.column.format.ColumnFormatOption;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.app.gui.view.column.ColumnFormView;
import migrator.app.gui.view.index.IndexFormView;
import migrator.app.gui.view.main.Layout;
import migrator.app.migration.model.ColumnProperty;

public class TableView extends SimpleView implements View, RouteView {
    private Layout layout;
    
    public TableView(
        Layout layout,
        ViewFactories viewFactories,
        ObjectProperty<Table> table,
        ObjectProperty<ProjectContainer> projectContainer,
        ObjectProperty<Column> selectedColumn,
        ObjectProperty<Index> selectedIndex,
        ObservableList<ColumnFormatOption> formatOptions,
        ObservableList<ColumnProperty> columns 
    ) {
        this.layout = layout;

        TableDetailView detailView = viewFactories.createTableDetail();
        detailView.bind(table);
        detailView.bindProject(projectContainer);

        TableFormView tableFormView = viewFactories.createTableForm();
        tableFormView.bind(table);
        ColumnFormView columnFormView = viewFactories.createColumnForm();
        columnFormView.bind(selectedColumn);
        columnFormView.bindFormats(formatOptions);
        IndexFormView indexFormView = viewFactories.createIndexForm();
        indexFormView.bind(selectedIndex);
        indexFormView.bindColumns(columns);
        
        this.layout.renderBody(detailView);
        this.layout.renderSide(tableFormView);

        selectedColumn.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.layout.renderSide(tableFormView);
            } else {
                this.layout.renderSide(columnFormView);
            }
        });
        selectedIndex.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                this.layout.renderSide(tableFormView);
            } else {
                this.layout.renderSide(indexFormView);
            }
        });
    }

    @Override
    public Node getNode() {
        return this.layout.getNode();
    }

    @Override
    public void activate() {}

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void suspend() {}
}