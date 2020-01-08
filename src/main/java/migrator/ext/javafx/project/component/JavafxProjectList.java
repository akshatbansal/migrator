package migrator.ext.javafx.project.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectOpener;
import migrator.app.domain.project.service.ProjectSelector;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.loading.LoadingIndicator;
import migrator.ext.javafx.component.card.CardListComponent;
import migrator.ext.javafx.UseCase;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxProjectList extends ViewComponent implements ProjectList, ProjectOpener, ProjectSelector {
    protected ProjectService projectService;
    protected CardListComponent<Project> cardList;
    protected LoadingIndicator loadingIndicator;

    @FXML protected VBox projectCards;

    public JavafxProjectList(Container container, LoadingIndicator loadingIndicator) {
        super(new ViewLoader());
        this.projectService = container.getProjectService();
        this.loadingIndicator = loadingIndicator;

        this.cardList = new CardListComponent<>(
            this.projectService.getList(),
            new ProjectCardFactory(
                this.viewLoader,
                this,
                this
            ),
            this.viewLoader
        );

        this.loadView("/layout/project/list.fxml");
    }

    @FXML public void initialize() {
        this.projectCards.getChildren().add(
            (Node) cardList.getContent()
        );
    }

    @Override
    @FXML public void newProject() {
        Project newProject = this.projectService.getFactory()
            .create("new_project");
        this.projectService.add(newProject);
        this.projectService.select(newProject);
    }

    @Override
    public void openProject(Project project) {
        UseCase.runOnThread(() -> {
            this.loadingIndicator.start();
            this.projectService.open(project);
            this.loadingIndicator.stop();
        });
    }

    @Override
    public void selectProject(Project project) {
        this.projectService.select(project);
    }
}