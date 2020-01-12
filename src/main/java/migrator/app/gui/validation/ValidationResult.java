package migrator.app.gui.validation;

public class ValidationResult {
    private boolean ok;
    private String message;

    public ValidationResult(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public ValidationResult(String message) {
        this(message.isEmpty(), message);
    }

    public boolean isOk() {
        return this.ok;
    }

    public String getMessage() {
        return this.message;
    }
}