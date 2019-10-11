package migrator.ext.javafx.project.component;

import java.io.File;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import migrator.app.Container;
import migrator.app.database.driver.DatabaseDriverManager;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.migration.Migration;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxProjectForm extends ViewComponent implements ProjectForm {
    protected DatabaseDriverManager databaseDriverManager;
    protected Migration migration;
    protected ActiveRoute activeRoute;
    protected Project project;
    protected ProjectService projectService;
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

    public JavafxProjectForm(Project project, ViewLoader viewLoader,Container container, Window window) {
        super(viewLoader);
        this.databaseDriverManager = container.getDatabaseDriverManager();
        this.migration = container.getMigration();
        this.activeRoute = container.getActiveRoute();
        this.projectService = container.getProjectService();
        this.project = project;
        this.window = window;

        this.loadView("/layout/project/form.fxml");
    }

    @FXML public void initialize() {
        this.outputType.getItems().setAll(
            this.migration.getGeneratorNames()
        );

        this.driver.getItems().setAll(this.databaseDriverManager.getDriverNames());

        this.name.textProperty().bindBidirectional(
            this.project.nameProperty()
        );
        this.outputType.valueProperty().bindBidirectional(
            this.project.outputTypeProperty()
        );
        this.folder.textProperty().bindBidirectional(
            this.project.folderProperty()
        );

        this.host.textProperty().bindBidirectional(
            this.project.getDatabase().getConnection().hostProperty()
        );
        this.driver.valueProperty().bindBidirectional(
            this.project.getDatabase().getConnection().driverProperty()
        );
        this.port.textProperty().bindBidirectional(
            this.project.getDatabase().getConnection().portProperty()
        );
        this.database.textProperty().bindBidirectional(
            this.project.getDatabase().databaseProperty()
        );
        this.user.textProperty().bindBidirectional(
            this.project.getDatabase().getConnection().userProperty()
        );
        this.password.textProperty().bindBidirectional(
            this.project.getDatabase().getConnection().passwordProperty()
        );
    }

    @Override
    @FXML public void close() {
        this.projectService.deselect();
        this.activeRoute.changeTo("project.index");
    }

    @Override
    @FXML public void open() {
        this.projectService.open(this.project);
        // this.activeRoute.changeTo("table.index");
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