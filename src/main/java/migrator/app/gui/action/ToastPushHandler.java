package migrator.app.gui.action;

import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.service.toast.ToastStore;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventHandler;

public class ToastPushHandler implements EventHandler {
    private ToastStore toastStore;

    public ToastPushHandler(ToastStore toastStore) {
        this.toastStore = toastStore;
    }

    @Override
    public void handle(Event<?> event) {
        Toast toast = (Toast) event.getValue();
        this.toastStore.show(toast);
    }
}