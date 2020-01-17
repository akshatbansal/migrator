package migrator.app.gui.component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import migrator.lib.dispatcher.Event;

public class SimpleComponent implements Component {
    protected Node node;
    protected FxmlLoader fxmlLoader;
    protected ObjectProperty<Event<?>> events;

    public SimpleComponent() {
        this.fxmlLoader = new FxmlLoader();
        this.events = new SimpleObjectProperty<>();
    }

    protected void loadFxml(String viewLocation) {
        this.node = this.fxmlLoader.load(this, viewLocation);
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @Override
    public ObjectProperty<Event<?>> outputs() {
        return this.events;
    }

    protected void output(Event<?> event) {
        this.events.set(event);
    }
}