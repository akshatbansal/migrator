package migrator.gui.javafx;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import migrator.emitter.Emitter;
import migrator.emitter.EventEmitter;
import migrator.emitter.Subscriber;
import migrator.emitter.Subscription;
import migrator.gui.SelectInput;

public class JavafxSelectInput<T> implements SelectInput<T> {
    protected Emitter emitter;
    protected ComboBox<T> select;

    public JavafxSelectInput(List<T> options, T value) {
        this.emitter = new EventEmitter();
        this.select = new ComboBox<>(FXCollections.observableArrayList(options));
        this.select.setValue(value);

        this.select.setOnAction((ActionEvent event) -> {
            this.emitter.emit("change", event);
        });
    }
    
    @Override
    public Object getContent() {
        return this.select;
    }

    @Override
    public T getValue() {
        return this.select.getSelectionModel().getSelectedItem();
    }

    @Override
    public Subscription onChange(Subscriber subscriber) {
        return this.emitter.on("change", subscriber);
    }

    @Override
    public void setOptions(List<T> options) {
        this.select.getItems().clear();
        this.select.getItems().addAll(options);
    }

    @Override
    public void setValue(T value) {
        this.select.getSelectionModel().select(value);
    }
}