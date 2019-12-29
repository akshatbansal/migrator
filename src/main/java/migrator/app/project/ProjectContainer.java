package migrator.app.project;

import migrator.app.database.DatabaseStructure;
import migrator.app.modification.ModificationContainer;
import migrator.app.project.property.ProjectProperty;
import migrator.lib.repository.UniqueItem;

public interface ProjectContainer extends UniqueItem {
    public DatabaseStructure getDatabaseStructure();
    public ModificationContainer getModificationContainer();
    public ProjectProperty getProjectProperty();
}