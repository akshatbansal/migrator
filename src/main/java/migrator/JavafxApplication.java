package migrator;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import migrator.app.boot.ApplicationService;
import migrator.app.boot.Container;
import migrator.app.domain.project.model.Project;
import migrator.lib.persistance.ListPersistance;
import migrator.lib.persistance.Persistance;

public class JavafxApplication extends Application {
    protected Persistance<List<Project>> projectsPersistance;
    protected ApplicationService applicationService;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.projectsPersistance = new ListPersistance<>("project.list");

        Container bootContainer = new Container();
        this.applicationService = new ApplicationService(bootContainer, primaryStage);
        this.applicationService.start();

        
        // container.getHotkeyService().connectKeyboard("find", "CTRL+F");
        // container.getHotkeyService().connectKeyboard("cancel", "ESCAPE");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        this.applicationService.stop();
        super.stop();
    }
}
