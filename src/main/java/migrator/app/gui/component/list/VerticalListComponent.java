package migrator.app.gui.component.list;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;

public class VerticalListComponent<T extends Component> extends SimpleComponent implements Component {
    private ObservableList<T> list;

    @FXML protected VBox pane;

    public VerticalListComponent() {
        super();

        this.loadFxml("/layout/component/verticallist.fxml");
    }

    public void bind(ObservableList<T> list) {
        this.list = list;
        list.addListener((Change<? extends T> change) -> {
            this.render();
        });
        this.render();
    }

    public void clearSpacing() {
        this.pane.setSpacing(0);
    }

    private void render() {
        List<Node> nodes = new LinkedList<>();
        for (T component : this.list) {
            nodes.add(component.getNode());
        }
        this.pane.getChildren().setAll(nodes);
    }
}