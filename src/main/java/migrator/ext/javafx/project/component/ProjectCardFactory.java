package migrator.ext.javafx.project.component;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectOpener;
import migrator.app.domain.project.service.ProjectSelector;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.CardComponent;
import migrator.ext.javafx.component.card.CardComponentFactory;

public class ProjectCardFactory implements CardComponentFactory<Project> {
    protected ViewLoader viewLoader;
    protected ProjectOpener projectOpener;
    protected ProjectSelector projectSelector;

    public ProjectCardFactory(ViewLoader viewLoader, ProjectOpener projectOpener, ProjectSelector projectSelector) {
        this.viewLoader = viewLoader;
        this.projectOpener = projectOpener;
        this.projectSelector = projectSelector;
    }

    @Override
    public CardComponent<Project> create(Project item) {
        return new ProjectCard(
            this.viewLoader,
            item,
            this.projectOpener,
            this.projectSelector
        );
    }
}