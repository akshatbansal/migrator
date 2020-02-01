package migrator.app.domain.table.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import migrator.app.boot.Container;
import migrator.app.domain.ApplicationTest;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.lib.dispatcher.SimpleEvent;

public class TableNewIntegrationTest {
    private ApplicationTest appTest;
    private Container container;

    @BeforeEach
    public void setUp() {
        this.appTest = new ApplicationTest();
        this.container = this.appTest.getContainer();
    }

    private Project createProject() {
        return new Project(
            new DatabaseConnection(
                new Connection("name", "user", "", "localhost", "3303", "mysql"), "database"
            ),
            "id",
            "name",
            "dbStructure",
            "folder"
        );
    }

    @Test
    public void tableNewEvent_projectSelected_addsTableToStoreList() {
        this.appTest.getAppService().start();
        this.container.projectStore()
            .open(this.createProject());

        this.container.dispatcher()
            .dispatch(
                new SimpleEvent<>("table.new")
            );

        assertEquals(1, this.container.tableContainer().tableStore().getList().size());
    }
}