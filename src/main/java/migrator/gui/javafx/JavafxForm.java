package migrator.gui.javafx;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.Alignment;
import migrator.gui.Form;
import migrator.gui.GuiNode;
import migrator.gui.Input;
import migrator.gui.Section;

public class JavafxForm implements Form {
    protected Map<String, Input<?>> inputs;
    protected Emitter emitter;
    protected Section section;

    public JavafxForm(Section section) {
        this.emitter = new EventEmitter();
        this.inputs = new HashMap<>();
        this.section = section;
    }

    @Override
    public Object getContent() {
        return this.section.getContent();
    }

    @Override
    public Input<?> getInput(String name) {
        return this.inputs.get(name);
    }

    @Override
    public void addInput(String name, Input<?> input) {
        this.inputs.put(name, input);
        input.onChange((Object o) -> {
            this.emitter.emit("change");
        });
    }

    @Override
    public Subscription onChange(Subscriber subscriber) {
        return this.emitter.on("change", subscriber);
    }

    @Override
    public Subscription onSubmit(Subscriber subscriber) {
        return this.emitter.on("submit", subscriber);
    }

    @Override
    public void addChild(GuiNode child) {
        this.section.addChild(child);
    }

    @Override
    public void growChild(GuiNode child) {
        this.section.growChild(child);
    }

    @Override
    public void setAlignment(Alignment alignment) {
        this.section.setAlignment(alignment);
    }

    @Override
    public void setWidth(Double width) {
        this.section.setWidth(width);
    }
}