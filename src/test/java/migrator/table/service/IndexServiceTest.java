package migrator.table.service;

import org.junit.jupiter.api.Test;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.table.model.Index;
import migrator.table.model.Table;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class IndexServiceTest {
    protected IndexService indexService;

    @BeforeEach
    public void setUp() {
        this.indexService = new IndexService(
            new IndexFactory()
        );
    }

    @Test public void testSetAllSetsListValues() {
        Index index = this.indexService.getFactory()
            .createNotChanged("index_name", new ArrayList<>());
        this.indexService.setAll(Arrays.asList(index));

        assertEquals(1, this.indexService.getList().size());
        assertEquals("index_name", this.indexService.getList().get(0).getName());
    }

    @Test public void testSelectSetsSelectedValue() {
        this.indexService.select(
            this.indexService.getFactory()
                .createNotChanged("primary", new ArrayList<>())
        );

        assertEquals("primary", this.indexService.getSelected().get().getName());
    }

    @Test public void testAddAddsIndexToList() {
        this.indexService.add(
            this.indexService.getFactory()
                .createNotChanged("primary", new ArrayList<>())
        );

        assertEquals(1, this.indexService.getList().size());
        assertEquals("primary", this.indexService.getList().get(0).getName());
    }

    @Test public void testRemoveRemovesIndexFromList() {
        Index index = this.indexService.getFactory()
            .createNotChanged("primary", new ArrayList<>());
        this.indexService.add(index);
        this.indexService.remove(index);

        assertEquals(0, this.indexService.getList().size());
    }
}