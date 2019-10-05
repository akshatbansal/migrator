package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.migration.ChangeService;
import migrator.migration.TableChangeFactory;
import migrator.table.model.Table;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class TableServiceTest {
    protected TableService tableService;

    @BeforeEach
    public void setUp() {
        TableChangeFactory tableChangeFactory = new TableChangeFactory();
        this.tableService = new TableService(
            new ChangeService(tableChangeFactory),
            new TableFactory(tableChangeFactory)
        );
    }

    @Test public void testSelectSetsSelectedValue() {
        this.tableService.select(
            this.tableService.getFactory()
                .createNotChanged(
                    new DatabaseConnection(
                        new Connection("localhost"),
                        "test_db"
                    ),
                    "test_table"
                )
        );

        assertEquals("test_table", this.tableService.getSelected().get().getName());
    }

    @Test public void testAddAddsTableToList() {
        this.tableService.add(
            this.tableService.getFactory()
                .createNotChanged(
                    new DatabaseConnection(
                        new Connection("localhost"),
                        "test_db"
                    ),
                    "test_table"
                )
        );

        assertEquals(1, this.tableService.getList().size());
        assertEquals("test_table", this.tableService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesTableFromList() {
        Table table = this.tableService.getFactory()
        .createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "test_db"
            ),
            "test_table"
        );
        this.tableService.add(table);
        this.tableService.remove(table);

        assertEquals(0, this.tableService.getList().size());
    }

    @Test public void testSetAllSetsListValues() {
        Table table = this.tableService.getFactory()
        .createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "test_db"
            ),
            "test_table"
        );
        this.tableService.setAll(Arrays.asList(table));

        assertEquals(1, this.tableService.getList().size());
        assertEquals("test_table", this.tableService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesSelectedIfNotInTheList() {
        Table table = this.tableService.getFactory()
        .createNotChanged(
            new DatabaseConnection(
                new Connection("localhost"),
                "test_db"
            ),
            "test_table"
        );
        this.tableService.add(table);
        this.tableService.select(table);
        this.tableService.remove(table);

        assertEquals(null, this.tableService.getSelected().get());
    }
}