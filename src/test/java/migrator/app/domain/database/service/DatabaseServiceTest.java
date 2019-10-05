package migrator.app.domain.connection.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseService;

public class DatabaseServiceTest {
    protected DatabaseService databaseService;

    @BeforeEach
    public void setUp() {
        this.databaseService = new DatabaseService();
    }

    @Test public void testConnectSetsConnectedValue() {
        DatabaseConnection databaseConnection = new DatabaseConnection(new Connection("localhost"), "test_db");
        this.databaseService.connect(databaseConnection);

        assertEquals(databaseConnection, this.databaseService.getConnected().get());
    }

    @Test public void testSetAllSetsDatabaseList() {
        DatabaseConnection databaseConnection = new DatabaseConnection(new Connection("localhost"), "test_db");
        this.databaseService.setAll(Arrays.asList(databaseConnection));

        assertEquals(1, this.databaseService.getList().size());
        assertEquals(databaseConnection, this.databaseService.getList().get(0));
    }
}