package migrator.app.domain.project.versionmigration;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import migrator.app.domain.project.model.EncryptedProjectAdapter;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.project.model.ProjectAdapter;
import migrator.app.version.VersionMigration;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.encryption.Encryption;
import migrator.lib.storage.Storage;
import migrator.lib.storage.Storages;

public class DatabasePasswordEncrypt implements VersionMigration {
    private Storage<Collection<Project>> oldJsonStorage;
    private Storage<Collection<Project>> newJsonStorage;

    public DatabasePasswordEncrypt(Encryption encryption) {
        Path storagePathValue = Paths.get(System.getProperty("user.home"), ".migrator");

        this.oldJsonStorage = Storages.getFileStorage(
            new File(storagePathValue.toString(), "project.json"),
            new SimpleJsonListAdapter<>(
                new ProjectAdapter()
            )
        );

        this.newJsonStorage = Storages.getFileStorage(
            new File(storagePathValue.toString(), "project.json"),
            new SimpleJsonListAdapter<>(
                new EncryptedProjectAdapter(encryption)
            )
        );
    }

    @Override
    public void migrate() {     
        this.newJsonStorage.store(
            this.oldJsonStorage.load()
        );
    }
}