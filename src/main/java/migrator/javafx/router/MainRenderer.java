package migrator.javafx.router;

import javafx.scene.layout.Pane;
import javafx.scene.Node;

public class MainRenderer {
    protected PaneRenderer left;
    protected PaneRenderer center;

    public MainRenderer(Pane centerPane, Pane leftPane) {
        this.center = new PaneRenderer(centerPane);
        this.left = new PaneRenderer(leftPane);
    }

    public void replaceCenter(Node centerNode) {
        this.center.replace(centerNode);
        this.left.hide();
    }

    public void replaceLeft(Node leftNode) {
        this.left.replace(leftNode);
    }

    public void hideLeft() {
        this.left.hide();
    }
}