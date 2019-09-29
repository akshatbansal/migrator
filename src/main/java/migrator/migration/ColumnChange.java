package migrator.migration;

public interface ColumnChange extends ColumnProperty {
    public ChangeCommand getCommand();
    public String getOriginalName();
    public Boolean hasNameChanged();
}