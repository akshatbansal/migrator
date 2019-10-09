package migrator.ext.phinx;

import org.junit.jupiter.api.Test;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableChange;
import migrator.ext.phinx.PhinxMigrationGenerator;
import migrator.ext.phinx.mock.FileStorage;
import migrator.ext.php.PhpCommandFactory;

public class PhinxMigrationGeneratorTest {
    protected PhinxMigrationGenerator migrator;
    protected FileStorage storage;

    @BeforeEach
    public void setUp() {
        this.storage = new FileStorage();
        this.migrator = new PhinxMigrationGenerator(this.storage, new PhpCommandFactory());
    }

    @Test public void testPhpMigrationCreateTableWithColumn() {
        TableChange change = new Table(
            new Project(
                new DatabaseConnection(
                    new Connection("localhost"),
                    "test_db"
                ),
                "test_project",
                "phinx",
                ""
            ),
            new SimpleTableProperty("table_name"),
            new SimpleTableProperty("table_name"),
            new ChangeCommand("create"),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        new SimpleColumnProperty("column_name", "string", null, false),
                        new SimpleColumnProperty("column_name", "string", null, false),
                        new ChangeCommand("create")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->addColumn('column_name', 'string', ['null' => false])\n" +
                        "\t\t\t->create();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    // @Test public void testPhpMigrationUpdateTableRenameTable() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty("new_table_name"),
    //         new ChangeCommand("update")
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->renameTable('new_table_name')\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationUpdateTableAddColumn() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name", 
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("update"),
    //         Arrays.asList(
    //             new SimpleColumnChange(
    //                 "column_name",
    //                 new SimpleColumnProperty("column_name", "column_format", null, false),
    //                 new ChangeCommand("create")
    //             )
    //         ),
    //         new ArrayList<>()
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->addColumn('column_name', 'column_format', ['null' => false])\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationUpdateTableRemoveColumn() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("update"),
    //         Arrays.asList(
    //             new SimpleColumnChange(
    //                 "column_name",
    //                 new SimpleColumnProperty("column_name", null, null, false),
    //                 new ChangeCommand("delete")
    //             )
    //         ),
    //         new ArrayList<>()
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" + 
    //                     "\t\t\t->removeColumn('column_name')\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationUpdateTableRenameColumn() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name", 
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("update"),
    //         Arrays.asList(
    //             new SimpleColumnChange(
    //                 "column_name",
    //                 new SimpleColumnProperty("new_column_name", null, null, false),
    //                 new ChangeCommand("update")
    //             )
    //         ),
    //         new ArrayList<>()
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->renameColumn('column_name', 'new_column_name')\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationDeleteTable() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("delete")
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->dropTable('table_name');\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationCreateTableWithColumnAndIndex() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("create"),
    //         Arrays.asList(
    //             new SimpleColumnChange(
    //                 "id",
    //                 new SimpleColumnProperty("id", "integer", null, false),
    //                 new ChangeCommand("create")
    //             ),
    //             new SimpleColumnChange(
    //                 "name",
    //                 new SimpleColumnProperty("name", "string", null, false),
    //                 new ChangeCommand("create")
    //             )
    //         ),
    //         Arrays.asList(
    //             new SimpleIndexChange(
    //                 new SimpleIndexProperty("id_name", Arrays.asList(new SimpleStringProperty("id"), new SimpleStringProperty("name"))),
    //                 new ChangeCommand("create")
    //             )
    //         )
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->addColumn('id', 'integer', ['null' => false])\n" +
    //                     "\t\t\t->addColumn('name', 'string', ['null' => false])\n" +
    //                     "\t\t\t->addIndex(['id', 'name'], ['name' => 'id_name'])\n" +
    //                     "\t\t\t->create();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // @Test public void testPhpMigrationCreateTableWithColumnRemoveIndex() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand("update"),
    //         new ArrayList(),
    //         Arrays.asList(
    //             new SimpleIndexChange(
    //                 new SimpleIndexProperty("id_name", new ArrayList<>()),
    //                 new ChangeCommand("delete")
    //             )
    //         )
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->removeIndexByName('id_name')\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }

    // public void testPhpMigrationCreateTablaWithoutChangeRetunrnsEmptyCommand() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand(null)
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals("",
    //         this.storage.load()
    //     );
    // }

    // public void testPhpMigrationGenerateChangedColumnFormat() {
    //     TableChange change = new SimpleTableChange(
    //         "table_name",
    //         new SimpleTableProperty(null),
    //         new ChangeCommand(null),
    //         Arrays.asList(
    //             new SimpleColumnChange(
    //                 new SimpleColumnProperty("name", "integer", null, false),
    //                 new SimpleColumnProperty(null, "string", null, null),
    //                 new ChangeCommand("update")
    //             )
    //         ),
    //         new ArrayList<>()
    //     );
    //     this.migrator.generateMigration("MigrationByMigrator", change);
    //     assertEquals(
    //         "<?php\n\n" +
    //         "use Phinx\\Migration\\AbstractMigration;\n\n" +
    //         "class MigrationByMigrator extends AbstractMigration\n" +
    //         "{\n" +
    //             "\tpublic function change()\n" +
    //             "\t{\n" +
    //                 "\t\t$this->table('table_name')\n" +
    //                     "\t\t\t->changeColumn('id_name')\n" +
    //                     "\t\t\t->update();\n" +
    //             "\t}\n" +
    //         "}\n",
    //         this.storage.load()
    //     );
    // }
}
