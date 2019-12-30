package migrator.ext.phinx;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.database.column.format.SimpleAppColumnFormat;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableChange;
import migrator.app.toast.PermanentToastService;
import migrator.ext.phinx.PhinxMigrationGenerator;
import migrator.ext.phinx.mock.FileStorage;
import migrator.ext.phinx.mock.FakeFileStorageFactory;
import migrator.ext.php.PhpCommandFactory;

public class PhinxMigrationGeneratorTest {
    protected PhinxMigrationGenerator migrator;
    protected FileStorage storage;
    protected ApplicationColumnFormatCollection appColumnFormatCollection;

    @BeforeEach
    public void setUp() {
        this.appColumnFormatCollection = new ApplicationColumnFormatCollection();
        this.appColumnFormatCollection.addFormat("string", new SimpleAppColumnFormat(true, false, false, false));
        this.appColumnFormatCollection.addFormat("column_format", new SimpleAppColumnFormat(false, false, false, false));
        this.appColumnFormatCollection.addFormat("integer", new SimpleAppColumnFormat(true, false, true, true));
        this.appColumnFormatCollection.addFormat("decimal", new SimpleAppColumnFormat(true, true, true, false));
        this.storage = new FileStorage();
        this.migrator = new PhinxMigrationGenerator(
            new FakeFileStorageFactory(this.storage),
            new PhpCommandFactory(this.appColumnFormatCollection),
            new PermanentToastService()
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumn() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", "create"),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "string", null, false, "255", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "string", null, false, "255", false, "", false),
                        new ChangeCommand("2", "create")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name', ['id' => false])\n" +
                        "\t\t\t->addColumn('column_name', 'string', ['null' => false, 'length' => 255])\n" +
                        "\t\t\t->create();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationRenameTable() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "new_table_name"),
            new ChangeCommand("1", "update"),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->rename('new_table_name')\n" +
                        "\t\t\t->update();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableAddColumn() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "column_format", null, false, "255", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "column_format", null, false, "255", false, "", false),
                        new ChangeCommand("2", "create")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->addColumn('column_name', 'column_format', ['null' => false])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRemoveColumn() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "column_format", null, false, "255", false, "", false),
                        new SimpleColumnProperty("1", "column_name", "column_format", null, false, "255", false, "", false),
                        new ChangeCommand("2", "delete")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" + 
                        "\t\t\t->removeColumn('column_name')\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRenameColumn() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "column_format", "", false, "255", false, "", false),
                        new SimpleColumnProperty("2", "new_column_name", "column_format", "", false, "255", false, "", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->renameColumn('column_name', 'new_column_name')\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationDeleteTable() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", "delete"),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')->drop()->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumnAndIndex() {
        ColumnProperty columnChange1 = new SimpleColumnProperty("2", "id", "integer", null, false, "11", true, "", false);
        ColumnProperty columnChange2 = new SimpleColumnProperty("4", "name", "string", null, false, "255", false, "", false);
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", "create"),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "id", "integer", null, false, "11", true, "", false),
                        columnChange1,
                        new ChangeCommand("2", "create")
                    ),
                    new Column(
                        "2",
                        "1",
                        new SimpleColumnProperty("3", "name", "string", null, false, "255", false, "", false),
                        columnChange2,
                        new ChangeCommand("3", "create")
                    )
                )
            ),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Index(
                        "1",
                        "1",
                        new SimpleIndexProperty("1", "id_name", Arrays.asList()),
                        new SimpleIndexProperty("2", "id_name", Arrays.asList(columnChange1, columnChange2)),
                        new ChangeCommand("4", "create")
                    )
                )
            )
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name', ['id' => false])\n" +
                        "\t\t\t->addColumn('id', 'integer', ['null' => false, 'length' => 11, 'signed' => true])\n" +
                        "\t\t\t->addColumn('name', 'string', ['null' => false, 'length' => 255])\n" +
                        "\t\t\t->addIndex(['id', 'name'], ['name' => 'id_name'])\n" +
                        "\t\t\t->create();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationRemoveIndex() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Index(
                        "1",
                        "1",
                        new SimpleIndexProperty("1", "id_name", Arrays.asList()),
                        new SimpleIndexProperty("2", "id_name", Arrays.asList()),
                        new ChangeCommand("2", "delete")
                    )
                )
            )
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->removeIndexByName('id_name')\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationNoChangeRetunrnsEmptyCommand() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertNull(this.storage.load());
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormat() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "column_format", "", false, "255", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "string", "", false, "255", false, "", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'string', ['null' => false, 'length' => 255])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnDefaultValue() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "string", "", false, "255", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "string", "default_value", false, "255", false, "", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'string', ['null' => false, 'default' => 'default_value', 'length' => 255])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormatLength() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "string", "", false, "255", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "string", "", false, "250", false, "", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'string', ['null' => false, 'length' => 250])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormatPrecision() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "decimal", "", false, "10", false, "4", false),
                        new SimpleColumnProperty("2", "column_name", "decimal", "", false, "10", false, "5", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'decimal', ['null' => false, 'precision' => 10, 'scale' => 5, 'signed' => false])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnSigned() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "integer", "", false, "10", true, "", false),
                        new SimpleColumnProperty("2", "column_name", "integer", "", false, "10", false, "", false),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'integer', ['null' => false, 'length' => 10, 'signed' => false])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnAutoIncrement() {
        TableChange change = new Table(
            "id-1",
            "1",
            new SimpleTableProperty("1", "table_name"),
            new SimpleTableProperty("2", "table_name"),
            new ChangeCommand("1", ""),
            FXCollections.observableArrayList(
                Arrays.asList(
                    new Column(
                        "1",
                        "1",
                        new SimpleColumnProperty("1", "column_name", "integer", "", false, "10", false, "", false),
                        new SimpleColumnProperty("2", "column_name", "integer", "", false, "10", false, "", true),
                        new ChangeCommand("2", "update")
                    )
                )
            ),
            FXCollections.observableArrayList()
        );
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "<?php\n\n" +
            "use Phinx\\Migration\\AbstractMigration;\n\n" +
            "class MigrationByMigrator extends AbstractMigration\n" +
            "{\n" +
                "\tpublic function change()\n" +
                "\t{\n" +
                    "\t\t$this->table('table_name')\n" +
                        "\t\t\t->changeColumn('column_name', 'integer', ['null' => false, 'length' => 10, 'signed' => false, 'identity' => true])\n" +
                        "\t\t\t->save();\n" +
                "\t}\n" +
            "}\n",
            this.storage.load()
        );
    }
}
