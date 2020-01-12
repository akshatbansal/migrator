package migrator.app.gui.view.table;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.app.gui.view.main.Layout;

public class TableView extends SimpleView implements View {
    private Layout layout;
    
    public TableView(
        Layout layout,
        ViewFactories viewFactories,
        ObjectProperty<Table> table,
        ObjectProperty<ProjectContainer> projectContainer
    ) {
        this.layout = layout;

        TableDetailView detailView = viewFactories.createTableDetail();
        detailView.bind(table);
        detailView.bindProject(projectContainer);

        TableFormView tableFormView = viewFactories.createTableForm();
        tableFormView.bind(table);
        
        this.layout.renderBody(detailView);
        this.layout.renderSide(tableFormView);
    }

    @Override
    public Node getNode() {
        return this.layout.getNode();
    }
}