package migrator.table.component;

import migrator.app.gui.GuiNode;
import migrator.table.model.Column;

public interface ColumnForm extends GuiNode {
    public void setColumn(Column column);
}