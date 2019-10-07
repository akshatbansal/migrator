package migrator.app.domain.table.component;

import migrator.app.domain.table.model.Table;
import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface TableCard extends GuiNode {
    public Subscription<Table> onSelect(Subscriber<Table> subscriber);
}