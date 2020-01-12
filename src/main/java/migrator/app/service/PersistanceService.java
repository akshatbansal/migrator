package migrator.app.service;

import java.util.Collection;

import migrator.lib.repository.Repository;
import migrator.lib.storage.Storage;

public class PersistanceService<T> implements Service {
    private Repository<T> repository;
    private Storage<Collection<T>> storage;

    public PersistanceService(Repository<T> repository, Storage<Collection<T>> storage) {
        this.repository = repository;
        this.storage = storage;
    }

    @Override
    public void start() {
        this.repository.addAll(
            this.storage.load()
        );
    }

    @Override
    public void stop() {
        this.storage.store(
            this.repository.getAll()
        );
    }
}