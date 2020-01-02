package migrator.app.domain.project;

import migrator.app.database.DatabaseStructure;
import migrator.app.domain.project.model.Project;

public class ProjectContainer {
    protected DatabaseStructure databaseStructure;
    protected Project project;

    public ProjectContainer(
        Project project,
        DatabaseStructure databaseStructure
    ) {
        this.project = project;
        this.databaseStructure = databaseStructure;
    }

    public DatabaseStructure getDatabaseStructure() {
        return this.databaseStructure;
    }

    public Project getProject() {
        return this.project;
    }
}