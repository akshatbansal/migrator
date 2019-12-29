package migrator.app.project;

import migrator.app.database.DatabaseStructure;
import migrator.app.modification.DatabaseModificationContainer;
import migrator.app.modification.ModificationContainer;
import migrator.app.project.property.ProjectProperty;

public class SimpleProjectContainer implements ProjectContainer {
    protected DatabaseStructure databaseStructure;
    protected ModificationContainer modificationContainer;
    protected ProjectProperty projectProperty;

    public SimpleProjectContainer(DatabaseStructure databaseStructure, ProjectProperty projectProperty) {
        this.databaseStructure = databaseStructure;
        this.projectProperty = projectProperty;
        this.modificationContainer = new DatabaseModificationContainer(this.databaseStructure);
    }

    @Override
    public String getUniqueKey() {
        return this.projectProperty.getUniqueKey();
    }

    @Override
    public DatabaseStructure getDatabaseStructure() {
        return this.databaseStructure;
    }

    @Override
    public ModificationContainer getModificationContainer() {
        return this.modificationContainer;
    }

    @Override
    public ProjectProperty getProjectProperty() {
        return this.projectProperty;
    }
}