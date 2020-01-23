package migrator.app.gui.validation;

import migrator.app.database.ConnectionResult;
import migrator.app.database.DatabaseContainer;
import migrator.app.database.DatabaseStructure;
import migrator.app.database.DatabaseStructureFactory;
import migrator.app.domain.connection.model.Connection;
import migrator.app.domain.project.model.Project;

public class ProjectConnectionValidator {
    private DatabaseContainer databaseContainer;

    public ProjectConnectionValidator(DatabaseContainer databaseContainer) {
        this.databaseContainer = databaseContainer;
    }

    public ValidationResult validate(Project project) {
        Connection connection = project.getDatabase().getConnection();
        DatabaseStructureFactory dbStrucutreFactory = this.databaseContainer.getStructureFactoryFor(connection.getDriver());
        DatabaseStructure dbStrucutre = dbStrucutreFactory.create(
            project.getDatabase().getUrl(),
            connection.getUser(),
            connection.getPassword()
        );
        ConnectionResult<?> connectionResult = dbStrucutre.testConnection();
        if (connectionResult.isOk()) {
            return new ValidationResult("");
        }
        return new ValidationResult(connectionResult.getError());
    }
}