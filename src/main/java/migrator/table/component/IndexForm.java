package migrator.table.component;

import migrator.gui.GuiNode;
import migrator.table.model.Index;

public interface IndexForm extends GuiNode {
    public void setIndex(Index index);
}