package migrator.app.version;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import migrator.app.boot.Container;
import migrator.app.service.Service;
import migrator.lib.persistantsystem.Persistantsystem;
import migrator.lib.version.Version;

public class VersionService implements Service {
    private VersionContainer versionContainer;
    private Version lastMigrationVersion;
    private Persistantsystem persistantsystem;

    public VersionService(Container container) {
        this.persistantsystem = container.persistantsystem();
        Path storagePathValue = Paths.get(System.getProperty("user.home"), ".migrator");
        String defaultVersion = container.configContainer().currentVersion().toString();
        if (storagePathValue.toFile().exists()) {
            defaultVersion = "0.4.0";
        }

        String lastMigrationVersionString = persistantsystem.getString("lastMigrationVersion", defaultVersion);
         this.lastMigrationVersion = new Version(lastMigrationVersionString);
        this.versionContainer = container.versionContainer();
    }

    @Override
    public void start() {
        List<VersionMigration> migrations = this.versionContainer.getMigrationsGreaterThen(this.lastMigrationVersion);
        if (migrations.size() == 0) {
            return;
        }
        for (VersionMigration migration : migrations) {
            migration.migrate();
        }
        this.persistantsystem.putString("lastMigrationVersion", this.versionContainer.getLastVersion().toString());
    }

    @Override
    public void stop() {
        
    }
}