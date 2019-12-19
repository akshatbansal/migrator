package migrator.lib.adapter;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter<T, U> implements Adapter<List<T>, List<U>> {
    protected Adapter<T, U> itemAdapter;

    public ListAdapter(Adapter<T, U> itemAdapter) {
        this.itemAdapter = itemAdapter;
    }

    @Override
    public List<T> concretize(List<U> items) {
        List<T> result = new ArrayList<>();
        if (items == null) {
            return result;
        }

        for (U item : items) {
            result.add(
                this.itemAdapter.concretize(item)
            );
        }
        return result;
    }

    @Override
    public List<U> generalize(List<T> items) {
        List<U> result = new ArrayList<>();
        if (items == null) {
            return result;
        }

        for (T item : items) {
            result.add(
                this.itemAdapter.generalize(item)
            );
        }
        return result;
    }
}