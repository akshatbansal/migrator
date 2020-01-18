package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Table;
import migrator.app.service.SimpleStore;

public class SimpleTableStore extends SimpleStore<Table> {
    @Override
    public void remove(Table table) {
        if (table == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(table);
    }
}