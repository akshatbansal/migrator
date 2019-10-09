package migrator.app.domain.table.component;

import migrator.app.gui.GuiNode;

public interface TableList extends GuiNode {
    public void addTable();
    public void commit();
}