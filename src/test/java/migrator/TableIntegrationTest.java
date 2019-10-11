package migrator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.domain.table.model.Table;
import migrator.app.domain.column.service.ColumnService;
import migrator.app.domain.index.service.IndexService;
import migrator.app.Bootstrap;
import migrator.app.ConfigContainer;
import migrator.app.Container;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.lib.database.driver.FakeDatabaseDriver;
import migrator.lib.database.driver.FakeDatabaseDriverManager;
import migrator.app.DataExtension;

public class TableIntegrationTest {
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
    //                         Arrays.asList("column_name", "string", "NO", "")
    //                     ),
    //                     Arrays.asList(
    //                         Arrays.asList("index_name", "id")
    //                     )
    //                 )
    //             )
    //         );

    //     Bootstrap bootstrap = new Bootstrap(
    //         new DataExtension(dataExtensionConfig)
    //     );
    //     this.container = bootstrap.getContainer();
    // }

    // @Test public void testSetColumnsOnTableSelect() {
    //     this.container.getColumnService().start();

    //     this.container.getTableService()
    //         .select(
    //             this.container.getTableFactory()
    //                 .createNotChanged(
    //                     new Project(
    //                         new DatabaseConnection(
    //                             new Connection("localhost"),
    //                             "test_db"
    //                         ),
    //                         "project_name",
    //                         "phinx",
    //                         ""
    //                     ),
    //                     "test_table"
    //                 )
    //         );
            
    //     ColumnService columnService = this.container.getColumnService();
    //     assertEquals(1, columnService.getList().size());
    //     assertEquals("column_name", columnService.getList().get(0).getName());
    // }

    // @Test public void testSetIndexesOnTableSelect() {
    //     this.container.getIndexService().start();

    //     this.container.getTableService()
    //         .select(
    //             this.container.getTableFactory()
    //                 .createNotChanged(
    //                     new Project(
    //                         new DatabaseConnection(
    //                             new Connection("localhost"),
    //                             "test_db"
    //                         ),
    //                         "project_name",
    //                         "phinx",
    //                         ""
    //                     ),
    //                     "test_table"
    //                 )
    //         );

    //     IndexService indexService = this.container.getIndexService();
    //     assertEquals(1, indexService.getList().size());
    //     assertEquals("index_name", indexService.getList().get(0).getName());
    // }
}