package migrator.app.domain.table.service;

import org.junit.jupiter.api.Test;

import migrator.app.domain.column.service.ColumnRepository;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.index.service.IndexRepository;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TableFactoryTest {
    protected TableFactory tableFactory;

    @BeforeEach
    public void setUp() {
        this.tableFactory = new TableFactory(
            new ColumnRepository(),
            new IndexRepository()
        );
    }

    @Test public void testCreateNotChangedHasTableNameSet() {
        Table table = this.tableFactory.createNotChanged(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Table table = this.tableFactory.createNotChanged(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "test_table"
        );
    
        assertNull(table.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasOriginalValuesSet() {
        Table table = this.tableFactory.createNotChanged(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getOriginalName());
    }

    @Test public void testCreateNotChangedHasChangesEqualToOriginal() {
        Table table = this.tableFactory.createNotChanged(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "test_table"
        );
    
        assertEquals("test_table", table.getChange().getName());
    }

    @Test public void testCreateWithCreateChangeByNameHasNameSet() {
        Table table = this.tableFactory.createWithCreateChange(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Table table = this.tableFactory.createWithCreateChange(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "test_table"
        );
    
        assertEquals("create", table.getChange().getCommand().getType());
    }

    @Test public void testCreateWithCreateChangeByNameHasChangeSet() {
        Table table = this.tableFactory.createWithCreateChange(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "project_name",
                "phinx",
                ""
            ),
            "table_name"
        );
    
        assertEquals("table_name", table.getChange().getName());
    }
}