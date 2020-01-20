package migrator.app.version;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.prefs.Preferences;

import migrator.app.boot.Container;
import migrator.app.service.Service;
import migrator.lib.version.Version;

public class VersionService implements Service {
    private VersionContainer versionContainer;
    private Version lastMigrationVersion;

    public VersionService(Container container) {
        Path storagePathValue = Paths.get(System.getProperty("user.home"), ".migrator");
        String defaultVersion = container.configContainer().currentVersion().toString();
        if (storagePathValue.toFile().exists()) {
            defaultVersion = "0.4.0";
        }

        String lastMigrationVersionString = Preferences.userRoot().get("lastMigrationVersion", defaultVersion);
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
        Preferences.userRoot().put("lastMigrationVersion", this.versionContainer.getLastVersion().toString());
    }

    @Override
    public void stop() {
        
    }
}