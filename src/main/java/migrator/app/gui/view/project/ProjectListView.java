package migrator.app.gui.view.project;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import migrator.app.domain.project.model.Project;
import migrator.app.gui.UseCase;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.project.ProjectCardComponentFactory;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.validation.ProjectConnectionValidator;
import migrator.app.gui.validation.ValidationResult;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectListView extends SimpleView implements View {
    protected CardListComponent<Project> cardList;
    protected ProjectConnectionValidator validator;
    protected EventDispatcher dispatcher;

    @FXML protected VBox projectCards;

    public ProjectListView(
        EventDispatcher dispatcher,
        ObservableList<Project> projects,
        ProjectConnectionValidator validator
    ) {
        super();
        this.validator = validator;
        this.dispatcher = dispatcher;
        this.cardList = new CardListComponent<>(
            new ProjectCardComponentFactory()
        );
        this.cardList.bind(projects);
        this.cardList.outputs().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equals("edit")) {
                dispatcher.dispatch(new SimpleEvent<Object>("project.select", newValue.getValue()));
            } else if (newValue.getName().equals("open")) {
                this.openProject((Project) newValue.getValue());
            }
        });

        this.loadFxml("/layout/project/list.fxml");

        this.projectCards.getChildren().add(cardList.getNode());
    }

    @FXML public void newProject() {
        this.dispatcher.dispatch(new SimpleEvent<Object>("project.new"));
    }

    public void openProject(Project project) {
        UseCase.runOnThread(() -> {
            project.disable();
            ValidationResult result = this.validator.validate(project);
            if (!result.isOk()) {
                this.dispatcher.dispatch(new SimpleEvent<Toast>("toast.push", new Toast(result.getMessage(), "error")));
                project.enable();
                return;
            }
            project.enable();
            Platform.runLater(() -> {
                this.dispatcher.dispatch(new SimpleEvent<Project>("project.open", project));
            });
        });
    }

    public void selectProject(Project project) {
        this.dispatcher.dispatch(new SimpleEvent<Project>("project.select", project));
    }
}