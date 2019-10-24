package migrator.lib.hotkyes;

import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface HotkeysService {
    public Subscription<Hotkey> on(String name, Subscriber<Hotkey> subscription);
    public void connectKeyboard(String name, String hotkeyCode);
    public void keyPressed(Hotkey hotkey);
}