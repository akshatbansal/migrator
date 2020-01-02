package migrator.app.database.index;

import javafx.collections.ObservableList;
import migrator.app.migration.model.ColumnProperty;

public interface IndexStructureFactory {
    public IndexStructure create(ObservableList<ColumnProperty> columns);
}