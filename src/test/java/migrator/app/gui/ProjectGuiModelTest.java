package migrator.app.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import migrator.app.gui.project.ProjectGuiModel;
import migrator.app.project.property.SimpleDatabaseProperty;
import migrator.app.project.property.SimpleProjectProperty;

public class ProjectGuiModelTest {
    private ProjectGuiModel createWithUrl(String url) {
        return new ProjectGuiModel(
            new SimpleProjectProperty("", "project_name", "", "", new SimpleDatabaseProperty(url, "user", "", "mysql"))
        );
    }

    @Test public void construct__hostIpIsParsedFromUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://127.0.0.1:3306/database_name");

        String result = project.hostProperty().get();

        assertEquals("127.0.0.1", result);
    }

    @Test public void construct__hostDomainIsParsedFromUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        String result = project.hostProperty().get();

        assertEquals("localhost", result);
    }

    @Test public void construct__hostSecondLevelDomainIsParsedFromUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://domain.local:3306/database_name");

        String result = project.hostProperty().get();

        assertEquals("domain.local", result);
    }

    @Test public void construct__portIsParsedFromUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        String result = project.portProperty().get();

        assertEquals("3306", result);
    }

    @Test public void construct__databaseNameIsParsedFromUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        String result = project.databaseProperty().get();

        assertEquals("database_name", result);
    }

    @Test public void construct__databaseNameIsEmptyIfUrlEndsWithPort() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306");

        String result = project.databaseProperty().get();

        assertEquals("", result);
    }

    @Test public void construct__databaseNameIsEmptyIfUrlEndsWithPortAndBackslash() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/");

        String result = project.databaseProperty().get();

        assertEquals("", result);
    }

    @Test public void setHost__updatesProjectUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        project.hostProperty().set("127.0.0.1");
        String result = project.getProjectProperty().getDatabase().urlProperty().get();

        assertEquals("mysql://127.0.0.1:3306/database_name", result);
    }

    @Test public void setPort__updatesProjectUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        project.portProperty().set("3307");
        String result = project.getProjectProperty().getDatabase().urlProperty().get();

        assertEquals("mysql://localhost:3307/database_name", result);
    }

    @Test public void setDatabaseName__updatesProjectUrl() {
        ProjectGuiModel project = this.createWithUrl("mysql://localhost:3306/database_name");

        project.databaseProperty().set("db_name");
        String result = project.getProjectProperty().getDatabase().urlProperty().get();

        assertEquals("mysql://localhost:3306/db_name", result);
    }
}