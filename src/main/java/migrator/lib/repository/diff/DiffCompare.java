package migrator.lib.repository.diff;

public interface DiffCompare<T> {
    public Boolean compare(T a, T b);
}