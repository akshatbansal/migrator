package migrator.app.gui.project;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.card.SimpleCardComponentFactory;
import migrator.ext.javafx.component.ViewLoader;

public class ProjectListView {
    protected ProjectController controller;
    protected Node node;

    @FXML protected VBox projectCards;

    public ProjectListView(ProjectController controller) {
        // this.projectService = container.getProjectService();
        // this.projectGuiKit = projectGuiKit;
        // this.loadingIndicator = loadingIndicator;
        this.controller = controller;
        ViewLoader viewLoader = new ViewLoader();

        CardListComponent<ProjectGuiModel> cards = new CardListComponent<>(
            controller.getList(),
            new SimpleCardComponentFactory<>(
                new ProjectCardAdapter()
            )
        );

        this.node = viewLoader.load(this, "/layout/project/list.fxml");
        this.projectCards.getChildren().addAll(
            cards.getNode()
        );

        cards.event("edit").addListener((observable, oldValue, newValue) -> {
            this.controller.select(newValue.getValue());
        });
    }

    @FXML public void newProject() {
        this.controller.create("new_project");
        List<ProjectGuiModel> projects = this.controller.getList();
        this.controller.select(projects.get(projects.size() - 1));
    }

    public void openProject(ProjectGuiModel project) {
        // UseCase.runOnThread(() -> {
        //     this.loadingIndicator.start();
        //     this.projectService.open(project);
        //     this.loadingIndicator.stop();
        // });
    }

    public void selectProject(ProjectGuiModel project) {
        this.controller.select(project);
    }

    public Node getNode() {
        return this.node;
    }
}