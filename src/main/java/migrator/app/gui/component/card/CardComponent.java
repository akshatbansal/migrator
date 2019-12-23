package migrator.app.gui.component.card;

import javafx.scene.Node;

public interface CardComponent<T> {
    public Node getNode();
    public void addListener(CardListener<T> listener);
}