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
            this.clearSide();
            return;
        }
        this.side.getChildren()
            .setAll(node);
    }

    public void renderSide(GuiNode node) {
        this.renderSide((Node) node.getContent());
    }

    public void clearSide() {
        this.side.getChildren().clear();
    }

    public void renderBody(Node node) {
        if (node == null) {
            this.clearBody();
            return;
        }
        this.body.getChildren()
            .setAll(node);
    }

    public void clearBody() {
        this.body.getChildren().clear();
    }

    public void renderBody(GuiNode node) {
        this.renderBody((Node) node.getContent());
    }
}