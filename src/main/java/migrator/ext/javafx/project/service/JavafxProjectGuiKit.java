package migrator.ext.javafx.project.service;

import javafx.stage.Window;
import migrator.app.Container;
import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.component.JavafxCommitForm;
import migrator.ext.javafx.project.component.JavafxProjectForm;
import migrator.ext.javafx.project.component.JavafxProjectList;

public class JavafxProjectGuiKit implements ProjectGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;
    protected Window window;

    public JavafxProjectGuiKit(ViewLoader viewLoader, Container container, Window window) {
        this.container = container;
        this.viewLoader = viewLoader;
        this.window = window;
    }

    @Override
    public ProjectForm createForm(Project project) {
        return new JavafxProjectForm(project, this.viewLoader, this.container, this.window);
    }

    @Override
    public ProjectList createList() {
        return new JavafxProjectList(this.viewLoader, this.container, this);
    }

    @Override
    public CommitForm createCommitForm(Project project) {
        return new JavafxCommitForm(project, this.viewLoader, this.container);
    }
}