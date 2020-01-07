package migrator.ext.javafx.project.route;

import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.router.ActiveRoute;
import migrator.app.router.SimpleConnection;
import migrator.ext.javafx.component.JavafxLayout;
import migrator.lib.factory.Factory;
import migrator.lib.factory.SingletonCallbackFactory;

public class ProjectIndexRoute extends SimpleConnection<Object> {
    protected JavafxLayout layout;
    protected ProjectService projectService;
    protected ActiveRoute activeRoute;

    protected Factory<ProjectList> projectListFactory;
    protected Factory<ProjectForm> projectFormFactory;

    public ProjectIndexRoute(JavafxLayout layout, ProjectGuiKit projectGuiKit, ProjectService projectService, ActiveRoute activeRoute) {
        this.layout = layout;
        this.projectService = projectService;
        this.activeRoute = activeRoute;

        this.projectFormFactory = new SingletonCallbackFactory<ProjectForm>(() -> {
            return projectGuiKit.createForm();
        });
        this.projectListFactory = new SingletonCallbackFactory<ProjectList>(() -> {
            return projectGuiKit.createList();
        });

        this.projectService.getSelected().addListener((observable, oldValue, newValue) -> {
            if (!this.isActive()) {
                return;
            }
            this.onSelect(newValue);
        });
    }

    @Override
    public void show(Object routeData) {
        super.show(routeData);
        this.layout.renderBody(this.projectListFactory.create());
        this.onSelect(
            this.projectService.getSelected().get()
        );
    }

    private void onSelect(Project project) {
        if (project == null) {
            this.layout.clearSide();
            return;
        }
        this.layout.renderSide(this.projectFormFactory.create());
    }
}