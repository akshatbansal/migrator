package migrator.lib.adapter;

public interface Adapter<T, U> {
    public T concretize(U item);
    public U generalize(T item);
}