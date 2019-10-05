package migrator.app.domain.table.component;

import migrator.app.domain.table.model.Index;
import migrator.app.gui.GuiNode;

public interface IndexForm extends GuiNode {
    public void setIndex(Index index);
}