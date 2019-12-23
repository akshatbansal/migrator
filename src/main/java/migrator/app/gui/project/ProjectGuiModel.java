package migrator.app.gui.project;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.app.gui.GuiModel;
import migrator.app.project.property.ProjectProperty;

public class ProjectGuiModel extends GuiModel {
    protected StringProperty host;
    protected StringProperty port;
    protected StringProperty database;
    protected ProjectProperty projectProperty;

    public ProjectGuiModel(ProjectProperty projectProperty) {
        this.projectProperty = projectProperty;
        String url = this.projectProperty.getDatabase().urlProperty().get();
        this.host = new SimpleStringProperty(
            this.parseHost(url)
        );
        this.port = new SimpleStringProperty(
            this.parsePort(url)
        );
        this.database = new SimpleStringProperty(
            this.parseDatabase(url)
        );

        this.host.addListener((observable, oldValue, newValue) -> {
            this.projectProperty.getDatabase().urlProperty().set(
                this.generateUrl()
            );
        });
        this.port.addListener((observable, oldValue, newValue) -> {
            this.projectProperty.getDatabase().urlProperty().set(
                this.generateUrl()
            );
        });
        this.database.addListener((observable, oldValue, newValue) -> {
            this.projectProperty.getDatabase().urlProperty().set(
                this.generateUrl()
            );
        });
    }

    public ProjectProperty getProjectProperty() {
        return this.projectProperty;
    }

    public StringProperty hostProperty() {
        return this.host;
    }

    public StringProperty portProperty() {
        return this.port;
    }

    public StringProperty databaseProperty() {
        return this.database;
    }

    private String generateUrl() {
        return this.projectProperty.getDatabase().driverProperty().get() + "://" + this.host.get() + ":" + this.port.get() + "/" + this.database.get();
    }

    private String parseHost(String url) {
        Pattern p = Pattern.compile("://(.+):");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }

    private String parsePort(String url) {
        Pattern p = Pattern.compile(":(\\d+)/");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }
    private String parseDatabase(String url) {
        Pattern p = Pattern.compile(":\\d+/(.+)");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group(1);
        }
        
        return "";
    }
}