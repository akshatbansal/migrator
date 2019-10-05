package migrator.app.domain.table.component;

import migrator.app.domain.table.model.Column;
import migrator.app.gui.GuiNode;

public interface ColumnForm extends GuiNode {
    public void setColumn(Column column);
}