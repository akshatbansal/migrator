package migrator.gui;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;

public interface Form extends Section {
    public Subscription onChange(Subscriber subscriber);
    public Subscription onSubmit(Subscriber subscriber);
    public Input<?> getInput(String name);
    public void addInput(String name, Input<?> input);
}