package migrator.app.project;

import migrator.app.database.DatabaseContainer;
import migrator.app.project.property.DatabaseProperty;
import migrator.app.project.property.ProjectProperty;

public class ProjectContainerFactory {
    protected DatabaseContainer databaseContainer;

    public ProjectContainer create(ProjectProperty projectProperty) {
        DatabaseProperty databaseProperty = projectProperty.getDatabase();
        return new SimpleProjectContainer(
            this.databaseContainer.getStructureFactoryFor(databaseProperty.driverProperty().getValue())
                .create(
                    databaseProperty.urlProperty().getValue(),
                    databaseProperty.userProperty().getValue(),
                    databaseProperty.passwordProperty().getValue()
                ),
            projectProperty
        );
    }
}