package migrator.app.gui;

import migrator.lib.hotkyes.HotkeyFactory;
import migrator.lib.hotkyes.HotkeysService;
import migrator.lib.hotkyes.SimpleHotkeysService;

public class HotkeyContainer {
    private HotkeysService hotkeysServiceValue;
    private HotkeyFactory hotkeyFactoryValue;

    public HotkeyContainer() {
        this.hotkeyFactoryValue = new HotkeyFactory();
        this.hotkeysServiceValue = new SimpleHotkeysService();
    }

    public HotkeysService hotkeysService() {
        return this.hotkeysServiceValue;
    }

    public HotkeyFactory hotkeyFactory() {
        return this.hotkeyFactoryValue;
    }
}