package migrator.app.gui.component.project;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.project.model.Project;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.card.CardComponent;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectCardComponent extends SimpleComponent implements CardComponent<Project> {
    @FXML protected Text name;
    @FXML protected VBox pane;
    @FXML protected Button openButton;

    protected Project project;
    private ChangeListener<Boolean> focusListener;
    private ChangeListener<Boolean> disabledListener;

    public ProjectCardComponent() {
        super();

        this.focusListener = (observable, oldValue, newValue) -> {
            this.setFocus(newValue);
        };
        this.disabledListener = (observable, oldValue, newValue) -> {
            this.setDisabled(newValue);
        };

        this.loadFxml("/layout/project/card.fxml");
    }

    @FXML public void edit() {
        this.output(new SimpleEvent<Project>("edit", this.project));
    }

    @FXML public void open() {
        this.output(new SimpleEvent<Project>("open", this.project));
    }

    @Override
    public Project getValue() {
        return this.project;
    }

    @Override
    public void bind(ObjectProperty<Project> item) {
        this.project = item.get();
        
        this.name.textProperty().bind(project.nameProperty());

        project.focusedProperty().addListener(this.focusListener);
        project.disabledProperty().addListener(this.disabledListener);

        this.setFocus(project.focusedProperty().getValue());
        this.setDisabled(project.disabledProperty().getValue());    
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

    @Override
    public void destroy() {
        this.project.focusedProperty().removeListener(this.focusListener);
        this.project.disabledProperty().removeListener(this.disabledListener);
    }
}