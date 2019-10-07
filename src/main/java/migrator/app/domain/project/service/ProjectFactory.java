package migrator.app.domain.project.service;

import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.project.model.Project;

public class ProjectFactory {
    protected DatabaseFactory databaseFactory;

    public ProjectFactory(DatabaseFactory databaseFactory) {
        this.databaseFactory = databaseFactory;
    }

    public Project create(DatabaseConnection databaseConnection, String name, String outputType, String folder) {
        return new Project(databaseConnection, name, outputType, folder);
    }

    public Project create(String name) {
        return this.create(
            this.databaseFactory.create(name),
            name,
            "",
            ""
        );
    }
}