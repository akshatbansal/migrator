package migrator.app.domain.table.component;

import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface TableCard extends GuiNode {
    public Subscription onSelect(Subscriber subscriber);
}