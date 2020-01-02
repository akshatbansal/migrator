package migrator.ext.javafx.project.component;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.Container;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.loading.LoadingIndicator;
import migrator.app.migration.Migration;
import migrator.ext.javafx.UseCase;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxProjectForm extends ViewComponent implements ProjectForm {
    protected ObservableList<String> databaseDrivers;
    protected Migration migration;
    protected Project project;
    protected ProjectService projectService;
    protected Window window;
    protected LoadingIndicator loadingIndicator;

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

    public JavafxProjectForm(ObjectProperty<Project> selected, Container container, Window window, LoadingIndicator loadingIndicator) {
        super(new ViewLoader());
        this.databaseDrivers = container.getDatanaseContainer().getDrivers();
        this.migration = container.getMigration();
        this.projectService = container.getProjectService();
        this.project = selected.get();
        this.window = window;
        this.loadingIndicator = loadingIndicator;

        this.loadView("/layout/project/form.fxml");

        this.outputType.getItems().setAll(this.migration.getGeneratorNames());

        this.driver.getItems().setAll(this.databaseDrivers);

        selected.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });

        // project.disabledProperty().addListener((oservable, oldValue, newValue) -> {
        //     this.openButton.setDisable(newValue);
        // });
    }

    private void onProjectChange(Project project) {
        if (this.project != null) {
            this.unbind(this.project);
        }

        this.project = project;
        if (this.project == null) {
            return;
        }
        
        this.name.textProperty().bindBidirectional(this.project.nameProperty());
        this.outputType.valueProperty().bindBidirectional(this.project.outputTypeProperty());
        this.folder.textProperty().bindBidirectional(this.project.folderProperty());

        this.host.textProperty().bindBidirectional(this.project.getDatabase().getConnection().hostProperty());
        this.driver.valueProperty().bindBidirectional(this.project.getDatabase().getConnection().driverProperty());
        this.port.textProperty().bindBidirectional(this.project.getDatabase().getConnection().portProperty());
        this.database.textProperty().bindBidirectional(this.project.getDatabase().databaseProperty());
        this.user.textProperty().bindBidirectional(this.project.getDatabase().getConnection().userProperty());
        this.password.textProperty().bindBidirectional(this.project.getDatabase().getConnection().passwordProperty());

        this.openButton.disableProperty().bind(
            this.project.disabledProperty()
        );
    }

    private void unbind(Project project) {
        this.name.textProperty().unbindBidirectional(project.nameProperty());
        this.outputType.valueProperty().unbindBidirectional(project.outputTypeProperty());
        this.folder.textProperty().unbindBidirectional(project.folderProperty());

        this.host.textProperty().unbindBidirectional(project.getDatabase().getConnection().hostProperty());
        this.driver.valueProperty().unbindBidirectional(project.getDatabase().getConnection().driverProperty());
        this.port.textProperty().unbindBidirectional(project.getDatabase().getConnection().portProperty());
        this.database.textProperty().unbindBidirectional(project.getDatabase().databaseProperty());
        this.user.textProperty().unbindBidirectional(project.getDatabase().getConnection().userProperty());
        this.password.textProperty().unbindBidirectional(project.getDatabase().getConnection().passwordProperty());

        this.openButton.disableProperty().unbind();
    }
    

    @Override
    @FXML public void close() {
        this.projectService.deselect();
    }

    @Override
    @FXML public void open() {
        UseCase.runOnThread(() -> {
            this.loadingIndicator.start();
            this.projectService.open(project);
            this.loadingIndicator.stop();
        });
    }

    @Override
    @FXML public void delete() {
        this.projectService.remove(this.project);
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