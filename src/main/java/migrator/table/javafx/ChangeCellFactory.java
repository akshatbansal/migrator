package migrator.table.javafx;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import migrator.table.model.Column;

public class ChangeCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public ChangeCellFactory() {
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                Column column = (Column) getTableRow().getItem(); 
                if (column == null) {
                    return;
                }
                
                this.onTypeChange( column.getChange().getCommand().getType());
                setPrefWidth(4.0);

                column.getChange().getCommand().typeProperty().addListener((ObservableValue<? extends String> obs, String oldValue, String newValue) -> {
                    Column c = (Column) this.getTableRow().getItem();
                    this.onTypeChange(c.getChange().getCommand().getType());
                });
            }

            protected void onTypeChange(String newValue) {
                this.getStyleClass().removeAll("cell--create", "cell--update", "cell--delete");
                this.getStyleClass().add("cell--" + newValue);
            }
        };
        return cell;
    }
}