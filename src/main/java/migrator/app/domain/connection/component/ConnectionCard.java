package migrator.app.domain.connection.component;

import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;
import migrator.app.domain.connection.model.Connection;
import migrator.app.gui.GuiNode;

public interface ConnectionCard extends GuiNode {
    public Subscription<Connection> onSelect(Subscriber<Connection> subscriber);
    public Subscription<Connection> onConnect(Subscriber<Connection> subscriber);
}