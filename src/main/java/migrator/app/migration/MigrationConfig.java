package migrator.app.migration;

import java.util.HashMap;
import java.util.Map;

public class MigrationConfig {
    protected Map<String, MigrationGeneratorFactory> generators;

    public MigrationConfig() {
        this.generators = new HashMap<>();
    }

    public void addGeneraotrFactory(String name, MigrationGeneratorFactory factory) {
        this.generators.put(name, factory);
    }

    public Map<String, MigrationGeneratorFactory> getGenerators() {
        return this.generators;
    }
}