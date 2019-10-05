package migrator.component;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class NewWindowRenderer implements Renderer<Node> {
    protected String title;
    protected Integer width;
    protected Integer height;
    protected Stage stage;

    public NewWindowRenderer() {
        this("Migrator", 450, 450);
    }

    public NewWindowRenderer(String title, Integer width, Integer height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    @Override
    public void show(Node node) {
        if (this.stage != null) {
            this.stage.requestFocus();
            return;
        }
        this.stage = new Stage();
        Pane root = new Pane();
        root.getChildren().add(node);
        this.stage.setTitle(this.title);
        this.stage.setScene(new Scene(root, this.width, this.height));
        this.stage.show();
        this.stage.setOnHidden(event -> {
            this.stage = null;
        });
    }

    @Override
    public void hide() {
        this.stage.hide();
    }
}