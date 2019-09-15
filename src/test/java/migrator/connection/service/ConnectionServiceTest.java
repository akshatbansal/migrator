package migrator.connection.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import migrator.connection.model.Connection;
import migrator.connection.service.ConnectionService;

public class ConnectionServiceTest {
    protected ConnectionService connectionService;

    @BeforeEach
    public void setUp() {
        this.connectionService = new ConnectionService();
    }

    @Test public void testConnectSetsConnectedValue() {
        Connection connection = new Connection("localhost");
        this.connectionService.connect(connection);

        assertEquals(connection, this.connectionService.getConnected().get());
    }

    @Test public void testSelectSetsSelectedValue() {
        Connection connection = new Connection("localhost");
        this.connectionService.select(connection);

        assertEquals(connection, this.connectionService.getSelected().get());
    }

    @Test public void testAddAddsConnectionToList() {
        Connection connection = new Connection("localhost");
        this.connectionService.add(connection);
        
        assertEquals(1, this.connectionService.getList().size());
        assertEquals(connection, this.connectionService.getList().get(0));
    }

    @Test public void testRemoveRemovesConnectionFromList() {
        Connection connection = new Connection("localhost");
        this.connectionService.add(connection);
        this.connectionService.remove(connection);
        
        assertEquals(0, this.connectionService.getList().size());
    }

    @Test public void testRemoveSelectedConnectionClearsSelected() {
        Connection connection = new Connection("localhost");
        this.connectionService.add(connection);
        this.connectionService.select(connection);
        this.connectionService.remove(connection);
        
        assertEquals(null, this.connectionService.getSelected().get());
    }

    @Test public void testDeselectedSetsSelectedToNull() {
        Connection connection = new Connection("localhost");
        this.connectionService.select(connection);
        this.connectionService.deselect();
        
        assertEquals(null, this.connectionService.getSelected().get());
    }

    @Test public void testDisconnectSetsConnectedToNull() {
        Connection connection = new Connection("localhost");
        this.connectionService.connect(connection);
        this.connectionService.disconnect();
        
        assertEquals(null, this.connectionService.getConnected().get());
    }

    @Test public void testInitializeConnections() {
        Connection connection = new Connection("localhost");
        this.connectionService = new ConnectionService(connection);
        
        assertEquals(1, this.connectionService.getList().size());
        assertEquals(connection, this.connectionService.getList().get(0));
    }
}