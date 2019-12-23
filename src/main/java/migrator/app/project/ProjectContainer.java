package migrator.app.project;

import migrator.app.database.DatabaseStructure;
import migrator.app.modification.ModificationContainer;
import migrator.app.project.property.ProjectProperty;

public interface ProjectContainer {
    public DatabaseStructure getDatabaseStructure();
    public ModificationContainer getModificationContainer();
    public ProjectProperty getProjectProperty();
}