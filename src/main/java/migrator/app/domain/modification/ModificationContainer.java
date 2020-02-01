package migrator.app.domain.modification;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;

import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.change.ChangeCommandAdapter;
import migrator.lib.adapter.SimpleJsonListAdapter;
import migrator.lib.filesystem.Filesystem;
import migrator.lib.repository.UniqueRepository;
import migrator.lib.storage.AdapterStorage;
import migrator.lib.storage.SimpleFileStorage;
import migrator.lib.storage.Storage;

public class ModificationContainer {
    private UniqueRepository<ChangeCommand> repositoryValue;
    private Storage<Collection<ChangeCommand>> storageValue;

    public ModificationContainer(
        Path storagePath,
        Filesystem filesystem
    ) {
        this.repositoryValue = new UniqueRepository<>();
        this.storageValue = new AdapterStorage<>(
            new SimpleFileStorage(
                filesystem,
                new File(storagePath.toString(), "change_command.json")
            ),
            new SimpleJsonListAdapter<>(
                new ChangeCommandAdapter()
            )
        );
    }

    public UniqueRepository<ChangeCommand> repository() {
        return this.repositoryValue;
    }

    public Storage<Collection<ChangeCommand>> storage() {
        return this.storageValue;
    }
}