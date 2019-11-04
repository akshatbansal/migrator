package migrator.app.domain.table.component;

import migrator.app.domain.table.model.Column;
import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface ColumnList extends GuiNode {
    public Subscription<Column> onSelect(Subscriber<Column> subscriber);
    public void deselect();
}