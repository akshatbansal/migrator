package migrator.app.toast;

public class Toast {
    protected String message;
    protected String type;

    public Toast(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public String getType() {
        return this.type;
    }
}