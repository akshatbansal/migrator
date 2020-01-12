package migrator.app.gui.view.main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;

public class Layout extends SimpleView implements View {
    @FXML protected Pane leftPane;
    @FXML protected Pane centerPane;

    public Layout() {
        this.loadFxml("/layout/layout.fxml");
    }

    public void renderSide(Node node) {
        if (node == null) {
            this.clearSide();
            return;
        }
        this.leftPane.getChildren()
            .setAll(node);
    }

    public void renderSide(View view) {
        this.renderSide(view.getNode());
    }

    public void clearSide() {
        this.leftPane.getChildren().clear();
    }

    public void renderBody(Node node) {
        if (node == null) {
            this.clearBody();
            return;
        }
        this.centerPane.getChildren()
            .setAll(node);
    }

    public void renderBody(View view) {
        this.renderBody(view.getNode());
    }

    public void clearBody() {
        this.centerPane.getChildren().clear();
    }
}