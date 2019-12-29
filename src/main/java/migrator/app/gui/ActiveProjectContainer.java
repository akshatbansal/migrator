package migrator.app.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import migrator.app.project.ProjectContainer;

public class ActiveProjectContainer {
    protected ObjectProperty<ProjectContainer> project;

    public ActiveProjectContainer() {
        this.project = new SimpleObjectProperty<>();
    }

    public ObjectProperty<ProjectContainer> projectProperty() {
        return this.project;
    }

    public void activate(ProjectContainer project) {
        this.project.set(project);
    }

    public void deactivate() {
        this.project.set(null);
    }
}