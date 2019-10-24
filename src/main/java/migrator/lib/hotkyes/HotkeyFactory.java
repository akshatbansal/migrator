package migrator.lib.hotkyes;

public class HotkeyFactory {
    public Hotkey create(String letter, boolean ctrl, boolean shift) {
        return new Hotkey(letter, ctrl, shift);
    }

    public Hotkey create(int keyCode, boolean ctrl, boolean shift) {
        String letter = "";
        if (keyCode == 27) {
            letter = "ESCAPE";
        } else {
            letter = Character.toString(keyCode);
        }
        return new Hotkey(letter, ctrl, shift);
    }
}