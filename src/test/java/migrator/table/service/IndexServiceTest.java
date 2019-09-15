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
}