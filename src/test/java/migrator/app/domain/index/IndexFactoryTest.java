package migrator.app.domain.index;

import org.junit.jupiter.api.Test;

import migrator.app.domain.index.service.IndexFactory;
import migrator.app.domain.table.model.Index;
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
        Index index = this.indexFactory.createNotChanged("1", "index_name", Arrays.asList("id", "name"));

        assertEquals("index_name", index.getOriginalName());
        assertEquals(2, index.originalColumnsProperty().size());
        assertEquals("id", index.originalColumnProperty(0).get());
        assertEquals("name", index.originalColumnProperty(1).get());
        assertEquals("id, name", index.originalColumnsStringProperty().get());
    }
}