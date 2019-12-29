package migrator.app.gui.table;

import javafx.collections.ObservableList;
import migrator.app.gui.GuiModel;
import migrator.app.migration.model.Modification;
import migrator.app.migration.model.TableProperty;

public class TableGuiModel extends GuiModel {
    protected Modification<TableProperty> tableProperty;
    protected ObservableList<Modification<?>> modifications;

    public TableGuiModel(Modification<TableProperty> tableProperty, ObservableList<Modification<?>> modifications) {
        this.tableProperty = tableProperty;
        this.modifications = modifications;
    }

    public Modification<TableProperty> getTableProperty() {
        return this.tableProperty;
    }

    public ObservableList<Modification<?>> getModifications() {
        return this.modifications;
    }
}