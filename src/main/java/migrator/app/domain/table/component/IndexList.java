package migrator.app.domain.table.component;

import migrator.app.domain.table.model.Index;
import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface IndexList extends GuiNode {
    public void deselect();
    public Subscription<Index> onSelect(Subscriber<Index> subscriber);
}