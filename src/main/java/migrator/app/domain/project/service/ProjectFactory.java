package migrator.app.domain.project.service;

import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.DatabaseFactory;
import migrator.app.domain.project.model.Project;
import migrator.lib.uid.Generator;

public class ProjectFactory {
    protected DatabaseFactory databaseFactory;
    protected Generator idGenerator;

    public ProjectFactory(DatabaseFactory databaseFactory, Generator idGenerator) {
        this.databaseFactory = databaseFactory;
        this.idGenerator = idGenerator;
    }

    public Project create(DatabaseConnection databaseConnection, String name, String outputType, String folder) {
        return new Project(
            databaseConnection,
            this.idGenerator.next(),
            name,
            outputType,
            folder
        );
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