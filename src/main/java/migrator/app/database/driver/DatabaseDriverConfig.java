package migrator.app.database.driver;

import java.util.HashMap;
import java.util.Map;

public class DatabaseDriverConfig {
    protected Map<String, DatabaseDriverFactory> drivers;

    public DatabaseDriverConfig() {
        this.drivers = new HashMap<>();
    }

    public void addDriver(String name, DatabaseDriverFactory factory) {
        this.drivers.put(name, factory);
    }

    public Map<String, DatabaseDriverFactory> getDrivers() {
        return this.drivers;
    }
}