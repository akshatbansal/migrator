package migrator.app.migration.model;

public interface IndexChange extends IndexProperty {
    public ChangeCommand getCommand();
    public void restore();
    public IndexProperty getOriginal();
}