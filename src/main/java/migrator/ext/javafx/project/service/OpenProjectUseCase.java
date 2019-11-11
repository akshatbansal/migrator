package migrator.ext.javafx.project.service;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.ext.javafx.UseCase;

public class OpenProjectUseCase implements UseCase {
    protected ProjectService projectService;
    protected Project project;

    public OpenProjectUseCase(ProjectService projectService, Project project) {
        this.projectService = projectService;
        this.project = project;
    }

    @Override
    public void run() {
        this.projectService.open(this.project);
    }
}