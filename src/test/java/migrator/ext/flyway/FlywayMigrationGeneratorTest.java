package migrator.ext.flyway;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import migrator.app.database.column.format.ApplicationColumnFormatCollection;
import migrator.app.database.column.format.SimpleAppColumnFormat;
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
    protected ApplicationColumnFormatCollection applicationColumnFormatCollection;

    @BeforeEach
    public void setUp() {
        this.applicationColumnFormatCollection = new ApplicationColumnFormatCollection();
        this.applicationColumnFormatCollection.addFormat("string", new SimpleAppColumnFormat(true, false, false, false));
        this.applicationColumnFormatCollection.addFormat("integer", new SimpleAppColumnFormat(true, false, true, true));
        this.applicationColumnFormatCollection.addFormat("float", new SimpleAppColumnFormat(true, true, true, false));

        this.storage = new FileStorage();
        this.migrator = new FlywayMigrationGenerator(
            new SqlCommandFactory(applicationColumnFormatCollection),
            new FakeFileStorageFactory(this.storage)
        );
    }

    @Test public void testPhpMigrationCreateTableWithColumn() {
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("create")
                    .withChangeName("column_name")
                    .withChangeFormat("string")
                    .withChangeLength("255")
                    .withChangeSign()
                    .build()
            )
            .build();

        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` ADD `column_name` varchar(255) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationUpdateTableRemoveColumn() {
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
        TableChange change = tableBuilder.withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withChangeFormat("string")
                    .withChangeLength("255")
                    .withOriginalName("column_name")
                    .withChangeName("new_column_name")
                    .withChangeSign()
                    .build()
            )
            .build();
        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `column_name` `new_column_name` varchar(255) NOT NULL;",
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder();
        ColumnBuilder columnBuilder2 = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder();
        ColumnBuilder columnBuilder2 = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder1 = new ColumnBuilder();
        ColumnBuilder columnBuilder2 = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
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
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("float")
                    .withChangeFormat("float")
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
            "ALTER TABLE `table_name` CHANGE `id` `id` float(11,4) NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnSigned() {
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("integer")
                    .withChangeFormat("integer")
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
            "ALTER TABLE `table_name` CHANGE `id` `id` int(11) UNSIGNED NOT NULL;",
            this.storage.load()
        );
    }

    @Test public void testPhpMigrationGenerateChangedColumnAutoIncrement() {
        TableBuilder tableBuilder = new TableBuilder();
        ColumnBuilder columnBuilder = new ColumnBuilder();
        TableChange change = tableBuilder.withChange("")
            .withOriginalName("table_name")
            .withChangeName("table_name")
            .withColumns(
                columnBuilder.withChange("update")
                    .withOriginalFormat("integer")
                    .withChangeFormat("integer")
                    .withOriginalLength("11")
                    .withChangeLength("11")
                    .withOriginalName("id")
                    .withChangeName("id")
                    .withOriginalSign()
                    .withChangeSign()
                    .withChangeAutoIncrement()
                    .build()
            )
            .build();

        this.migrator.generateMigration("", "MigrationByMigrator", change);
        assertEquals(
            "ALTER TABLE `table_name` CHANGE `id` `id` int(11) auto_increment primary key NOT NULL;",
            this.storage.load()
        );
    }
}
