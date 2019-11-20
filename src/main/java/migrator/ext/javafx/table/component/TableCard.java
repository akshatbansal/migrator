package migrator.ext.javafx.table.component;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableActivator;
import migrator.app.gui.GuiNode;
import migrator.app.migration.model.ChangeCommand;
import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.ext.javafx.component.MarkComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.component.card.CardComponent;

public class TableCard extends ViewComponent implements CardComponent<Table> {
    protected static final Integer MARKS_LINE_LIMIT = 8;

    protected Table table;
    protected TableActivator tableActivator;
    
    @FXML protected Text name;
    @FXML protected HBox buttonBox;
    @FXML protected VBox pane;
    @FXML protected HBox marksTop;
    @FXML protected HBox marksBottom;

    public TableCard(Table table, ViewLoader viewLoader, TableActivator tableActivator) {
        super(viewLoader);
        this.table = table;
        this.tableActivator = tableActivator;

        this.loadView("/layout/table/card.fxml");

        this.name.textProperty().bind(this.table.nameProperty());

        for (ColumnChange columnChange : this.table.getColumnsChanges()) {
            if (columnChange.getCommand().isType(ChangeCommand.NONE)) {
                continue;
            }
            this.addMark(
                new MarkComponent(viewLoader, columnChange.getCommand().getType())
            );
        }
        for (IndexChange IndexChange : this.table.getIndexesChanges()) {
            if (IndexChange.getCommand().isType(ChangeCommand.NONE)) {
                continue;
            }
            this.addMark(
                new MarkComponent(viewLoader, IndexChange.getCommand().getType())
            );
        }

        this.table.getChangeCommand().typeProperty().addListener((obs, oldValue, newValue) -> {
            this.setChanged(oldValue, newValue);
        });

        this.setChanged("", this.table.getChangeCommand().getType());
    }

    protected void addMark(GuiNode node) {
        if (this.marksTop.getChildren().size() >= MARKS_LINE_LIMIT) {
            return;
        }
        if (this.marksBottom.getChildren().size() == this.marksTop.getChildren().size()) {
            this.marksBottom.getChildren().add(
                (Node) node.getContent()
            );
        } else {
            this.marksTop.getChildren().add(
                (Node) node.getContent()
            );
        }
    }

    @Override
    public Table getValue() {
        return this.table;
    }

    @FXML public void edit() {
        this.tableActivator.activateTable(this.table);
    }

    protected void setChanged(String oldValue, String newValue) {
        if (!oldValue.isEmpty()) {
            this.pane.getStyleClass().remove("card--" + oldValue);
        }
        if (!newValue.isEmpty()) {
            this.pane.getStyleClass().add("card--" + newValue);
        }
    }
}