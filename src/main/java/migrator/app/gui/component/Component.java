package migrator.app.gui.component;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import migrator.lib.dispatcher.Event;

public interface Component {
    public Node getNode();
    public ObjectProperty<Event<?>> outputs();
}