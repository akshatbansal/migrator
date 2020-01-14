package migrator.app.gui.view.project;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.validation.ProjectConnectionValidator;
import migrator.app.gui.validation.ValidationResult;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.ext.javafx.UseCase;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectFormView extends SimpleView implements View {
    protected EventDispatcher dispatcher;
    protected ObservableList<String> databaseDrivers;
    protected ObservableList<String> outputDrivers;
    protected Project project;
    protected ProjectService projectService;
    protected Window window;
    protected ProjectConnectionValidator validator;

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

    public ProjectFormView(
        EventDispatcher dispatcher,
        ObjectProperty<Project> selected,
        ObservableList<String> databaseDrivers,
        ObservableList<String> outputDrivers,
        Window window,
        ProjectConnectionValidator validator
    ) {
        super();
        this.dispatcher = dispatcher;
        this.databaseDrivers = databaseDrivers;
        this.outputDrivers = outputDrivers;
        this.project = selected.get();
        this.window = window;
        this.validator = validator;

        this.loadFxml("/layout/project/form.fxml");

        this.outputType.setItems(outputDrivers);
        this.driver.setItems(this.databaseDrivers);

        selected.addListener((observable, oldValue, newValue) -> {
            this.onProjectChange(newValue);
        });
        this.onProjectChange(project);
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
    
    @FXML public void close() {
        this.dispatcher.dispatch(new SimpleEvent<Project>("project.deselect", this.project));
    }

    @FXML public void open() {
        UseCase.runOnThread(() -> {
            this.project.disable();
            ValidationResult result = this.validator.validate(this.project);
            if (!result.isOk()) {
                this.project.enable();
                this.dispatcher.dispatch(new SimpleEvent<Toast>("toast.push", new Toast(result.getMessage(), "error")));
                return;
            }
            this.project.enable();
            Platform.runLater(() -> {
                this.dispatcher.dispatch(new SimpleEvent<Project>("project.open", this.project));
            });
        });
    }

    @FXML public void delete() {
        this.dispatcher.dispatch(new SimpleEvent<Project>("project.remove", this.project));
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