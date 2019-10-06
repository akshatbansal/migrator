package migrator.ext.javafx.renderer;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import migrator.app.gui.GuiNode;
import migrator.app.router.RouteRenderer;

public class PaneRenderer implements RouteRenderer {
    protected Pane pane;

    public PaneRenderer(Pane pane) {
        this.pane = pane;
    }

    @Override
    public void render(GuiNode node) {
        this.pane.getChildren()
            .setAll(
                (Node) node.getContent()
            );
    }
}