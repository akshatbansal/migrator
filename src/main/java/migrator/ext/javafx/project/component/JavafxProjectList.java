package migrator.ext.javafx.project.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.domain.project.component.ProjectList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectGuiKit;
import migrator.app.domain.project.service.ProjectService;
import migrator.ext.javafx.component.card.CardListComponent;
import migrator.ext.javafx.component.card.simple.SimpleCardComponentFactory;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxProjectList extends ViewComponent implements ProjectList {
    protected ProjectService projectService;
    protected ProjectGuiKit projectGuiKit;
    protected CardListComponent<Project> cardList;

    @FXML protected VBox projectCards;

    public JavafxProjectList(ViewLoader viewLoader, Container container, ProjectGuiKit projectGuiKit) {
        super(viewLoader);
        this.projectService = container.getProjectService();
        this.projectGuiKit = projectGuiKit;

        this.cardList = new CardListComponent<>(
            this.projectService.getList(),
            new ProjectCardFactory(),
            new SimpleCardComponentFactory<>(viewLoader),
            this.viewLoader
        );

        this.cardList.onSecondary((Project eventDataProject) -> {
            this.projectService.select(eventDataProject);
        });

        this.cardList.onPrimary((Project eventDataProject) -> {
            this.projectService.open(eventDataProject);
        });

        this.loadView("/layout/project/list.fxml");

        this.projectService.getSelected()
            .addListener((obs, oldValue, newValue) -> {
                this.cardList.focus(newValue);
            });
        this.cardList.focus(this.projectService.getSelected().get());
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
}