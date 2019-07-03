package migrator.connection.model;

import java.io.Serializable;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import migrator.common.Extractable;

public class Connection implements Serializable, Extractable {
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
        this.name = new SimpleStringProperty(name);
        this.user = new SimpleStringProperty(user);
        this.password = new SimpleStringProperty(password);
        this.host = new SimpleStringProperty("");
        this.port = new SimpleStringProperty("");
        this.driver = new SimpleStringProperty("");
        this.url = new SimpleStringProperty("");
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

    // private void writeObject(ObjectOutputStream s) throws IOException {
    //     s.defaultWriteObject();
    //     s.writeUTF(this.url.get());
    //     s.writeUTF(this.name.get());
    // }

    // private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        // s.defaultReadObject();
    //     this.name.set(s.readUTF());
    //     this.url.set(s.readUTF());
    // }

    @Override
    public Observable[] extract() {
        return new Observable[]{this.name, this.url};
    }
}