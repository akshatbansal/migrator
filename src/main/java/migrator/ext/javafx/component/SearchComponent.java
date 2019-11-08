package migrator.ext.javafx.component;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import migrator.lib.emitter.Emitter;
import migrator.lib.emitter.EventEmitter;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public class SearchComponent extends ViewComponent {
    protected StringProperty searchString;
    protected Emitter<Object> emitter;

    @FXML protected TextField searchField;

    public SearchComponent(ViewLoader viewLoader, StringProperty searchString) {
        super(viewLoader);
        this.searchString = searchString;
        this.emitter = new EventEmitter<>();

        this.loadView("/layout/component/search.fxml");

        this.searchField.textProperty().bindBidirectional(this.searchString);
    }

    @FXML public void close() {
        this.searchString.set("");
        this.emitter.emit("close");
    }

    public void focus() {
        this.searchField.requestFocus();
    }

    public Subscription<Object> onClose(Subscriber<Object> subscriber) {
        return this.emitter.on("close", subscriber);
    }
}