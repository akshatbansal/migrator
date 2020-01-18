package migrator.app.gui.component.card;

import javafx.beans.property.ObjectProperty;
import migrator.app.gui.component.Component;

public interface CardComponent<T> extends Component {
    public T getValue();
    public void bind(ObjectProperty<T> item);
    public void destroy();
}