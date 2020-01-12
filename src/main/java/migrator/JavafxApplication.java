package migrator;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import migrator.app.boot.ApplicationService;
import migrator.app.domain.project.model.Project;
import migrator.ext.flyway.FlywayExtension;
import migrator.ext.mariadb.MariadbExtension;
import migrator.ext.mysql.MysqlExtension;
import migrator.ext.phinx.PhinxExtension;
import migrator.ext.php.PhpExtension;
import migrator.ext.postgresql.PostgresqlExtension;
import migrator.ext.sentry.SentryExtension;
import migrator.ext.sql.SqlExtension;
import migrator.lib.persistance.ListPersistance;
import migrator.lib.persistance.Persistance;
import migrator.app.EnviromentConfig;

public class JavafxApplication extends Application {
    protected Persistance<List<Project>> projectsPersistance;
    protected ApplicationService applicationService;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.projectsPersistance = new ListPersistance<>("project.list");

        String env = "production";
        if (System.getenv("MIGRATOR_ENV") != null) {
            env = System.getenv("MIGRATOR_ENV");
        }
        EnviromentConfig enviromentConfig = new EnviromentConfig(env);

        // Bootstrap bootstrap = new Bootstrap(
        //     Arrays.asList(
        //         new PhinxExtension(),
        //         new FlywayExtension(),
        //         new MysqlExtension(),
        //         new MariadbExtension(),
        //         new PostgresqlExtension(),
        //         new PhpExtension(),
        //         new SqlExtension(),
        //         new SentryExtension(enviromentConfig.getProperties("sentry"))
        //     )
        // );

        migrator.app.boot.Container bootContainer = new migrator.app.boot.Container();
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
