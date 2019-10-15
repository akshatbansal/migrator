package migrator.app.domain.project.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.database.model.DatabaseConnection;

public class Project {
    protected DatabaseConnection databaseConnection;
    protected StringProperty name;
    protected StringProperty outputType;
    protected StringProperty folder;
    protected String id;

    public Project(DatabaseConnection databaseConnection, String id, String name, String outputType, String folder) {
        this.databaseConnection = databaseConnection;
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.outputType = new SimpleStringProperty(outputType);
        this.folder = new SimpleStringProperty(folder);
    }

    public String getId() {
        return this.id;
    }

    public DatabaseConnection getDatabase() {
        return this.databaseConnection;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public String getName() {
        return this.nameProperty().get();
    }

    public StringProperty outputTypeProperty() {
        return this.outputType;
    }

    public String getOutputType() {
        return this.outputTypeProperty().get();
    }

    public StringProperty folderProperty() {
        return this.folder;
    }

    public String getFolder() {
        return this.folderProperty().get();
    }
}