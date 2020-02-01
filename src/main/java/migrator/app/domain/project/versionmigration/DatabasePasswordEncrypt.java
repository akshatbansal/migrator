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
import migrator.lib.filesystem.Filesystem;
import migrator.lib.storage.AdapterStorage;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;

public class DatabasePasswordEncrypt implements VersionMigration {
    private Storage<Collection<Project>> oldJsonStorage;
    private Storage<Collection<Project>> newJsonStorage;

    public DatabasePasswordEncrypt(Encryption encryption, Filesystem filesystem) {
        Path storagePathValue = Paths.get(System.getProperty("user.home"), ".migrator");

        this.oldJsonStorage = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePathValue.toString(), "project.json")
            ),
            new SimpleJsonListAdapter<>(
                new ProjectAdapter()
            )
        );

        this.newJsonStorage = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePathValue.toString(), "project.json")
            ),
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