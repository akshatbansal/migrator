package migrator.phpphinx;

import org.junit.jupiter.api.Test;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.migration.ChangeCommand;
import migrator.migration.ColumnChange;
import migrator.migration.IndexChange;
import migrator.migration.TableChange;
import migrator.phpphinx.PhinxMigration;
import migrator.phpphinx.mock.FileStorage;

public class PhinxMigrationTest {
    protected PhinxMigration migrator;
    protected FileStorage storage;

    protected Map<String, Observable> createArguments(Object[] ... argsPair) {
        Map<String, Observable> args = new Hashtable<>();
        for (Object[] pair : argsPair) {
            args.put((String) pair[0], new SimpleObjectProperty(pair[1]));
        }
        return args;
    }

    @BeforeEach
    public void setUp() {
        this.storage = new FileStorage();
        this.migrator = new PhinxMigration(this.storage, new PhpCommandFactory());
    }

    @Test public void testPhpMigrationCreateTableWithColumn() {
        Map<String, Observable> args = this.createArguments(
            new Object[]{"name", "name"},
            new Object[]{"format", "string"}
        );

        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("create"),
            Arrays.asList(new ColumnChange("name", new ChangeCommand("create", args)))
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->addColumn('name', 'string')\n" +
                "\t->save();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRenameTable() {
        Map<String, Observable> args = this.createArguments(
            new Object[]{"name", "new_table_name"}
        );

        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("update", args)
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->renameTable('new_table_name')\n" +
                "\t->update();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableAddColumn() {
        Map<String, Observable> args = this.createArguments(
            new Object[]{"name", "column_name"},
            new Object[]{"format", "column_format"}
        );

        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("update"),
            Arrays.asList(new ColumnChange("column_name", new ChangeCommand("create", args)))
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->addColumn('column_name', 'column_format')\n" +
                "\t->update();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRemoveColumn() {
        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("update"),
            Arrays.asList(new ColumnChange("column_name", new ChangeCommand("delete")))
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" + 
                "\t->removeColumn('column_name')\n" +
                "\t->update();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRenameColumn() {
        Map<String, Observable> args = this.createArguments(
            new Object[]{"name", "new_column_name"}
        );

        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("update"),
            Arrays.asList(new ColumnChange("column_name", new ChangeCommand("update", args)))
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->renameColumn('column_name', 'new_column_name')\n" +
                "\t->update();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationDeleteTable() {
        TableChange change = new TableChange("table_name", new ChangeCommand("delete"));
        this.migrator.create(change);
        assertEquals(
            "$this->dropTable('table_name');\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumnAndIndex() {
        List<Map<String, Observable>> args = Arrays.asList(
            this.createArguments(
                new Object[]{"name", "id"},
                new Object[]{"format", "integer"},
                new Object[]{"unsigned", true},
                new Object[]{"auto_increment", true}
            ),
            this.createArguments(
                new Object[]{"name", "name"},
                new Object[]{"format", "string"}
            ),
            this.createArguments(
                new Object[]{"columns", Arrays.asList("id", "name")}
            )
        );

        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("create"),
            Arrays.asList(
                new ColumnChange("id", new ChangeCommand("create", args.get(0))),
                new ColumnChange("name", new ChangeCommand("create", args.get(1)))
            ),
            Arrays.asList(
                new IndexChange("id_name", new ChangeCommand("create", args.get(2)))
            )
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->addColumn('id', 'integer')\n" +
                "\t->addColumn('name', 'string')\n" +
                "\t->addIndex(['id', 'name'], ['name' => 'id_name'])\n" +
                "\t->save();\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumnRemoveIndex() {
        TableChange change = new TableChange(
            "table_name", 
            new ChangeCommand("create"),
            new ArrayList(),
            Arrays.asList(
                new IndexChange("id_name", new ChangeCommand("delete"))
            )
        );
        this.migrator.create(change);
        assertEquals(
            "$this->table('table_name')\n" +
                "\t->removeIndexByName('id_name')\n" +
                "\t->save();\n",
            this.storage.load()
        );
    }
}
