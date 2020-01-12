package migrator.app.gui.service.toast;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class PermanentToastStore implements ToastStore {
    protected ObservableList<Toast> list;

    public PermanentToastStore() {
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
        this.list.add(toast);
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