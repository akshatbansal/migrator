package migrator.gui;

import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;

public interface Window {
    public void show();
    public void hide();
    public Subscription addOnShow(Subscriber subscriber);
    public Subscription addOnHide(Subscriber subscriber);
    public void setContent(GuiNode node);
}