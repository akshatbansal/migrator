package migrator.app.gui.component.table;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.model.Table;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.component.card.CardComponent;
import migrator.app.gui.component.mark.MarkComponent;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.lib.dispatcher.SimpleEvent;

public class TableCardComponent extends SimpleComponent implements CardComponent<Table> {
    protected static final Integer MARKS_LINE_LIMIT = 8;

    protected Table table;
    private ChangeListener<String> changeCommandListener;
    private ListChangeListener<IndexChange> indexListListener;
    private ListChangeListener<ColumnChange> columnListListener;
    
    @FXML protected Text name;
    @FXML protected HBox buttonBox;
    @FXML protected VBox pane;
    @FXML protected HBox marksTop;
    @FXML protected HBox marksBottom;

    public TableCardComponent() {
        super();

        this.changeCommandListener = (observable, oldValue, newValue) -> {
            System.out.println("command");
            this.onInnerChange();
        };
        this.indexListListener = (Change<? extends IndexChange> change) -> {
            System.out.println("index");
            this.onInnerChange();
        };
        this.columnListListener = (Change<? extends ColumnChange> change) -> {
            System.out.println("column");
            this.onInnerChange();
        };

        this.loadFxml("/layout/table/card.fxml");
    }

    @Override
    public void bind(ObjectProperty<Table> item) {
        this.table = item.get();

        this.name.textProperty().bind(this.table.nameProperty());

        this.table.getColumnsChanges().addListener(this.columnListListener);
        for (ColumnChange columnChange : this.table.getColumnsChanges()) {
            columnChange.getCommand().typeProperty().addListener(this.changeCommandListener);
        }
        this.table.getIndexesChanges().addListener(this.indexListListener);
        for (IndexChange indexChange : this.table.getIndexesChanges()) {
            indexChange.getCommand().typeProperty().addListener(this.changeCommandListener);
        }

        this.table.getChangeCommand().typeProperty().addListener(this.changeCommandListener);
        this.onInnerChange();

        this.setChanged("", this.table.getChangeCommand().getType());
    }

    protected void addMark(Component mark) {
        if (this.marksTop.getChildren().size() >= MARKS_LINE_LIMIT) {
            return;
        }
        if (this.marksBottom.getChildren().size() == this.marksTop.getChildren().size()) {
            this.marksBottom.getChildren().add(
                mark.getNode()
            );
        } else {
            this.marksTop.getChildren().add(
                mark.getNode()
            );
        }
    }

    @Override
    public Table getValue() {
        return this.table;
    }

    @FXML
    public void edit() {
        this.output(new SimpleEvent<>("edit", this.table));
    }

    protected void setChanged(String oldValue, String newValue) {
        if (!oldValue.isEmpty()) {
            this.pane.getStyleClass().remove("card--" + oldValue);
        }
        if (!newValue.isEmpty()) {
            this.pane.getStyleClass().add("card--" + newValue);
        }
    }

    private void onInnerChange() {
        Platform.runLater(() -> {
            this.marksTop.getChildren().clear();
            this.marksBottom.getChildren().clear();
            for (ColumnChange columnChange : this.table.getColumnsChanges()) {
                if (columnChange.getCommand().isType(ChangeCommand.NONE)) {
                    continue;
                }
                this.addMark(
                    new MarkComponent(columnChange.getCommand().getType())
                );
            }
            for (IndexChange IndexChange : this.table.getIndexesChanges()) {
                if (IndexChange.getCommand().isType(ChangeCommand.NONE)) {
                    continue;
                }
                this.addMark(
                    new MarkComponent(IndexChange.getCommand().getType())
                );
            }
        });
    }

    @Override
    public void destroy() {
        this.table.getColumnsChanges().removeListener(this.columnListListener);
        for (ColumnChange columnChange : this.table.getColumnsChanges()) {
            columnChange.getCommand().typeProperty().removeListener(this.changeCommandListener);
        }
        this.table.getIndexesChanges().removeListener(this.indexListListener);
        for (IndexChange indexChange : this.table.getIndexesChanges()) {
            indexChange.getCommand().typeProperty().removeListener(this.changeCommandListener);
        }

        this.table.getChangeCommand().typeProperty().removeListener(this.changeCommandListener);
    }
}