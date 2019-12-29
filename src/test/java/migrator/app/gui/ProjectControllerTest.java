package migrator.app.gui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;

import migrator.app.gui.project.ProjectController;
import migrator.app.gui.project.ProjectGuiModel;
import migrator.app.project.property.SimpleDatabaseProperty;
import migrator.app.project.property.SimpleProjectProperty;

public class ProjectControllerTest {
    private ProjectGuiModel createProjectGuiModel() {
        return new ProjectGuiModel(new SimpleProjectProperty(
            "",
            "",
            "",
            "",
            new SimpleDatabaseProperty(
                "",
                "",
                "",
                ""
            )
        ));
    }

    @Test public void select_null_previousNullDoesNothing() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        controller.select(null);
        ObjectProperty<ProjectGuiModel> result = controller.selectedProperty();

        assertNull(result.get());
    }

    @Test public void select_project_previousNullSetSelectedToProject() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        ProjectGuiModel project = this.createProjectGuiModel();
        controller.select(project);
        ObjectProperty<ProjectGuiModel> result = controller.selectedProperty();

        assertEquals(project, result.get());
    }

    @Test public void select_null_previousHasProjectSelectedSetSelectedToNull() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        controller.select(this.createProjectGuiModel());
        controller.select(null);
        ObjectProperty<ProjectGuiModel> result = controller.selectedProperty();

        assertNull(result.get());
    }

    @Test public void select_project_setsProjectSelectedAttributeToTrue() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        ProjectGuiModel project = this.createProjectGuiModel();
        controller.select(project);

        assertTrue(project.attribute("selected").get());
    }

    @Test public void select_null_setsPreviousProjectSelectedAttributeToFalse() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        ProjectGuiModel project = this.createProjectGuiModel();
        controller.select(project);
        controller.select(null);

        assertFalse(project.attribute("selected").get());
    }

    @Test public void deselect_nothingSelected_keepSelectedSetToNull() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        controller.deselect();
        ObjectProperty<ProjectGuiModel> result = controller.selectedProperty();

        assertNull(result.get());
    }

    @Test public void deselect_selectedProject_setSelectedToNull() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        ProjectGuiModel project = this.createProjectGuiModel();
        controller.select(project);
        controller.deselect();
        ObjectProperty<ProjectGuiModel> result = controller.selectedProperty();

        assertNull(result.get());
    }

    @Test public void deselect_selectedProject_setPreviousProjectsSelectedAttributeToFalse() {
        ProjectController controller = new ProjectController(
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),FXCollections.observableArrayList(),
            new SimpleObjectProperty<>()
        );

        ProjectGuiModel project = this.createProjectGuiModel();
        controller.select(project);
        controller.deselect();

        assertFalse(project.attribute("selected").get());
    }
}