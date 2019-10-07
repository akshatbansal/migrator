package migrator.app.domain.connection.component;

import migrator.app.gui.GuiNode;

public interface ConnectionList extends GuiNode {
    public void addConnection();
    public void commit();
}