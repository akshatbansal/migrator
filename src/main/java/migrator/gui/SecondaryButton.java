package migrator.gui;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;

public interface SecondaryButton extends GuiNode {
    public void setText(String value);
    public Subscription onAction(Subscriber subscriber);
}