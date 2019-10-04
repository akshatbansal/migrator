package migrator.table.component;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.app.gui.GuiNode;

public interface TableCard extends GuiNode {
    public Subscription onSelect(Subscriber subscriber);
}