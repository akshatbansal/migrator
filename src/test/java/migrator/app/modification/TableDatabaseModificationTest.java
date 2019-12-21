package migrator.app.modification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleTableProperty;
import migrator.app.migration.model.TableProperty;
import migrator.app.modification.table.TableModificationFactory;
import migrator.app.modification.table.TablePropertyNameCompare;

public class TableDatabaseModificationTest {
    private ModificationCollection<TableProperty> createCollection(ObservableList<TableProperty> values) {
        return new DatabaseModifications<>(
            values,
            new TableModificationFactory(),
            new TablePropertyNameCompare()
        );
    }

    @Test public void add_tableProperty_addsItemToList() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList());

        tableModifications.add(new SimpleTableProperty("", "table_name"));
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals(1, result.size());
    }

    @Test public void add_tableProperty_addsItemWithOriginalNull() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList());

        tableModifications.add(new SimpleTableProperty("", "table_name"));
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertNull(result.get(0).getOriginal());
    }

    @Test public void add_tableProperty_addsItemWithModificationSetToAddedTable() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList());

        TableProperty table = new SimpleTableProperty("", "table_name");
        tableModifications.add(table);
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals(table, result.get(0).getModification());
    }

    @Test public void add_tableProperty_addsItemWithChangeCommandCreate() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList());

        tableModifications.add(new SimpleTableProperty("", "table_name"));
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals("create", result.get(0).getChangeCommand().typeProperty().get());
    }

    @Test public void delete_createdModification_removesFromList() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList());

        tableModifications.add(new SimpleTableProperty("", "table_name"));
        tableModifications.delete(tableModifications.getAll().get(0));
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals(0, result.size());
    }

    @Test public void delete_unchangedModification_makrsAsChangeDelete() {
        ModificationCollection<TableProperty> tableModifications = this.createCollection(FXCollections.observableArrayList(
            new SimpleTableProperty("", "table_name")
        ));

        tableModifications.delete(tableModifications.getAll().get(0));
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals("delete", result.get(0).getChangeCommand().getType());
    }

    @Test public void removeFromDb_removesTableFromModifications() {
        ObservableList<TableProperty> dbTables = FXCollections.observableArrayList(
            new SimpleTableProperty("", "table_name")
        );
        ModificationCollection<TableProperty> tableModifications = this.createCollection(dbTables);

        dbTables.remove(0);
        List<Modification<TableProperty>> result = tableModifications.getAll();

        assertEquals(0, result.size());
    }
}