package migrator.app.domain.table.service;

import org.junit.jupiter.api.Test;

import migrator.app.domain.column.ColumnRepository;
import migrator.app.domain.index.IndexRepository;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableFactory;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.uid.SessionIncrementalGenerator;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TableFactoryTest {
    protected TableFactory tableFactory;

    @BeforeEach
    public void setUp() {
        UniqueRepository<ChangeCommand> changeCommandRepo = new UniqueRepository<>();
        UniqueRepository<IndexProperty> indexPropertyRepo = new UniqueRepository<>();
        UniqueRepository<ColumnProperty> columnPropertyRepo = new UniqueRepository<>();
        this.tableFactory = new TableFactory(
            new ColumnRepository(
                columnPropertyRepo,
                changeCommandRepo
            ),
            new IndexRepository(
                indexPropertyRepo,
                changeCommandRepo
            ),
            new SessionIncrementalGenerator("test")
        );
    }

    @Test public void testCreateNotChangedHasTableNameSet() {
        Table table = this.tableFactory.createNotChanged(
            "1",
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateNotChangedIsNoneType() {
        Table table = this.tableFactory.createNotChanged(
            "1",
            "test_table"
        );
    
        assertEquals("", table.getChange().getCommand().getType());
    }

    @Test public void testCreateNotChangedHasOriginalValuesSet() {
        Table table = this.tableFactory.createNotChanged(
            "1",
            "table_name"
        );
    
        assertEquals("table_name", table.getOriginalName());
    }

    @Test public void testCreateNotChangedHasChangesEqualToOriginal() {
        Table table = this.tableFactory.createNotChanged(
            "1",
            "test_table"
        );
    
        assertEquals("test_table", table.getChange().getName());
    }

    @Test public void testCreateWithCreateChangeByNameHasNameSet() {
        Table table = this.tableFactory.createWithCreateChange(
            "1",
            "table_name"
        );
    
        assertEquals("table_name", table.getName());
    }

    @Test public void testCreateWithCreateChangeByNameIsCreateType() {
        Table table = this.tableFactory.createWithCreateChange(
            "1",
            "test_table"
        );
    
        assertEquals("create", table.getChange().getCommand().getType());
    }

    @Test public void testCreateWithCreateChangeByNameHasChangeSet() {
        Table table = this.tableFactory.createWithCreateChange(
            "1",
            "table_name"
        );
    
        assertEquals("table_name", table.getChange().getName());
    }
}