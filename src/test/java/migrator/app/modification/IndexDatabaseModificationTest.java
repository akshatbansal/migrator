package migrator.app.modification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.migration.model.IndexProperty;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.SimpleIndexProperty;
import migrator.app.modification.index.IndexModificationFactory;
import migrator.app.modification.index.IndexPropertyNameCompare;

public class IndexDatabaseModificationTest {
    private ModificationCollection<IndexProperty> createCollection(ObservableList<IndexProperty> values) {
        return new DatabaseModifications<>(
            values,
            new IndexModificationFactory(),
            new IndexPropertyNameCompare()
        );
    }

    @Test public void add_indexProperty_addsItemToList() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleIndexProperty("", "index_name", new ArrayList<>()));
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals(1, result.size());
    }

    @Test public void add_indexProperty_addsItemWithOriginalNull() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleIndexProperty("", "index_name", new ArrayList<>()));
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertNull(result.get(0).getOriginal());
    }

    @Test public void add_indexProperty_addsItemWithModificationSetToAddedProperty() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        IndexProperty index = new SimpleIndexProperty("", "index_name", new ArrayList<>());
        modifications.add(index);
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals(index, result.get(0).getModification());
    }

    @Test public void add_indexProperty_addsItemWithChangeCommandCreate() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(new SimpleIndexProperty("", "index_name", new ArrayList<>()));
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals("create", result.get(0).getChangeCommand().typeProperty().get());
    }

    @Test public void delete_createdModification_removesFromList() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList());

        modifications.add(
            new SimpleIndexProperty("", "index_name", new ArrayList<>())
        );
        modifications.delete(modifications.getAll().get(0));
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals(0, result.size());
    }

    @Test public void delete_unchangedModification_makrsAsChangeDelete() {
        ModificationCollection<IndexProperty> modifications = this.createCollection(FXCollections.observableArrayList(
            new SimpleIndexProperty("", "index_name", new ArrayList<>())
        ));

        modifications.delete(modifications.getAll().get(0));
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals("delete", result.get(0).getChangeCommand().getType());
    }

    @Test public void removeFromDb_removesIndexFromModifications() {
        ObservableList<IndexProperty> dbIndexes = FXCollections.observableArrayList(
            new SimpleIndexProperty("", "index_name", new ArrayList<>())
        );
        ModificationCollection<IndexProperty> modifications = this.createCollection(dbIndexes);

        dbIndexes.remove(0);
        List<Modification<IndexProperty>> result = modifications.getAll();

        assertEquals(0, result.size());
    }
}