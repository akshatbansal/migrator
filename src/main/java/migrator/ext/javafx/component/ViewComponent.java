package migrator.ext.javafx.component;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Node;
import migrator.app.gui.GuiNode;
import migrator.ext.javafx.component.ViewLoader;
import migrator.lib.emitter.Subscription;

abstract public class ViewComponent implements GuiNode {
    protected Node node;
    protected ViewLoader viewLoader;
    protected List<Subscription<?>> subscriptions;

    public ViewComponent(ViewLoader viewLoader) {
        this.viewLoader = viewLoader;
        this.subscriptions = new LinkedList<>();
    }

    @Override
    public Object getContent() {
        return this.node;
    }

    protected void loadView(String viewLocation) {
        this.node = this.viewLoader.load(this, viewLocation);
    }

    @Override
    public void destroy() {
        for (Subscription<?> s : this.subscriptions) {
            s.unsubscribe();
        }
    }
}