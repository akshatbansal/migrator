package migrator.app.gui.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.gui.BindObservableList;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.TableProperty;
import migrator.app.modification.ModificationCollection;
import migrator.app.modification.SimpleModification;

public class TableController {
    protected BindObservableList<Modification<TableProperty>, TableGuiModel> bindList;
    protected ModificationCollection<TableProperty> modifications;

    public TableController(ObservableList<Modification<TableProperty>> list) {
        this.bindList = new BindObservableList<>(
            list,
            FXCollections.observableArrayList(),
            new TableGuiAdapter()
        );
    }

    public ObservableList<TableGuiModel> getList() {
        return this.bindList.getList();
    }

    public void create(String tableName) {
        this.bindList.add(
            new SimpleModification<TableProperty>(
                null,
                null,
                null
            )
        );
    }

    public void remove(Modification<TableProperty> table) {
        this.modifications.delete(table);
    }

    public void refreshList() {
        this.modifications.
    }
}