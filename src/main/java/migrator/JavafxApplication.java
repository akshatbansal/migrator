package migrator;

import javafx.application.Application;
import javafx.stage.Stage;
import migrator.app.boot.ApplicationService;
import migrator.app.boot.Container;
import migrator.app.gui.service.GuiService;

public class JavafxApplication extends Application {
    protected ApplicationService applicationService;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Container bootContainer = new Container();
        this.applicationService = new ApplicationService(bootContainer);
        this.applicationService.bindUserInterface(
            new GuiService(bootContainer, primaryStage)
        );
        this.applicationService.start();
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
