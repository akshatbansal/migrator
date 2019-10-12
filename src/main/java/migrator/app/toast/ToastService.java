package migrator.app.toast;

import javafx.collections.ObservableList;

public interface ToastService {
    public Toast show(String message, String type);
    public void show(Toast toast);
    public Toast success(String message);
    public Toast error(String message);
    public Toast warning(String message);
    public Toast info(String message);
    public void hide(Toast toast);
    public ObservableList<Toast> getList();
}