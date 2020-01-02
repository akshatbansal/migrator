package migrator.app.database.column;

import java.util.List;
import java.util.Map;

public interface DatabaseColumnDriver {
    public List<Map<String, String>> getColumns(String tableName);
} 