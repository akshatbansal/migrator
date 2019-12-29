package migrator.app.gui.project;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import migrator.ext.javafx.UseCase;
import migrator.ext.javafx.component.ViewLoader;

public class ProjectFormView {
    protected Node node;
    protected ProjectController controller;
    protected Window window;

    @FXML protected TextField name;
    @FXML protected ComboBox<String> outputType;
    @FXML protected TextField folder;
    @FXML protected ComboBox<String> driver;
    @FXML protected TextField host;
    @FXML protected TextField port;
    @FXML protected TextField database;
    @FXML protected TextField user;
    @FXML protected PasswordField password;
    @FXML protected HBox buttonsBox;
    @FXML protected Button openButton;

    public ProjectFormView(ProjectController controller, Window window) {
        this.controller = controller;
        this.window = window;

        ViewLoader viewLoader = new ViewLoader();
        this.node = viewLoader.load(this, "/layout/project/form.fxml");

        // TODO: listen for changes in outputList and update select
        this.outputType.getItems().setAll(
            controller.getOutputList()
        );

        // TODO: listen for changes in drivers list and update select
        this.driver.getItems().setAll(
            controller.getDatabaseDriverList()
        );

        controller.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.unsetProject(oldValue);
            this.setProject(newValue);
        });
        this.setProject(controller.selectedProperty().get());
    }

    protected void unsetProject(ProjectGuiModel project) {
        if (project == null) {
            return;
        }
        this.name.textProperty().unbindBidirectional(
            project.getProjectProperty().nameProperty()
        );
        this.outputType.valueProperty().unbindBidirectional(
            project.getProjectProperty().outputProperty()
        );
        this.folder.textProperty().unbindBidirectional(
            project.getProjectProperty().folderProperty()
        );

        this.host.textProperty().unbindBidirectional(
            project.hostProperty()
        );
        this.driver.valueProperty().unbindBidirectional(
            project.getProjectProperty().getDatabase().driverProperty()
        );
        this.port.textProperty().unbindBidirectional(
            project.portProperty()
        );
        this.database.textProperty().unbindBidirectional(
            project.databaseProperty()
        );
        this.user.textProperty().unbindBidirectional(
            project.getProjectProperty().getDatabase().userProperty()
        );
        this.password.textProperty().unbindBidirectional(
            project.getProjectProperty().getDatabase().passwordProperty()
        );
    }

    protected void setProject(ProjectGuiModel project) {
        if (project == null) {
            return;
        }
        this.name.textProperty().bindBidirectional(
            project.getProjectProperty().nameProperty()
        );
        this.outputType.valueProperty().bindBidirectional(
            project.getProjectProperty().outputProperty()
        );
        this.folder.textProperty().bindBidirectional(
            project.getProjectProperty().folderProperty()
        );

        this.host.textProperty().bindBidirectional(
            project.hostProperty()
        );
        this.driver.valueProperty().bindBidirectional(
            project.getProjectProperty().getDatabase().driverProperty()
        );
        this.port.textProperty().bindBidirectional(
            project.portProperty()
        );
        this.database.textProperty().bindBidirectional(
            project.databaseProperty()
        );
        this.user.textProperty().bindBidirectional(
            project.getProjectProperty().getDatabase().userProperty()
        );
        this.password.textProperty().bindBidirectional(
            project.getProjectProperty().getDatabase().passwordProperty()
        );
    }

    public Node getNode() {
        return this.node;
    }

    @FXML public void close() {
        this.controller.deselect();
    }

    @FXML public void open() {
        UseCase.runOnThread(() -> {
            // this.loadingIndicator.start();
            this.controller.open(this.controller.selectedProperty().get());
            // this.loadingIndicator.stop();
        });
    }

    @FXML public void delete() {
        this.controller.remove(this.controller.selectedProperty().get());
        this.close();
    }

    @FXML public void chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(this.window);

        if(selectedDirectory == null){
            return;
        }
        
        this.folder.textProperty().set(
            selectedDirectory.getAbsolutePath()
        );
    }
}