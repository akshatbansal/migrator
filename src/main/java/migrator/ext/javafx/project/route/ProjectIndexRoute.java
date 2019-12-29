package migrator.ext.javafx.project.route;

import javafx.stage.Window;
import migrator.app.gui.project.ProjectController;
import migrator.app.gui.project.ProjectFormView;
import migrator.app.gui.project.ProjectGuiModel;
import migrator.app.gui.project.ProjectListView;
import migrator.app.router.ActiveRouteConnection;
import migrator.ext.javafx.component.JavafxLayout;

public class ProjectIndexRoute extends ActiveRouteConnection<Object> {
    protected JavafxLayout layout;
    protected ProjectController controller;
    protected ProjectListView projectListView;
    protected ProjectFormView projectFormView;

    public ProjectIndexRoute(JavafxLayout layout, ProjectController controller, Window window) {
        super();
        this.layout = layout;
        this.controller = controller;

        this.projectListView = new ProjectListView(controller);
        this.projectFormView = new ProjectFormView(controller, window);

        this.controller.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.onSelectedChange(newValue);
        });
    }

    @Override
    public void show(Object routeData) {
        super.show(routeData);
        this.layout.renderBody(
            this.projectListView.getNode()
        );
        this.onSelectedChange(this.controller.selectedProperty().get());
    }

    private void onSelectedChange(ProjectGuiModel project) {
        if (!this.isActive()) {
            return;
        }
        if (project == null) {
            this.layout.clearSide();
        } else {
            this.layout.renderSide(
                this.projectFormView.getNode()
            );
        }
    }
}