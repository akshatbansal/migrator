package migrator.app.migration.model;

public interface Modification<T> {
    public T getOriginal();
    public T getModification();
    public ChangeCommand getChangeCommand();
    public void updateOriginal(T original);
}