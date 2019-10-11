package migrator.app.toast;

public class Toast {
    protected String message;

    public Toast(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}