package migrator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.Bootstrap;
import migrator.app.BusinessLogic;
import migrator.app.ConfigContainer;
import migrator.app.Container;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseService;
import migrator.app.domain.table.service.TableService;
import migrator.mock.FakeDatabaseDriver;
import migrator.mock.FakeDatabaseDriverManager;
import migrator.mock.DataExtension;

public class ConnectionIntegrationTest {
    protected Container container;

    @BeforeEach
    public void setUp() {
        ConfigContainer dataExtensionConfig = new ConfigContainer();
        dataExtensionConfig.databaseDriverManagerConfig()
            .set(
                new FakeDatabaseDriverManager(
                    new FakeDatabaseDriver(
                        Arrays.asList("test_db"),
                        Arrays.asList("test_table"),
                        Arrays.asList(
                            Arrays.asList("column")
                        ),
                        Arrays.asList(
                            Arrays.asList("index")
                        )
                    )
                )
            );

        Bootstrap bootstrap = new Bootstrap(
            new DataExtension(dataExtensionConfig)
        );
        this.container = bootstrap.getContainer();
    }

    @Test public void testSetDatabaseListOnConnectionConnect() {
        new BusinessLogic(this.container);
        this.container.getConnectionService()
            .connect(new Connection("localhost"));
        
        DatabaseService databaseService = this.container.getDatabaseService();
        assertEquals(1, databaseService.getList().size());
        assertEquals("test_db", databaseService.getList().get(0).getDatabase());
    }

    @Test public void testSetTableListOnDatabaseConnect() {
        new BusinessLogic(this.container);
        this.container.getDatabaseService()
            .connect(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                )
            );

        TableService tableService = this.container.getTableService();
        assertEquals(1, tableService.getList().size());
        assertEquals("test_table", tableService.getList().get(0).getName());
    }
}