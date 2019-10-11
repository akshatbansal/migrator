package migrator.ext.javafx.table.component;

// import org.junit.jupiter.api.Test;

// import migrator.ext.javafx.component.FakeViewLoader;
// import migrator.app.ConfigContainer;
import migrator.app.Container;
// import migrator.app.domain.column.service.ColumnRepository;
// import migrator.app.domain.connection.service.ConnectionFactory;
// import migrator.app.domain.database.service.DatabaseFactory;
// import migrator.app.domain.index.service.IndexActiveState;
// import migrator.app.domain.index.service.IndexFactory;
// import migrator.app.domain.index.service.IndexRepository;
// import migrator.app.domain.index.service.SimpleIndexService;
// import migrator.app.domain.project.service.ProjectFactory;
// import migrator.app.domain.project.service.ProjectService;
// import migrator.app.domain.table.service.SimpleTableService;
// import migrator.app.domain.table.service.TableActiveState;
// import migrator.app.domain.table.service.TableFactory;
// import migrator.app.domain.table.service.TableRepository;
// import migrator.app.router.ActiveRoute;
// import migrator.ext.javafx.component.JavafxLayout;
// import migrator.lib.modelstorage.SimpleActiveState;

// import org.junit.jupiter.api.BeforeEach;
// import static org.junit.jupiter.api.Assertions.*;

public class JavafxIndexListTest {
    protected JavafxIndexList javafxIndexList;
    protected Container container;

    // @BeforeEach public void setUp() {
    //     TableActiveState tableActiveState = new TableActiveState(
    //         new TableRepository(),
    //         new ProjectService(
    //             new ProjectFactory(
    //                 new DatabaseFactory(
    //                     new ConnectionFactory()
    //                 )
    //             )
    //         )
    //     );

    //     ConfigContainer configContainer = new ConfigContainer();
    //     configContainer.indexFactoryConfig().set(
    //         new IndexFactory()
    //     );
    //     configContainer.indexActiveStateConfig().set(
    //         new IndexActiveState(
    //             new IndexRepository(),
    //             tableActiveState
    //         )
    //     );
    //     configContainer.activeRouteConfig().set(
    //         new ActiveRoute()
    //     );
    //     this.container = new Container(configContainer);

    //     this.javafxIndexList = new JavafxIndexList(
    //         new FakeViewLoader(),
    //         container
    //     );
    // }

    // @Test public void testAddIndexAddIndexObjectToIndexService() {
    //     this.javafxIndexList.addIndex();
    //     assertEquals(1, this.container.getIndexService().getAll().size());
    // }

    // @Test public void testAddIndexAddIndexWithChangeTypeCreated() {
    //     this.javafxIndexList.addIndex();
    //     assertEquals("create", this.container.getIndexService().getAll().get(0).getChangeCommand().getType());
    // }
}