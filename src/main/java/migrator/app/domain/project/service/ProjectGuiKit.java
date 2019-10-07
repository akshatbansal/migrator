package migrator.app.domain.project.service;

import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;

public interface ProjectGuiKit {
    public ProjectForm createForm(Project project);
    public ProjectList createList();
}