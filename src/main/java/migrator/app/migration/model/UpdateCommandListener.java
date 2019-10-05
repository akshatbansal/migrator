package migrator.app.migration.model;

import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class UpdateCommandListener implements ChangeListener<Object> {
    protected ChangeCommand changeCommand;
    protected List<Property<? extends Object>> properties;

    public UpdateCommandListener(ChangeCommand changeCommand, List<Property<? extends Object>> properties) {
        this.changeCommand = changeCommand;
        this.properties = properties;
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        if (this.changeCommand.isType(ChangeCommand.DELETE)) {
            return;
        }
        if (this.changeCommand.isType(ChangeCommand.CREATE)) {
            return;
        }
        for (Property<? extends Object> property : this.properties) {
            if (property.getValue() != null) {
                this.changeCommand.typeProperty().set(ChangeCommand.UPDATE);
                return;
            }
        }
        this.changeCommand.typeProperty().set(ChangeCommand.NONE);
    }
}