package migrator.app.migration.model;

public interface ColumnChange extends ColumnProperty {
    public ChangeCommand getCommand();
    public ColumnProperty getOriginal();
    public Boolean hasNameChanged();
    public Boolean hasFormatChanged();
    public Boolean hasDefaultValueChanged();
    public Boolean hasNullEnabledChanged();
    public void restore();
}