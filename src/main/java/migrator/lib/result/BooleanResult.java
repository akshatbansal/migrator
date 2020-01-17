package migrator.lib.result;

public class BooleanResult {
    protected String error;
    protected boolean ok;
    
    public BooleanResult(String error) {
        this.error = error;
        this.ok = false;
    }

    public BooleanResult() {
        this.error = "";
        this.ok = true;
    }

    public boolean isOk() {
        return this.ok;
    }

    public String getError() {
        return this.error;
    }
}