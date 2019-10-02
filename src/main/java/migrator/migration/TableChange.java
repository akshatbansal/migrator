package migrator.migration;

import javafx.collections.ObservableList;

public interface TableChange extends TableProperty {
    public String getOriginalName();
    public ObservableList<ColumnChange> getColumnsChanges();
    public ObservableList<IndexChange> getIndexesChanges();
    public ChangeCommand getCommand();
    public Boolean isNameChanged();
    public void clear();
}