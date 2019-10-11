package migrator;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import static org.junit.jupiter.api.Assertions.*;

// import java.util.Arrays;

// import migrator.app.Bootstrap;
// import migrator.app.ConfigContainer;
import migrator.app.Container;
// import migrator.app.domain.connection.model.Connection;
// import migrator.app.domain.database.model.DatabaseConnection;
// import migrator.app.domain.database.service.DatabaseService;
// import migrator.app.domain.project.model.Project;
// import migrator.app.domain.table.service.TableService;
// import migrator.lib.database.driver.FakeDatabaseDriver;
// import migrator.lib.database.driver.FakeDatabaseDriverManager;
// import migrator.app.DataExtension;

public class ProjectIntegrationTest {
    protected Container container;

    // @BeforeEach
    // public void setUp() {
    //     ConfigContainer dataExtensionConfig = new ConfigContainer();
    //     dataExtensionConfig.databaseDriverManagerConfig()
    //         .set(
    //             new FakeDatabaseDriverManager(
    //                 new FakeDatabaseDriver(
    //                     Arrays.asList("test_db"),
    //                     Arrays.asList("test_table"),
    //                     Arrays.asList(
    //                         Arrays.asList("column")
    //                     ),
    //                     Arrays.asList(
    //                         Arrays.asList("index")
    //                     )
    //                 )
    //             )
    //         );

    //     Bootstrap bootstrap = new Bootstrap(
    //         new DataExtension(dataExtensionConfig)
    //     );
    //     this.container = bootstrap.getContainer();
    // }

    // @Test public void testSetTableListOnProjectOpen() {
    //     this.container.getTableService().start();

    //     this.container.getProjectService()
    //         .open(
    //             new Project(
    //                 new DatabaseConnection(
    //                     new Connection("localhost"),
    //                     "test_db"
    //                 ),
    //                 "project_name",
    //                 "phinx",
    //                 ""
    //             )
    //         );

    //     TableService tableService = this.container.getTableService();
    //     assertEquals(1, tableService.getList().size());
    //     assertEquals("test_table", tableService.getList().get(0).getName());
    // }
}