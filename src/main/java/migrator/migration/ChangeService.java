package migrator.migration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChangeService {
    protected ObservableList<TableChange> list;

    public ChangeService() {
        this.list = FXCollections.observableArrayList();
    }

    public ChangeService(TableChange ... changes) {
        this.list = FXCollections.observableArrayList(changes);
    }

    public ObservableList<TableChange> getChanges() {
        return this.list;
    }

    public void addTableChange(TableChange tableChange) {
        this.list.add(tableChange);
    }

    // public void addColumnChange(String table, ColumnChange columnChange) {
    //     TableChange tableChange = this.getOrCreateTable(table);
    //     tableChange.addColumnChange(columnChange);
    // }

    // public TableChange getOrCreateTable(String table) {
    //     TableChange tableChange = this.getTable(table);
    //     if (tableChange == null) {
    //         tableChange = new TableChange(table);
    //         this.addTableChange(tableChange);
    //     }
    //     return tableChange;
    // }

    public TableChange getTable(String table) {
        for (TableChange tableChange : this.list) {
            if (tableChange.getName() == table) {
                return tableChange;
            }
        }
        return null;
    }
}