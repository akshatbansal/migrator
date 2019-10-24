package migrator.lib.hotkyes;

import java.util.HashMap;
import java.util.Map;

import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class SimpleHotkeysService implements HotkeysService {
    protected Emitter<Hotkey> emitter;
    protected Map<String, String> hotkeysToNames;

    public SimpleHotkeysService() {
        this.emitter = new EventEmitter<>();
        this.hotkeysToNames = new HashMap<>();
    }

    @Override
    public void connectKeyboard(String name, String hotkeyCode) {
        this.hotkeysToNames.put(hotkeyCode, name);
    }

    @Override
    public Subscription<Hotkey> on(String name, Subscriber<Hotkey> subscription) {
        return this.emitter.on(name, subscription);
    }

    @Override
    public void keyPressed(Hotkey hotkey) {
        if (!this.hotkeysToNames.containsKey(hotkey.getCode())) {
            return;
        }
        this.emitter.emit(
            this.hotkeysToNames.get(hotkey.getCode()),
            hotkey
        );
    }
}