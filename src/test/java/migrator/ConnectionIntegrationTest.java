package migrator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.mock.FakeServerConnectionFactory;
import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.mock.FakeServerConnection;

public class ConnectionIntegrationTest {
    @BeforeEach
    public void setUp() {}

    @Test public void testSetDatabaseListOnConnectionConnect() {
        BusinessLogic businessLogic = new BusinessLogic(
            new FakeServerConnectionFactory(
                new FakeServerConnection(
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

        businessLogic.getConnection().connect(new Connection("localhost"));
        assertEquals(1, businessLogic.getDatabase().getList().size());
        assertEquals("test_db", businessLogic.getDatabase().getList().get(0).getDatabase());
    }

    @Test public void testSetTableListOnDatabaseConnect() {
        BusinessLogic businessLogic = new BusinessLogic(
            new FakeServerConnectionFactory(
                new FakeServerConnection(
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

        businessLogic.getDatabase().connect(
            new DatabaseConnection(
                new Connection("localhost"),
                "test_db"
            )
        );
        assertEquals(1, businessLogic.getTable().getList().size());
        assertEquals("test_table", businessLogic.getTable().getList().get(0).getName());
    }
}