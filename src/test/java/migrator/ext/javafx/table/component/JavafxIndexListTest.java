package migrator.ext.javafx.table.component;

import org.junit.jupiter.api.Test;

import migrator.router.BasicRouter;
import migrator.app.domain.table.service.IndexFactory;
import migrator.app.domain.table.service.IndexService;
import migrator.mock.FakeView;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class JavafxIndexListTest {
    @BeforeEach public void setUp() {

    }

    @Test public void testAddIndexAddIndexObjectToIndexService() {
        IndexService indexService = new IndexService(
            new IndexFactory()
        );
        JavafxIndexList javafxIndexList = new JavafxIndexList(
            new FakeView(),
            indexService,
            new BasicRouter()
        );

        javafxIndexList.addIndex();
        assertEquals(1, indexService.getList().size());
    }

    @Test public void testAddIndexAddIndexWithChangeTypeCreated() {
        IndexService indexService = new IndexService(
            new IndexFactory()
        );
        JavafxIndexList javafxIndexList = new JavafxIndexList(
            new FakeView(),
            indexService,
            new BasicRouter()
        );

        javafxIndexList.addIndex();
        assertEquals("create", indexService.getList().get(0).getChangeCommand().getType());
    }
}