package migrator.ext.javafx.loading;

import javafx.stage.Window;
import migrator.app.loading.LoadingIndicator;

public class CursorLoadingIndicator implements LoadingIndicator {
    protected Window window;

    public CursorLoadingIndicator(Window window) {
        this.window = window;
    }

    @Override
    public void start() {
        this.window.getScene().getRoot().getStyleClass().add("loading");
    }

    @Override
    public void stop() {
        this.window.getScene().getRoot().getStyleClass().remove("loading");
    }
}