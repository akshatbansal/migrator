package migrator.app.domain.connection.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Connection implements Serializable {
    private static final long serialVersionUID = -9103144481928774650L;
    protected transient StringProperty name;
    protected transient StringProperty user;
    protected transient StringProperty password;
    protected transient StringProperty host;
    protected transient StringProperty port;
    protected transient StringProperty driver;
    protected transient StringProperty url;

    public Connection(String name) {
        this(name, "root", "");
    }

    public Connection(String name, String user, String password) {
        this(name, user, password, "localhost", "3306", "mysql");
    }

    public Connection(String name, String user, String password, String host, String port, String driver) {
        this.name = new SimpleStringProperty(name);
        this.user = new SimpleStringProperty(user);
        this.password = new SimpleStringProperty(password);
        this.host = new SimpleStringProperty(host);
        this.port = new SimpleStringProperty(port);
        this.driver = new SimpleStringProperty(driver);
        this.url = new SimpleStringProperty("");
        this.initialize();
    }

    protected void initialize() {
        this.url.bind(Bindings.concat(this.driver, "://", this.host, ":", this.port));
    }

    public StringProperty urlProperty() {
        return this.url;
    }
    public String getUrl() {
        return this.url.get();
    }

    public String getName() {
        return this.name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public StringProperty nameProperty() {
        return this.name;
    }

    public String getUser() {
        return this.user.get();
    }
    public void setUser(String value) {
        this.user.set(value);
    }
    public StringProperty userProperty() {
        return this.user;
    }

    public String getPassword() {
        return this.password.get();
    }
    public void setPassword(String value) {
        this.password.set(value);
    }
    public StringProperty passwordProperty() {
        return this.password;
    }

    public String getPort() {
        return this.port.get();
    }
    public void setPort(String value) {
        this.port.set(value);
    }
    public StringProperty portProperty() {
        return this.port;
    }

    public String getDriver() {
        return this.driver.get();
    }
    public void setDriver(String value) {
        this.driver.set(value);
    }
    public StringProperty driverProperty() {
        return this.driver;
    }

    public String getHost() {
        return this.host.get();
    }
    public void setHost(String value) {
        this.host.set(value);
    }
    public StringProperty hostProperty() {
        return this.host;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(this.name.get());
        s.writeUTF(this.host.get());
        s.writeUTF(this.port.get());
        s.writeUTF(this.driver.get());
        s.writeUTF(this.user.get());
        s.writeUTF(this.password.get());

        s.writeUTF(this.url.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.name = new SimpleStringProperty(s.readUTF());
        this.host = new SimpleStringProperty(s.readUTF());
        this.port = new SimpleStringProperty(s.readUTF());
        this.driver = new SimpleStringProperty(s.readUTF());
        this.user = new SimpleStringProperty(s.readUTF());
        this.password = new SimpleStringProperty(s.readUTF());

        this.url = new SimpleStringProperty(s.readUTF());
        this.initialize();
    }
}