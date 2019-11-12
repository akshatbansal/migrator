package migrator.app.domain.project.service;

import migrator.app.domain.project.model.Project;

public interface ProjectSelector {
    public void selectProject(Project project);
}