package migrator.ext.javafx.project.component;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectOpener;
import migrator.app.domain.project.service.ProjectSelector;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.CardComponent;

public class ProjectCard extends ViewComponent implements CardComponent<Project>, ProjectOpener, ProjectSelector {
    @FXML protected Text name;
    @FXML protected VBox pane;
    @FXML protected Button openButton;

    protected Project project;
    protected ProjectOpener projectOpener;
    protected ProjectSelector projectSelector;

    public ProjectCard(
        ViewLoader viewLoader,
        Project project,
        ProjectOpener projectOpener,
        ProjectSelector projectSelector
    ) {
        super(viewLoader);
        this.project = project;
        this.projectOpener = projectOpener;
        this.projectSelector = projectSelector;

        this.loadView("/layout/project/card.fxml");

        this.name.textProperty().bind(project.nameProperty());

        project.focusedProperty().addListener((observable, oldValue, newValue) -> {
            this.setFocus(newValue);
        });
        project.disabledProperty().addListener((observable, oldValue, newValue) -> {
            this.setDisabled(newValue);
        });

        this.setFocus(project.focusedProperty().getValue());
        this.setDisabled(project.disabledProperty().getValue());
    }

    @Override
    public void selectProject(Project project) {
        this.projectSelector.selectProject(project);
    }

    @FXML public void edit() {
        this.selectProject(this.project);
    }

    @FXML public void open() {
        this.openProject(this.project);
    }

    @Override
    public void openProject(Project project) {
        this.projectOpener.openProject(project);
    }

    @Override
    public Project getValue() {
        return this.project;
    }

    protected void setFocus(Boolean focused) {
        if (focused) {
            this.pane.getStyleClass().add("card--active");
        } else {
            this.pane.getStyleClass().remove("card--active");
        }
    }

    protected void setDisabled(Boolean disabled) {
        this.openButton.setDisable(disabled);
    }
}