package migrator.table.component;

import migrator.gui.GuiNode;
import migrator.table.model.Column;

public interface ColumnForm extends GuiNode {
    public void setColumn(Column column);
}