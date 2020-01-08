package migrator.app.migration.model;

public interface ColumnChange extends ColumnProperty {
    public ChangeCommand getCommand();
    public ColumnProperty getOriginal();
    public void restore();
}