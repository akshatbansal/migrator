package migrator.gui;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;

public interface Input<T> extends GuiNode {
    public T getValue();
    public void setValue(T value);
    public Subscription onChange(Subscriber subscriber);
}