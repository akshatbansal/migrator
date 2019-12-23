package migrator.app.database.index;

import java.util.List;

public interface DatabaseIndexDriver {
    public List<List<String>> getIndexes(String tableName);
}