package migrator.app.migration.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChangeCommand implements Serializable {
    private static final long serialVersionUID = 5024506227307450694L;
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String NONE = "";

    protected transient StringProperty name;

    public ChangeCommand() {
        this(ChangeCommand.NONE);
    }

    public ChangeCommand(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public Boolean isType(String name) {
        return this.name.get().equals(name);
    }

    public StringProperty typeProperty() {
        return this.name;
    }

    public String getType() {
        return this.name.get();
    }

    public void setType(String type) {
        this.name.set(type);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(this.name.get());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.name = new SimpleStringProperty(s.readUTF());
    }
}