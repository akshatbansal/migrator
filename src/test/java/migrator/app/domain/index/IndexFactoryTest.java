package migrator.app.domain.index;

import org.junit.jupiter.api.Test;

import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.lib.uid.SessionIncrementalGenerator;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

public class IndexFactoryTest {
    protected IndexFactory indexFactory;

    @BeforeEach
    public void setUp() {
        this.indexFactory = new IndexFactory(
            new SessionIncrementalGenerator("IndexFactory")
        );
    }

    @Test public void testCreateNotChangedCreatesIndexWithChangeCommandNone() {
        Index index = this.indexFactory.createNotChanged("1", "index_name", new ArrayList<>());

        assertEquals("", index.getChangeCommand().getType());
    }

    @Test public void testCreateNotChangedSetsOriginalValuesByArguments() {
        Index index = this.indexFactory.createNotChanged(
            "1",
            "index_name",
            Arrays.asList(
                new SimpleColumnProperty("1", "id", "", "", false, "", false, "", false),
                new SimpleColumnProperty("2", "name", "", "", false, "", false, "", false)
            )
        );

        assertEquals("index_name", index.getOriginalName());
        assertEquals(2, index.originalColumnsProperty().size());
        assertEquals("id", index.getOriginal().columnsProperty().get(0).getName());
        assertEquals("name", index.getOriginal().columnsProperty().get(1).getName());
        assertEquals("id, name", index.originalColumnsStringProperty().get());
    }
}