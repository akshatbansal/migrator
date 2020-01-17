package migrator.app.gui.view.project;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import migrator.app.domain.project.model.Project;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.app.gui.view.ViewFactories;
import migrator.app.gui.view.main.Layout;

public class ProjectsView extends SimpleView implements View, RouteView {
    protected Layout layout;

    protected ProjectFormView projectFormView;

    public ProjectsView(
        Layout layout,
        ViewFactories viewFactories,
        ObservableList<Project> projects,
        ObjectProperty<Project> selectedProject,
        ObservableList<String> dbDrivers,
        ObservableList<String> outputDrivers
    ) {
        this.layout = layout;

        this.projectFormView = viewFactories.createProjectForm(
            selectedProject,
            dbDrivers,
            outputDrivers
        );
        ProjectListView list = viewFactories.createProjectList(projects);

        selectedProject.addListener((observable, oldValue, newValue) -> {
            this.onSelectedProjectChange(newValue);
        });
        this.layout.renderBody(list);
        this.onSelectedProjectChange(selectedProject.get());
    }

    protected void onSelectedProjectChange(Project project) {
        if (project == null) {
            this.layout.clearSide();
            return;
        }
        this.layout.renderSide(this.projectFormView);
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