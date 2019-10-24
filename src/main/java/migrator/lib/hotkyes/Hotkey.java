package migrator.lib.hotkyes;

import java.util.ArrayList;
import java.util.List;

public class Hotkey {
    public static final String SHIFT = "SHIFT";
    public static final String CONTROL = "CTRL";
    public static final String ANY = "ANY";

    protected boolean shift;
    protected boolean ctrl;
    protected String letter;

    public Hotkey(String letter, boolean ctrl, boolean shift) {
        this.letter = letter;
        this.ctrl = ctrl;
        this.shift = shift;
    }

    public String getCode() {
        List<String> codeParts = new ArrayList<>();
        if (this.ctrl) {
            codeParts.add(Hotkey.CONTROL);
        }
        if (this.shift) {
            codeParts.add(Hotkey.SHIFT);
        }
        codeParts.add(this.letter);
        return String.join("+", codeParts);
    }
}