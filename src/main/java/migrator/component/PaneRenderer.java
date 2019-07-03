package migrator.component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneRenderer implements Renderer<Node> {
    protected Pane node;

    public PaneRenderer(Pane node) {
        this.node = node;
    }

    @Override
    public void show(Node node) {
        this.node.getChildren().clear();
        this.node.getChildren().add(node);
    }

    @Override
    public void hide() {
        this.node.getChildren().clear();
    }
}