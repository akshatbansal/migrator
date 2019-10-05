package migrator.app.domain.connection.component;

import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;
import migrator.app.gui.GuiNode;

public interface ConnectionCard extends GuiNode {
    public Subscription onSelect(Subscriber subscriber);
    public Subscription onConnect(Subscriber subscriber);
}