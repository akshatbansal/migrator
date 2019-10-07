package migrator.ext.javafx.table.component;

import org.junit.jupiter.api.Test;

import migrator.mock.FakeViewLoader;
import migrator.app.ConfigContainer;
import migrator.app.Container;
import migrator.app.domain.table.service.IndexFactory;
import migrator.app.domain.table.service.IndexService;
import migrator.app.router.ActiveRoute;
import migrator.ext.javafx.component.JavafxLayout;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class JavafxIndexListTest {
    protected JavafxIndexList javafxIndexList;
    protected Container container;

    @BeforeEach public void setUp() {
        ConfigContainer configContainer = new ConfigContainer();
        configContainer.indexFactoryConfig()
            .set(new IndexFactory());
        configContainer.indexServiceConfig()
            .set(new IndexService(
                configContainer.indexFactoryConfig().get()
            ));
        configContainer.activeRouteConfig()
            .set(new ActiveRoute());
        this.container = new Container(configContainer);

        this.javafxIndexList = new JavafxIndexList(
            new FakeViewLoader(),
            container
        );
    }

    @Test public void testAddIndexAddIndexObjectToIndexService() {
        this.javafxIndexList.addIndex();
        assertEquals(1, this.container.getIndexService().getList().size());
    }

    @Test public void testAddIndexAddIndexWithChangeTypeCreated() {
        this.javafxIndexList.addIndex();
        assertEquals("create", this.container.getIndexService().getList().get(0).getChangeCommand().getType());
    }
}