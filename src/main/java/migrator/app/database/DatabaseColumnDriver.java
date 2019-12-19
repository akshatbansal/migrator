package migrator.app.database;

import java.util.List;
import java.util.Map;

public interface DatabaseColumnDriver {
    public List<Map<String, String>> getColumns(String tableName);
}