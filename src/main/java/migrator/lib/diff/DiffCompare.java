package migrator.lib.diff;

public interface DiffCompare<T> {
    public Boolean compare(T a, T b);
}