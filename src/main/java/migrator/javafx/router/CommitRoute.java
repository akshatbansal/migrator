package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.change.service.GuiKit;
import migrator.javafx.Container;
import migrator.router.Route;

public class CommitRoute implements Route {
    protected MainRenderer renderer;
    protected GuiKit guiKit;

    public CommitRoute(MainRenderer renderer, Container container) {
        this.renderer = renderer;
        this.guiKit = container.getGui().getChangeKit();
    }

    @Override
    public void show(Object routeData) {
        this.renderer.replaceCenter(null);
        Node leftNode = (Node) this.guiKit.createCommitForm().getContent();
        VBox.setVgrow(leftNode, Priority.ALWAYS);
        this.renderer.replaceLeft(leftNode);
    }
}