package migrator.ext.javafx.component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import migrator.app.gui.GuiNode;

public class JavafxLayout {
    protected Pane side;
    protected Pane body;

    public JavafxLayout(Pane body, Pane side) {
        this.body = body;
        this.side = side;
    }

    public void renderSide(Node node) {
        if (node == null) {
            this.side.getChildren().clear();
            return;
        }
        this.side.getChildren()
            .setAll(node);
    }

    public void renderBody(Node node) {
        if (node == null) {
            this.side.getChildren().clear();
            return;
        }
        this.body.getChildren()
            .setAll(node);
    }

    public void renderBody(GuiNode node) {
        this.renderBody((Node) node.getContent());
    }

    public void render(Node body, Node side) {
        this.renderBody(body);
        this.renderSide(side);
    }

    public void render(GuiNode body, GuiNode side) {
        this.renderBody((Node) body.getContent());
        this.renderSide((Node) side.getContent());
    }
}