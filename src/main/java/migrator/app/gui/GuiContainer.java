package migrator.app.gui;

import javafx.collections.FXCollections;
import migrator.app.database.DatabaseContainer;
import migrator.app.gui.ActiveProjectContainer;
import migrator.app.gui.project.ProjectController;
import migrator.app.migration.Migration;
import migrator.app.project.ProjectContainerFactory;
import migrator.app.project.ProjectService;
import migrator.app.router.ActiveRoute;
import migrator.app.toast.PermanentToastService;
import migrator.app.toast.ToastService;

public class GuiContainer {
    private ActiveProjectContainer activeProjectContainer;
    private ProjectController projectController;
    private ToastService toastService;
    private ActiveRoute activeRoute;

    public GuiContainer(
        ProjectService projectService,
        DatabaseContainer databaseContainer,
        Migration migration
    ) {
        this.activeProjectContainer = new ActiveProjectContainer();
        this.toastService = new PermanentToastService();
        this.activeRoute = new ActiveRoute();

        this.projectController = new ProjectController(
            projectService.getProjects(),
            databaseContainer.getDrivers(),
            FXCollections.observableArrayList(
                migration.getGeneratorNames()
            ),
            this.activeProjectContainer,
            new ProjectContainerFactory(databaseContainer),
            this.toastService,
            this.activeRoute
        );
    }

    public ActiveProjectContainer getActiveProjectContainer() {
        return this.activeProjectContainer;
    }

    public ProjectController getProjectController() {
        return this.projectController;
    }

    public ToastService getToastService() {
        return this.toastService;
    }

    public ActiveRoute getActiveRoute() {
        return this.activeRoute;
    }
}