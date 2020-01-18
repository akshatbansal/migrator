package migrator.app.domain.index.service;

import migrator.app.domain.table.model.Index;
import migrator.app.service.SimpleStore;

public class IndexStore extends SimpleStore<Index> {
    @Override
    public void remove(Index item) {
        if (item == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(item);
    }
}