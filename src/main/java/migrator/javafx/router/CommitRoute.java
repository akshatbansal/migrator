package migrator.javafx.router;

import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.router.Route;

public class CommitRoute implements Route {
    protected MainRenderer renderer;
    protected ChangeGuiKit guiKit;

    public CommitRoute(MainRenderer renderer, Container container, Gui gui) {
        this.renderer = renderer;
        this.guiKit = gui.getChangeKit();
    }

    @Override
    public void show(Object routeData) {
        this.renderer.replaceCenter(null);
        Node leftNode = (Node) this.guiKit.createCommitForm().getContent();
        VBox.setVgrow(leftNode, Priority.ALWAYS);
        this.renderer.replaceLeft(leftNode);
    }
}