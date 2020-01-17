package migrator.app.gui.route;

public class SimpleRoute implements Route {
    private String name;

    public SimpleRoute(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}