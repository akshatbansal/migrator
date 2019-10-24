package migrator.app.router;

import java.util.LinkedList;
import java.util.List;

import migrator.app.gui.GuiNode;

public abstract class GuiNodeConnection<T> implements RouteConnection<T> {
    protected List<GuiNode> guiNodes;

    public GuiNodeConnection() {
        this.guiNodes = new LinkedList<>();
    }

    @Override
    public void hide() {
        for (GuiNode node : this.guiNodes) {
            node.destroy();
        }
        this.guiNodes.clear();
    }
}