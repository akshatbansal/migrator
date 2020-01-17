package migrator.app.gui.view.project;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.app.gui.view.main.Layout;
import migrator.app.gui.view.table.TableListView;

public class ProjectView extends SimpleView implements View, RouteView {
    private Layout layout;
    
    public ProjectView(
        Layout layout,
        ViewFactories viewFactories,
        ObservableList<Table> tables
    ) {
        this.layout = layout;

        TableListView tableListView = viewFactories.createTableList(tables);
        this.layout.renderBody(tableListView);
        this.layout.clearSide();
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