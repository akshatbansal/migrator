package migrator.ext.flyway;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.database.ColumnFormatBuilder;
import migrator.app.database.format.ColumnFormatManager;
import migrator.app.database.format.FakeColumnFormatManager;
import migrator.app.database.format.SimpleColumnFormat;
import migrator.app.domain.column.service.ColumnBuilder;
import migrator.app.domain.index.service.IndexBuilder;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.service.TableBuilder;
import migrator.app.migration.model.TableChange;
import migrator.ext.phinx.mock.FakeFileStorageFactory;
import migrator.ext.phinx.mock.FileStorage;
import migrator.ext.sql.SqlCommandFactory;

public class FlywayMigrationGeneratorTest {
    protected FlywayMigrationGenerator migrator;
    protected FileStorage storage;
    protected ColumnFormatManager columnFormatManager;

    @BeforeEach
    public void setUp() {
        this.columnFormatManager = new FakeColumnFormatManager(
            new SimpleColumnFormat("test")
        );
        this.storage = new FileStorage();
        this.migrator = new FlywayMigrationGenerator(
            new SqlCommandFactory(),
            new FakeFileStorageFactory(this.storage)
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumn() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("create")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withTableId("1")
                    .withChange("create")
                    .withChangeName("column_name")
                    .withChangeFormat("string")
                    .withChangeLength("255")
                    .withChangeSign()
                    .build()
            )
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "CREATE TABLE IF NOT EXISTS `table_name` (`column_name` varchar(255) NOT NULL);",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationRenameTable() {
        TableBuilder tableBuilder = new TableBuilder();
        TableChange change = tableBuilder.withChange("update")
            .withOriginalName("table_name")
            .withChangeName("new_table_name")
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` RENAME TO `new_table_name`;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableAddColumn() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("create")
                    .withChangeName("column_name")
                    .withChangeFormat("column_format")
                    .withChangeLength("255")
                    .withChangeSign()
                    .build()
            )
            .build();

        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` ADD `column_name` column_format(255) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRemoveColumn() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("delete")
                    .withChangeName("column_name")
                    .withChangeFormat("column_format")
                    .withChangeLength("255")
                    .build()
            )
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` DROP COLUMN `column_name`;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRenameColumn() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.build()
            )
        );
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withChangeFormat("column_format")
                    .withOriginalName("column_name")
                    .withChangeName("new_column_name")
                    .withChangeSign()
                    .build()
            )
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `column_name` `new_column_name` column_format NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationDeleteTable() {
        TableBuilder tableBuilder = new TableBuilder();
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChange("delete")
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "DROP TABLE `table_name`;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumnAndIndex() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        ColumnBuilder columnBuilder2 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        IndexBuilder indexBuilder = new IndexBuilder();
        Column column1 = columnBuilder1.withChange("create")
            .withChangeFormat("integer")
            .withChangeLength("11")
            .withChangeName("id")
            .withChangeSign()
            .build();
        Column column2 = columnBuilder2.withChange("create")
            .withChangeFormat("string")
            .withChangeLength("255")
            .withChangeName("name")
            .withChangeSign()
            .withId("2")
            .build();
        TableChange change = tableBuilder.withChange("create")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(column1, column2)
            .withIndexes(
                indexBuilder.withChange("create")
                    .withChangeName("id_name")
                    .withChangeColumns(
                        Arrays.asList(column1.getChange(), column2.getChange())
                    )
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "CREATE TABLE IF NOT EXISTS `table_name` (`id` int(11) NOT NULL, `name` varchar(255) NOT NULL);CREATE INDEX `id_name` ON `table_name`(`id`, `name`);",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationAndIndex() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        ColumnBuilder columnBuilder2 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        IndexBuilder indexBuilder = new IndexBuilder();
        Column column1 = columnBuilder1.withChange("")
            .withChangeFormat("integer")
            .withChangeLength("11")
            .withChangeName("id")
            .withChangeSign()
            .build();
        Column column2 = columnBuilder2.withChange("")
            .withChangeFormat("string")
            .withChangeLength("255")
            .withChangeName("name")
            .withChangeSign()
            .withId("2")
            .build();
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(column1, column2)
            .withIndexes(
                indexBuilder.withChange("create")
                    .withChangeName("id_name")
                    .withChangeColumns(
                        Arrays.asList(column1.getChange(), column2.getChange())
                    )
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "CREATE INDEX `id_name` ON `table_name`(`id`, `name`);",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationRemoveIndex() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        ColumnBuilder columnBuilder2 = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        IndexBuilder indexBuilder = new IndexBuilder();
        Column column1 = columnBuilder1.withChange("")
            .withChangeFormat("integer")
            .withChangeLength("11")
            .withChangeName("id")
            .build();
        Column column2 = columnBuilder2.withChange("")
            .withChangeFormat("string")
            .withChangeLength("255")
            .withChangeName("name")
            .withId("2")
            .build();
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(column1, column2)
            .withIndexes(
                indexBuilder.withChange("delete")
                    .withChangeName("id_name")
                    .withChangeColumns(
                        Arrays.asList(column1.getChange(), column2.getChange())
                    )
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` DROP INDEX `id_name`;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationNoChangeRetunrnsEmptyCommand() {
        TableBuilder tableBuilder = new TableBuilder();
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertNull(this.storage.load());
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormat() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("integer")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withChangeSign()
                    .build()
            )
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` int(11) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnDefaultValue() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("string")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withOriginalDefaultValue("")
                    .withChangeDefaultValue("def_val")
                    .withChangeSign()
                    .build()
            )
            .build();

        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` ALTER `id` SET DEFAULT 'def_val';",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormatLength() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("string")
                    .withOriginalLength("11")
                    .withChangeLength("12")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withChangeSign()
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` varchar(12) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnFormatPrecision() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .withPrecision()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("string")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withChangePrecision("2")
                    .withChangePrecision("4")
                    .withChangeSign()
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` varchar(11, 4) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnSigned() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .withSign()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("string")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withOriginalSign()
                    .build()
            )
            .build();
        
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` varchar(11) UNSIGNED NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnAutoIncrement() {
        ColumnFormatBuilder columnFormatBuilder = new ColumnFormatBuilder();
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder(
            new FakeColumnFormatManager(
                columnFormatBuilder.withLength()
                    .withAutoIncrement()
                    .build()
            )
        );
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("string")
                    .withChangeFormat("string")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withChangeAutoIncrement()
                    .build()
            )
            .build();

        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` varchar(11) auto_increment primary key NOT NULL;",
            this.storage.load()
        );
    }
}
