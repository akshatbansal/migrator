package migrator.app.migration.model;

public interface ColumnChange extends ColumnProperty {
    public ChangeCommand getCommand();
    public String getOriginalName();
    public Boolean hasNameChanged();
    public Boolean hasAttributeChanged();
    public void clear();
}