package migrator.table.component;

import migrator.app.gui.GuiNode;
import migrator.table.model.Index;

public interface IndexForm extends GuiNode {
    public void setIndex(Index index);
}