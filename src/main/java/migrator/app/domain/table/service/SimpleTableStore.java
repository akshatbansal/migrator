package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Table;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.service.SimpleStore;

public class SimpleTableStore extends SimpleStore<Table> {
    @Override
    public void remove(Table table) {
        if (!table.getChangeCommand().isType(ChangeCommand.CREATE)) {
            table.getChangeCommand().setType(ChangeCommand.DELETE);
            return;
        }
        if (table == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(table);
    }
}