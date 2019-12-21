package migrator.app.modification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.migration.model.ColumnProperty;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleColumnProperty;
import migrator.app.modification.column.ColumnModificationFactory;
import migrator.app.modification.column.ColumnPropertyNameCompare;

public class ColumnDatabaseModificationTest {
    private ModificationCollection<ColumnProperty> createCollection(ObservableList<ColumnProperty> values) {
        return new DatabaseModifications<>(
            values,
            new ColumnModificationFactory(),
            new ColumnPropertyNameCompare()
        );
    }

    @Test public void add_columnProperty_addsItemToList() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false));
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals(1, result.size());
    }

    @Test public void add_columnProperty_addsItemWithOriginalNull() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false));
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertNull(result.get(0).getOriginal());
    }

    @Test public void add_columnProperty_addsItemWithModificationSetToAddedProperty() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        ColumnProperty column = new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false);
        modifications.add(column);
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals(column, result.get(0).getModification());
    }

    @Test public void add_columnProperty_addsItemWithChangeCommandCreate() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false));
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals("create", result.get(0).getChangeCommand().typeProperty().get());
    }

    @Test public void delete_createdModification_removesFromList() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(
            new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false)
        );
        modifications.delete(modifications.getAll().get(0));
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals(0, result.size());
    }

    @Test public void delete_unchangedModification_makrsAsChangeDelete() {
        ModificationCollection<ColumnProperty> modifications = this.createCollection(FXCollections.observableArrayList(
            new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false)
        ));

        modifications.delete(modifications.getAll().get(0));
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals("delete", result.get(0).getChangeCommand().getType());
    }

    @Test public void removeFromDb_removesColumnFromModifications() {
        ObservableList<ColumnProperty> dbColumns = FXCollections.observableArrayList(
            new SimpleColumnProperty("", "column_name", "", "", false, "", false, "", false)
        );
        ModificationCollection<ColumnProperty> modifications = this.createCollection(dbColumns);

        dbColumns.remove(0);
        List<Modification<ColumnProperty>> result = modifications.getAll();

        assertEquals(0, result.size());
    }
}