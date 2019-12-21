package migrator.app.modification;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.Modification;
import migrator.lib.diff.DiffCompare;

public class DatabaseModifications<T> implements ModificationCollection<T> {
    protected ObservableList<Modification<T>> modifications;
    protected ModificationFactory<T> modificationFactory;
    protected DiffCompare<T> compare;

    public DatabaseModifications(
        ObservableList<T> dbValues,
        ModificationFactory<T> modificationFactory,
        DiffCompare<T> compare
    ) {
        this.modificationFactory = modificationFactory;
        this.compare = compare;
        this.modifications = FXCollections.observableArrayList();
        this.addOriginals(dbValues);

        dbValues.addListener((Change<? extends T> change) -> {
            while (change.next()) {
                this.removeOriginals(change.getRemoved());
                this.addOriginals(change.getAddedSubList());
            }
        });
    }

    @Override
    public void add(T item) {
        this.modifications.add(
            this.modificationFactory.createNew(item)
        );
    }

    @Override
    public ObservableList<Modification<T>> getAll() {
        return this.modifications;
    }

    @Override
    public void delete(Modification<T> modification) {
        if (modification.getChangeCommand().isType(ChangeCommand.CREATE)) {
            this.modifications.remove(modification);
        } else {
            modification.getChangeCommand().setType(ChangeCommand.DELETE);
        }
    }

    @Override
    public void restore(Modification<T> modification) {
        // TODO Auto-generated method stub
    }

    private void removeOriginals(List<? extends T> items) {
        List<Modification<T>> toBeRemoved = new LinkedList<>();
        for (T item : items) {
            Modification<T> modification = this.findByOriginal(item);
            toBeRemoved.add(modification);
        }
        this.modifications.removeAll(toBeRemoved);
    }

    private void addOriginals(List<? extends T> items) {
        List<Modification<T>> toBeAdded = new LinkedList<>();
        for (T item : items) {
            toBeAdded.add(
                this.modificationFactory.createExisting(item)
            );
        }
        this.modifications.addAll(toBeAdded);
    }

    private Modification<T> findByOriginal(T original) {
        for (Modification<T> modification : this.modifications) {
            if (!this.compare.compare(modification.getOriginal(), original)) {
                continue;
            }
            return modification;
        }
        return null;
    }
}