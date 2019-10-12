package migrator.app.toast;

import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.application.Platform;

public class AutohideToastService implements ToastService {
    protected ObservableList<Toast> list;
    protected Integer hideDelay;

    public AutohideToastService(Integer hideDelay) {
        this.hideDelay = hideDelay;
        this.list = FXCollections.observableArrayList();
    }

    @Override
    public Toast show(String message, String type) {
        Toast toast = new Toast(message, type);
        this.show(toast);
        return toast;
    }

    @Override
    public void show(Toast toast) {
        ToastService self = this;
        this.list.add(toast);
        Timer timer = new Timer();
        timer.schedule(
            new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        self.hide(toast);
                    });
                }
            },
            this.hideDelay
        );
    }

    @Override
    public void hide(Toast toast) {
        this.list.remove(toast);
    }

    public ObservableList<Toast> getList() {
        return this.list;
    }

    @Override
    public Toast error(String message) {
        return this.show(message, "error");
    }

    @Override
    public Toast info(String message) {
        return this.show(message, "info");
    }

    @Override
    public Toast success(String message) {
        return this.show(message, "success");
    }

    @Override
    public Toast warning(String message) {
        return this.show(message, "warning");
    }
}