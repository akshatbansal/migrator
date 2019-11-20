package migrator.lib.repository.diff;

public class EqualsCompare<T> implements DiffCompare<T> {
    @Override
    public Boolean compare(T a, T b) {
        return a.equals(b);
    }
}