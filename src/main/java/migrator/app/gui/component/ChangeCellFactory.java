package migrator.app.gui.component;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ChangeCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public ChangeCellFactory() {
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                this.onTypeChange((String) item);
            }

            protected void onTypeChange(String newValue) {
                this.getStyleClass().removeAll("cell--create", "cell--update", "cell--delete");
                if (newValue == null) {
                    return;
                }
                this.getStyleClass().add("cell--" + newValue);
            }
        };
        return cell;
    }
}