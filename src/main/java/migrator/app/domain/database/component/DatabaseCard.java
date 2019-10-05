package migrator.app.domain.database.component;

import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;
import migrator.app.gui.GuiNode;

public interface DatabaseCard extends GuiNode {
    public Subscription onConnect(Subscriber subscriber);
}