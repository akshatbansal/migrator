package migrator.migration;

import java.util.List;
import migrator.migration.TableChange;

public interface Migration {
    public Boolean create(List<TableChange> changes);
    public Boolean create(TableChange ... changes);
}