package migrator.app.gui.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.app.gui.BindObservableList;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.TableProperty;

public class TableController {
    protected BindObservableList<Modification<TableProperty>, TableGuiModel> bindList;

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

    public void add(Modification<TableProperty> table) {
        this.bindList.add(table);
    }

    public void remove(TableProperty table) {

    }
}