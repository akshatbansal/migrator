package migrator.app.domain.project.action;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.boot.Container;
import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.service.ProjectService;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class ProjectOpenHandlerTest {
    private ProjectService projectService;
    private EventDispatcher dispatcher;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.container = new Container();
        this.container.databaseContainer().addStrucutreFactory("dbStructure", new MockDatabaseStructureFactory(
            new ConnectionResult<Boolean>(true),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList()
        ));
        this.projectService = new ProjectService(container);
        this.dispatcher = container.dispatcher();
    }

    private Project createProjectWithDriver(String driver) {
        return new Project(
            new DatabaseConnection(
                new Connection("name", "user", "", "localhost", "3303", driver), "database"
            ),
            "id",
            "name",
            "dbStructure",
            "folder"
        );
    }

    @Test
    public void onProjectOpen_whenProjectClosed_projectStoreOpenedHasValue() {
        this.projectService.start();

        Project project = this.createProjectWithDriver("dbStructure");
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.open", project)
        );

        assertEquals(
            project,
            this.container.projectStore().getOpened().get().getProject()
        );
    }

    @Test
    public void onProjectOpen_whenOtherProjectOpened_projectStoreOpenedHasValue() {
        this.projectService.start();
        this.container.projectStore().getOpened().set(
            new ProjectContainer(
                new Project(null, null, null, null, null),
                this.container.databaseContainer().getStructureFactoryFor("dbStructure").create("", "", "")
            )
        );

        Project project = this.createProjectWithDriver("dbStructure");
        this.dispatcher.dispatch(
            new SimpleEvent<>("project.open", project)
        );

        assertEquals(
            project,
            this.container.projectStore().getOpened().get().getProject()
        );
    }

    @Test
    public void onProjectOpenNull_whenOtherProjectOpened_projectStoreOpenedIsNull() {
        this.projectService.start();
        this.container.projectStore().getOpened().set(
            new ProjectContainer(
                new Project(null, null, null, null, null),
                this.container.databaseContainer().getStructureFactoryFor("dbStructure").create("", "", "")
            )
        );

        this.dispatcher.dispatch(
            new SimpleEvent<>("project.open", null)
        );

        assertNull(
            this.container.projectStore().getOpened().get()
        );
    }

    public class MockDatabaseStructureFactory implements DatabaseStructureFactory {
        private ConnectionResult<?> connectionResult;
        private ObservableList<ColumnProperty> columns;
        private ObservableList<IndexProperty> indexes;
        private ObservableList<TableProperty> tables;

        public MockDatabaseStructureFactory(
            ConnectionResult<?> connectionResult,
            ObservableList<TableProperty> tables,
            ObservableList<ColumnProperty> columns,
            ObservableList<IndexProperty> indexes
        ) {
            this.connectionResult = connectionResult;
            this.columns = columns;
            this.tables = tables;
            this.indexes = indexes;
        }

        @Override
        public DatabaseStructure create(String url, String user, String password) {
            return new MockDatabaseStructure(
                this.connectionResult,
                this.tables,
                this.columns,
                this.indexes
            );
        }
    }

    public class MockDatabaseStructure implements DatabaseStructure {
        private ConnectionResult<?> connectionResult;
        private ObservableList<ColumnProperty> columns;
        private ObservableList<IndexProperty> indexes;
        private ObservableList<TableProperty> tables;

        public MockDatabaseStructure(
            ConnectionResult<?> connectionResult,
            ObservableList<TableProperty> tables,
            ObservableList<ColumnProperty> columns,
            ObservableList<IndexProperty> indexes
        ) {
            this.connectionResult = connectionResult;
            this.columns = columns;
            this.tables = tables;
            this.indexes = indexes;
        }

        @Override
        public ObservableList<ColumnProperty> getColumns(String table) {
            return this.columns;
        }

        @Override
        public ObservableList<IndexProperty> getIndexes(String table) {
            return this.indexes;
        }

        @Override
        public ObservableList<TableProperty> getTables() {
            return this.tables;
        }

        @Override
        public ConnectionResult<?> testConnection() {
            return this.connectionResult;
        }

        @Override
        public void close() throws Exception {}
    }
}