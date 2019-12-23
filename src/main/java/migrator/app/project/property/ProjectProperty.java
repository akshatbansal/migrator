package migrator.app.project.property;

import javafx.beans.property.StringProperty;
import migrator.lib.repository.UniqueItem;

public interface ProjectProperty extends UniqueItem {
    public StringProperty nameProperty();
    public StringProperty outputProperty();
    public StringProperty folderProperty();
    public DatabaseProperty getDatabase();
}