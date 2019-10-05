package migrator.app.router;

public class Route {
    protected String name;
    protected Object value;

    public Route(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}