package migrator.app.project.property;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SimpleDatabaseProperty implements DatabaseProperty {
    protected StringProperty url;
    protected StringProperty user;
    protected StringProperty password;
    protected StringProperty driver;

    public SimpleDatabaseProperty(
        String url,
        String user,
        String password,
        String driver
    ) {
        this.driver = new SimpleStringProperty(driver);
        this.user = new SimpleStringProperty(user);
        this.password = new SimpleStringProperty(password);
        this.url = new SimpleStringProperty(url);
    }

    @Override
    public StringProperty driverProperty() {
        return this.driver;
    }

    @Override
    public StringProperty passwordProperty() {
        return this.password;
    }

    @Override
    public StringProperty urlProperty() {
        return this.url;
    }

    @Override
    public StringProperty userProperty() {
        return this.user;
    }
}