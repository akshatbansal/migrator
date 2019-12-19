package migrator.app.database;

import java.util.List;

public interface DatabaseTableDriver {
    public List<String> getTables();
}