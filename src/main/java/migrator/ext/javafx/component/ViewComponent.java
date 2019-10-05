package migrator.ext.javafx.component;

import javafx.scene.Node;
import migrator.app.gui.GuiNode;
import migrator.ext.javafx.component.ViewLoader;

abstract public class ViewComponent implements GuiNode {
    protected Node node;
    protected ViewLoader viewLoader;

    public ViewComponent(ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    protected void loadView(String viewLocation) {
        this.node = this.viewLoader.load(this, viewLocation);
    }
}