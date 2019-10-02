package migrator.table.model;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChangeStringPropertyListener implements ChangeListener<String> {
    protected StringProperty original;
    protected StringProperty change;

    public ChangeStringPropertyListener(StringProperty original, StringProperty change) {
        this.original = original;
        this.change = change;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue.equals(this.original.get())) {
            this.change.set(null);
        } else {
            this.change.set(newValue);
        }
    }
}