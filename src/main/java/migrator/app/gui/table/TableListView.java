package migrator.app.gui.table;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.card.SimpleCardComponentFactory;
import migrator.ext.javafx.UseCase;
import migrator.ext.javafx.component.ViewLoader;

public class TableListView {
    protected Node node;
    protected TableController controller;

    @FXML protected VBox tableCards;

    public TableListView(TableController controller) {
        // this.loadingIndicator = loadingIndicator;
        this.controller = controller;
        ViewLoader viewLoader = new ViewLoader();

        CardListComponent<TableGuiModel> cards = new CardListComponent<>(
            controller.getList(),
            new TableCardComponentFactory()
        );

        this.node = viewLoader.load(this, "/layout/table/index.fxml");
        this.tableCards.getChildren().addAll(
            cards.getNode()
        );

        cards.event("edit").addListener((observable, oldValue, newValue) -> {
            System.out.println("EDIT");
            // this.controller.open(newValue.getValue());
        });
    }

    @FXML public void addTable() {
        // this.controller.create("new_project");
        // List<ProjectGuiModel> projects = this.controller.getList();
        // this.controller.select(projects.get(projects.size() - 1));
    }

    @FXML public void commit() {

    }

    public void openTable(TableGuiModel table) {
        UseCase.runOnThread(() -> {
            // this.loadingIndicator.start();
            // this.controller.open(project);
            // this.loadingIndicator.stop();
        });
    }

    public Node getNode() {
        return this.node;
    }
}