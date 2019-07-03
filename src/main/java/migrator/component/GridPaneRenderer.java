package migrator.component;

import java.util.LinkedList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridPaneRenderer implements Renderer<List<Node>> {
    protected GridPane pane;
    protected Integer rowIndex;
    protected List<Node> shown;

    public GridPaneRenderer(GridPane pane, Integer rowIndex) {
        this.pane = pane;
        this.rowIndex = rowIndex;
        this.shown = new LinkedList<>();
    }

    @Override
    public void show(List<Node> list) {
        for (Integer i = 0; i < list.size(); i++) {
            this.pane.add(list.get(i), i, this.rowIndex);            
        }
        this.shown.addAll(list);
    }

    @Override
    public void hide() {
        this.pane.getChildren().removeAll(this.shown);
        this.shown = new LinkedList<>();
    }
}