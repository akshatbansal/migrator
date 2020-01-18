package migrator.app.domain.column.service;

import migrator.app.domain.table.model.Column;
import migrator.app.service.SimpleStore;

public class SimpleColumnStore extends SimpleStore<Column> {
    @Override
    public void remove(Column item) {
        if (item == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(item);
    }
}