package migrator.app.gui;

import java.util.Hashtable;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class GuiModel {
    protected Map<String, BooleanProperty> attributes;

    public GuiModel() {
        this.attributes = new Hashtable<>();
    }

    private BooleanProperty getOrCreateAttribute(String name) {
        if (!this.attributes.containsKey(name)) {
            this.attributes.put(name, new SimpleBooleanProperty(false));
        }
        return this.attributes.get(name);
    }

    public BooleanProperty attribute(String name) {
        return this.getOrCreateAttribute(name);
    }

    public void enable(String name) {
        this.getOrCreateAttribute(name).set(true);
    }

    public void disable(String name) {
        this.getOrCreateAttribute(name).set(false);
    }
} 