package migrator.ext.javafx.component.card;

import migrator.app.gui.GuiNode;

public interface CardComponent<T> extends GuiNode {
    public T getValue();
}