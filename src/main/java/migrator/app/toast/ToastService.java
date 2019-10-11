package migrator.app.toast;

import javafx.collections.ObservableList;

public interface ToastService {
    public Toast show(String message);
    public void show(Toast toast);
    public void hide(Toast toast);
    public ObservableList<Toast> getList();
}