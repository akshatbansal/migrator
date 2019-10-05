package migrator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.table.model.Table;
import migrator.app.BusinessLogic;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.connection.service.ConnectionService;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.mock.FakeDatabaseDriver;
import migrator.mock.FakeDatabaseDriverManager;

public class TableIntegrationTest {
    @BeforeEach
    public void setUp() {}

    @Test public void testSetColumnsOnTableSelect() {
        BusinessLogic businessLogic = new BusinessLogic(
            new FakeDatabaseDriverManager(
                new FakeDatabaseDriver(
                    Arrays.asList("test_db"),
                    Arrays.asList("test_table"),
                    Arrays.asList(
                        Arrays.asList("column", "string", "NO", "")
                    ),
                    Arrays.asList(
                        Arrays.asList("index", "id")
                    )
                )
            )
        );

        businessLogic.getTable().select(
            businessLogic.getTable().getFactory()
                .createNotChanged(
                    new DatabaseConnection(
                        new Connection("localhost"),
                        "test_db"
                    ),
                    "test_table"
                )
        );
        assertEquals(1, businessLogic.getColumn().getList().size());
        assertEquals("column", businessLogic.getColumn().getList().get(0).getName());
    }

    @Test public void testSetIndexesOnTableSelect() {
        BusinessLogic businessLogic = new BusinessLogic(
            new FakeDatabaseDriverManager(
                new FakeDatabaseDriver(
                    Arrays.asList("test_db"),
                    Arrays.asList("test_table"),
                    Arrays.asList(
                        Arrays.asList("column", "string", "NO", "")
                    ),
                    Arrays.asList(
                        Arrays.asList("index_name", "id")
                    )
                )
            )
        );

        businessLogic.getTable().select(
            businessLogic.getTable().getFactory()
                .createNotChanged(
                    new DatabaseConnection(
                        new Connection("localhost"),
                        "test_db"
                    ),
                    "test_table"
                )
        );
        assertEquals(1, businessLogic.getIndex().getList().size());
        assertEquals("index_name", businessLogic.getIndex().getList().get(0).getName());
    }
}