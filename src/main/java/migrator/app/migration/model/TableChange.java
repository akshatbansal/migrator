package migrator.app.migration.model;

import javafx.collections.ObservableList;

public interface TableChange extends TableProperty {
    public String getOriginalName();
    public TableProperty getOriginal();
    public ObservableList<? extends ColumnChange> getColumnsChanges();
    public ObservableList<? extends IndexChange> getIndexesChanges();
    public ChangeCommand getCommand();
    public Boolean hasNameChanged();
}