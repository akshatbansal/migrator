package migrator.ext.javafx.project.service;

import migrator.app.Container;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.component.JavafxProjectForm;
import migrator.ext.javafx.project.component.JavafxProjectList;

public class JavafxProjectGuiKit implements ProjectGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;

    public JavafxProjectGuiKit(ViewLoader viewLoader, Container container) {
        this.container = container;
        this.viewLoader = viewLoader;
    }

    @Override
    public ProjectForm createForm(Project project) {
        return new JavafxProjectForm(project, this.viewLoader, this.container);
    }

    @Override
    public ProjectList createList() {
        return new JavafxProjectList(this.viewLoader, this.container, this);
    }
}