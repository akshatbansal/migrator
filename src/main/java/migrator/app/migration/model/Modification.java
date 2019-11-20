package migrator.app.migration.model;

public interface Modification<T> {
    public T getOriginal();
    public T getChange();
    public ChangeCommand getChangeCommand();
    public void updateOriginal(T original);
}