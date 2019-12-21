package migrator.app.modification;

import javafx.collections.ObservableList;
import migrator.app.migration.model.Modification;

public interface ModificationCollection<T> {
    public void add(T item);
    public void delete(Modification<T> modification);
    public void restore(Modification<T> modification);
    public ObservableList<Modification<T>> getAll();
}