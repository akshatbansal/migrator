package migrator.app.gui.service;

import migrator.app.gui.action.ToastCloseHandler;
import migrator.app.gui.action.ToastPushHandler;
import migrator.app.gui.service.toast.ToastStore;
import migrator.app.service.Service;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;

public class ToastService implements Service {
    protected EventDispatcher dispatcher;
    protected ToastStore toastStore;

    protected EventHandler toastClose;
    protected EventHandler toastShow;

    public ToastService(EventDispatcher dispatcher, ToastStore toastStore) {
        this.dispatcher = dispatcher;
        this.toastStore = toastStore;
        this.toastClose = new ToastCloseHandler(toastStore);
        this.toastShow = new ToastPushHandler(toastStore);
    }

    @Override
    public void start() {
        this.dispatcher.register("toast.close", this.toastClose);
        this.dispatcher.register("toast.push", this.toastShow);
        
    }

    @Override
    public void stop() {
        this.dispatcher.unregister("toast.close", this.toastClose);
        this.dispatcher.unregister("toast.push", this.toastShow);
    }
}