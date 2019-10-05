package migrator.app.migration;

import java.util.Collection;

public class Migration {
    protected MigrationConfig config;

    public Migration(MigrationConfig config) {
        this.config = config;
    }

    public MigrationGeneratorFactory getGenerator(String name) {
        return this.config.getGenerators().get(name);
    }

    public Collection<String> getGeneratorNames() {
        return this.config.getGenerators().keySet();
    }
}