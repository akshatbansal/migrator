package migrator.ext.javafx.project.service;

import javafx.stage.Window;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.component.CommitView;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.project.component.JavafxCommitForm;
import migrator.ext.javafx.project.component.JavafxCommitView;
import migrator.ext.javafx.project.component.JavafxProjectForm;
import migrator.ext.javafx.project.component.JavafxProjectList;

public class JavafxProjectGuiKit implements ProjectGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;
    protected Window window;
    protected Gui gui;

    public JavafxProjectGuiKit(ViewLoader viewLoader, Container container, Window window, Gui gui) {
        this.container = container;
        this.viewLoader = viewLoader;
        this.window = window;
        this.gui = gui;
    }

    @Override
    public ProjectForm createForm(Project project) {
        return new JavafxProjectForm(project, this.viewLoader, this.container, this.window, this.gui.getLoadingIndicator());
    }

    @Override
    public ProjectList createList() {
        return new JavafxProjectList(this.viewLoader, this.container, this, this.gui.getLoadingIndicator());
    }

    @Override
    public CommitForm createCommitForm(Project project) {
        return new JavafxCommitForm(project, this.viewLoader, this.container);
    }

    @Override
    public CommitView createCommitView(Project project) {
        return new JavafxCommitView(this.container, project, this.viewLoader, this.gui);
    }
}