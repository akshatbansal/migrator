package migrator.app.domain.index.service;

import migrator.app.domain.table.model.Index;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.service.SimpleStore;

public class IndexStore extends SimpleStore<Index> {
    @Override
    public void remove(Index item) {
        if (!item.getChangeCommand().isType(ChangeCommand.CREATE)) {
            item.getChangeCommand().setType(ChangeCommand.DELETE);
            return;
        }
        if (item == this.selected.get()) {
            this.deselect();
        }
        this.list.remove(item);
    }
}