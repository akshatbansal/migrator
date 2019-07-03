package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneRenderer {
    protected Pane pane;

    public PaneRenderer(Pane pane) {
        this.pane = pane;
    }

    public void replace(Node node) {
        this.hide();
        if (node == null) {
            return;
        }
        this.pane.getChildren().add(node);
    }

    public void hide() {
        this.pane.getChildren().clear();
    }
}