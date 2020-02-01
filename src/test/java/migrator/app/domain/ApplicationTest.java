package migrator.app.domain;

import migrator.app.boot.ApplicationService;
import migrator.app.boot.Container;
import migrator.app.domain.database.mock.DatabaseTestable;
import migrator.app.domain.database.mock.MockDatabaseStructureFactory;

public class ApplicationTest {
    private Container container;
    private ApplicationService appService;
    private MockDatabaseStructureFactory mockDatabaseStructureFactory;

    public ApplicationTest() {
        this.mockDatabaseStructureFactory = new MockDatabaseStructureFactory();
        this.container = new Container();
        this.container.filesystem().setProxy(
            new MockFilesystem()
        );
        this.container.persistantsystem().setProxy(
            new MockPersistantsystem()
        );
        this.appService = new ApplicationService(this.container);
        this.container.databaseContainer()
            .addStrucutreFactory("mysql", this.mockDatabaseStructureFactory);
    }

    public DatabaseTestable getDatabase() {
        return this.mockDatabaseStructureFactory;
    }

    public ApplicationService getAppService() {
        return this.appService;
    }

    public Container getContainer() {
        return this.container;
    }
}