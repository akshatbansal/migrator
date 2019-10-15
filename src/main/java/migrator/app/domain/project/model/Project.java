package migrator.app.domain.project.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.domain.database.model.DatabaseConnection;

public class Project implements Serializable {
    private static final long serialVersionUID = 8438644134414504238L;
    protected DatabaseConnection databaseConnection;
    protected transient StringProperty name;
    protected transient StringProperty outputType;
    protected transient StringProperty folder;
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

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(this.name.get());
        s.writeUTF(this.outputType.get());
        s.writeUTF(this.folder.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.name = new SimpleStringProperty(s.readUTF());
        this.outputType = new SimpleStringProperty(s.readUTF());
        this.folder = new SimpleStringProperty(s.readUTF());
    }
}