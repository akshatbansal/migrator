package migrator.lib.result;

public class ObjectResult<T> {
    protected T item;
    protected String error;
    protected boolean ok;

    public ObjectResult(T item) {
        this.item = item;
        this.error = "";
        this.ok = true;
    }
    
    public ObjectResult(String error) {
        this.item = null;
        this.error = error;
        this.ok = false;
    }

    public T getValue() {
        return this.item;
    }

    public boolean isOk() {
        return this.ok;
    }

    public String getError() {
        return this.error;
    }
}