package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.connection.model.Connection;
import migrator.database.model.DatabaseConnection;
import migrator.table.model.Index;
import migrator.table.model.Table;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class IndexServiceTest {
    protected IndexService indexService;

    @BeforeEach
    public void setUp() {
        this.indexService = new IndexService();
    }

    @Test public void testSetAllSetsListValues() {
        Index index = new Index("index_name");
        this.indexService.setAll(Arrays.asList(index));

        assertEquals(1, this.indexService.getList().size());
        assertEquals("index_name", this.indexService.getList().get(0).getName());
    }

    @Test public void testSelectSetsSelectedValue() {
        this.indexService.select(
            new Index("primary")
        );

        assertEquals("primary", this.indexService.getSelected().get().getName());
    }

    @Test public void testAddAddsIndexToList() {
        this.indexService.add(
            new Index("primary")
        );

        assertEquals(1, this.indexService.getList().size());
        assertEquals("primary", this.indexService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesIndexFromList() {
        Index index = new Index("primary");
        this.indexService.add(index);
        this.indexService.remove(index);

        assertEquals(0, this.indexService.getList().size());
    }
}