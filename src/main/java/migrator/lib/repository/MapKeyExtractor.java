package migrator.lib.repository;

public interface MapKeyExtractor<T> {
    public String getKeyOf(T item);
}