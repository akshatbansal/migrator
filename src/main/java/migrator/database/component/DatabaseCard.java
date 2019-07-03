package migrator.database.component;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.GuiNode;

public interface DatabaseCard extends GuiNode {
    public Subscription onConnect(Subscriber subscriber);
}