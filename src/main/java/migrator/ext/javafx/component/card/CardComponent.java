package migrator.ext.javafx.component.card;

import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface CardComponent<T> extends GuiNode {
    public Subscription<Card<T>> onPrimary(Subscriber<Card<T>> subscriber);
    public Subscription<Card<T>> onSecondary(Subscriber<Card<T>> subscriber);
    public void focus();
    public void blur();
    public void changeTo(String changeType);
}