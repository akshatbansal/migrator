package migrator.app.gui.component.card;

import javafx.beans.property.StringProperty;
import migrator.app.gui.GuiModel;

public class CardGuiModel<T> extends GuiModel {
    protected StringProperty name;
    protected StringProperty buttonPrimaryName;
    protected StringProperty buttonSecondaryName;
    protected T item;

    public CardGuiModel(
        T item,
        StringProperty name
    ) {
        this.item = item;
        this.name = name;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public StringProperty secondaryProperty() {
        return this.buttonSecondaryName;
    }

    public StringProperty primaryProperty() {
        return this.buttonPrimaryName;
    }

    public T getValue() {
        return this.item;
    }
}