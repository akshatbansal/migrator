package migrator.lib.adapter;

import java.util.Collection;
import java.util.LinkedList;

public class CollectionAdapter<T, U> implements Adapter<Collection<T>, Collection<U>> {
    private ListAdapter<T, U> listAdapter;

    public CollectionAdapter(Adapter<T, U> itemAdapter) {
        this.listAdapter = new ListAdapter<>(itemAdapter);
    }

    @Override
    public Collection<T> concretize(Collection<U> items) {
        return this.listAdapter.concretize(
            new LinkedList<>(items)
        );
    }

    @Override
    public Collection<U> generalize(Collection<T> items) {
        return this.listAdapter.generalize(
            new LinkedList<>(items)
        );
    }
}