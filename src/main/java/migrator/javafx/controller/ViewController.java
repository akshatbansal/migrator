package migrator.javafx.controller;

import javafx.scene.Node;
import migrator.gui.GuiNode;
import migrator.javafx.helpers.View;

abstract public class ViewController implements GuiNode {
    protected Node node;

    public ViewController(View view, String viewLocation) {
        this.beforeInitialize();
        this.node = view.createFromFxml(this, viewLocation);
        this.afterInitialize();
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    protected void beforeInitialize() {}

    protected void afterInitialize() {}
}