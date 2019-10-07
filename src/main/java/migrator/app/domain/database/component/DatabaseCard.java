package migrator.app.domain.database.component;

import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.gui.GuiNode;

public interface DatabaseCard extends GuiNode {
    public Subscription<DatabaseConnection> onConnect(Subscriber<DatabaseConnection> subscriber);
}