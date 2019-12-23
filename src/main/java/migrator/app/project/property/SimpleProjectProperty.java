package migrator.app.project.property;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleProjectProperty implements ProjectProperty {
    protected String id;
    protected StringProperty name;
    protected StringProperty folder;
    protected StringProperty output;
    protected DatabaseProperty database;

    public SimpleProjectProperty(
        String id,
        String name,
        String folder,
        String output,
        DatabaseProperty database
    ) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.folder = new SimpleStringProperty(folder);
        this.output = new SimpleStringProperty(output);
        this.database = database;
    }

    @Override
    public String getUniqueKey() {
        return this.id;
    }

    @Override
    public StringProperty folderProperty() {
        return this.folder;
    }

    @Override
    public DatabaseProperty getDatabase() {
        return this.database;
    }

    @Override
    public StringProperty nameProperty() {
        return this.name;
    }

    @Override
    public StringProperty outputProperty() {
        return this.output;
    }
}