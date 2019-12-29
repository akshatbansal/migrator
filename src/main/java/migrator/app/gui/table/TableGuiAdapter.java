package migrator.app.gui.table;

import javafx.collections.FXCollections;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.TableProperty;
import migrator.lib.adapter.Adapter;

public class TableGuiAdapter implements Adapter<Modification<TableProperty>, TableGuiModel> {
    @Override
    public Modification<TableProperty> concretize(TableGuiModel item) {
        return item.getTableProperty();
    }

    @Override
    public TableGuiModel generalize(Modification<TableProperty> item) {
        return new TableGuiModel(item, FXCollections.observableArrayList());
    }
}