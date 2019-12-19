package migrator.ext.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import migrator.app.database.DatabaseColumnDriver;
import migrator.app.database.DatabaseIndexDriver;
import migrator.app.database.DatabaseTableDriver;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.TableProperty;

public class MysqlDatabaseStructureTest {
    private MysqlDatabaseStructure createStructure(List<String> tables, List<Map<String, String>> columns, List<List<String>> indexes) {
        return new MysqlDatabaseStructure(
            new MockTableDriver(tables),
            new MockColumnDriver(columns),
            new MockIndexDriver(indexes)
        );
    }

    @Test public void getTables_secondCall_sameReference() {
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(null, null, null);

        assertEquals(mysqlDatabaseStructure.getTables(), mysqlDatabaseStructure.getTables());
    }

    @Test public void getTables_oneTableInDatabase_containsOneTable() {
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(Arrays.asList("table_name"), null, null);

        List<TableProperty> result = mysqlDatabaseStructure.getTables();

        assertEquals(1, result.size());
    }

    @Test public void getTables_tableCountChange_containsTwoTables() {
        MockTableDriver tableDriver = new MockTableDriver(Arrays.asList("table_name"));
        MysqlDatabaseStructure mysqlDatabaseStructure = new MysqlDatabaseStructure(tableDriver, null, null);

        tableDriver.set(Arrays.asList("table_name", "properties"));
        List<TableProperty> result = mysqlDatabaseStructure.getTables();

        assertEquals(2, result.size());
    }

    @Test public void getColumns_secondCallForTheSameTable_sameReference() {
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(null, null, null);

        assertEquals(mysqlDatabaseStructure.getColumns("table_name"), mysqlDatabaseStructure.getColumns("table_name"));
    }

    @Test public void getColumns_oneColumnInDatabase_containsOneTable() {
        Map<String, String> column = new Hashtable<>();
        column.put("name", "column_name");
        column.put("format", "varchar(10)");
        column.put("nullEnabled", "");
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(null, Arrays.asList(column), null);

        List<ColumnProperty> result = mysqlDatabaseStructure.getColumns("table_name");

        assertEquals(1, result.size());
    }

    @Test public void getIndexes_secondCallForTheSameTable_sameReference() {
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(null, null, null);

        assertEquals(mysqlDatabaseStructure.getIndexes("table_name"), mysqlDatabaseStructure.getIndexes("table_name"));
    }

    @Test public void getIndexes_oneIndexInDatabase_containsOneIndex() {
        List<String> index = Arrays.asList(
            "index_name",
            "id"
        );
        MysqlDatabaseStructure mysqlDatabaseStructure = this.createStructure(null, null, Arrays.asList(index));

        List<IndexProperty> result = mysqlDatabaseStructure.getIndexes("table_name");

        assertEquals(1, result.size());
    }

    @Test public void getIndexes() {

    }

    public class MockTableDriver implements DatabaseTableDriver {
        private List<String> dbData;

        public MockTableDriver(List<String> dbData) {
            this.dbData = dbData;
        }

        @Override
        public List<String> getTables() {
            return this.dbData;
        }

        public void set(List<String> dbData) {
            this.dbData = dbData;
        }
    }

    public class MockColumnDriver implements DatabaseColumnDriver {
        public List<Map<String, String>> dbData;

        public MockColumnDriver(List<Map<String, String>> dbData) {
            this.dbData = dbData;
        }

        @Override
        public List<Map<String, String>> getColumns(String tableName) {
            return this.dbData;
        }

        public void setData(List<Map<String, String>> dbData) {
            this.dbData = dbData;
        }
    }

    public class MockIndexDriver implements DatabaseIndexDriver {
        protected List<List<String>> dbData;

        public MockIndexDriver(List<List<String>> dbData) {
            this.dbData = dbData;
        }

        @Override
        public List<List<String>> getIndexes(String tableName) {
            return this.dbData;
        }
    }
}