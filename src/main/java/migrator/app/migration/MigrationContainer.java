package migrator.app.migration;

import java.util.Hashtable;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MigrationContainer {
    private Map<String, MigrationGeneratorFactory> factories;
    private ObservableList<String> names;

    public MigrationContainer() {
        this.factories = new Hashtable<>();
        this.names = FXCollections.observableArrayList();
    }

    public void addGeneratorFactory(String name, MigrationGeneratorFactory factory) {
        this.factories.put(name, factory);
        this.names.add(name);
    }

    public MigrationGeneratorFactory getGeneratorFactoryFor(String name) {
        if (!this.factories.containsKey(name)) {
            return null;
        }
        return this.factories.get(name);
    }

    public ObservableList<String> getGeneratorNames() {
        return this.names;
    }
}