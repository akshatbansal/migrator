package migrator.app.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GuiController<T extends GuiModel> {
    protected T selected;
    protected ObservableList<T> list;

    public GuiController() {
        this.list = FXCollections.observableArrayList();
    }

    public void select(T item) {
        this.deselect(this.selected);
        this.selected = item;
        this.selected.enable("selected");
    }

    public void deselect(T item) {
        if (item == null) {
            return;
        }
        item.disable("selected");
    }

    public ObservableList<T> getList() {
        return this.list;
    }
}