package migrator.app.gui.view.commit;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.app.gui.view.main.Layout;

public class CommitView extends SimpleView implements View, RouteView{
    private Layout layout;

    private CommitOverviewView commitOverviewView;

    public CommitView(
        Layout layout,
        ViewFactories viewFactories,
        ObjectProperty<ProjectContainer> activeProjectContainer,
        ObservableList<Table> tables
    ) {
        this.layout = layout;

        this.commitOverviewView = viewFactories.createCommitOverview();
        commitOverviewView.bind(activeProjectContainer);
        CommitFormView commitFormView =viewFactories.createCommitForm();
        commitOverviewView.bindTables(tables);
        commitFormView.bind(activeProjectContainer);
        this.layout.renderBody(commitOverviewView);
        this.layout.renderSide(commitFormView);
    }

    @Override
    public Node getNode() {
        return this.layout.getNode();
    }

    @Override
    public void activate() {
        this.commitOverviewView.render();
    }

    @Override
    public View getView() {
        return this;
    }
    
    @Override
    public void suspend() {}
}