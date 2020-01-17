package migrator.app.gui.component.toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.service.toast.Toast;

public class ToastListComponent extends SimpleComponent implements Component {
    protected ObservableList<Toast> list;

    @FXML protected VBox pane;

    public ToastListComponent() {
        super();

        this.loadFxml("/layout/toast/list.fxml");        
    }

    public void bind(ObservableList<Toast> toasts) {
        this.list = toasts;
        this.list.addListener((Change<? extends Toast> change) -> {
            this.render();
        });
        this.render();
    }

    protected void render() {
        Platform.runLater(() -> {
            List<Node> childrens = new ArrayList<>();
            this.pane.getChildren().clear();
            Iterator<Toast> iterator = this.list.iterator();
            while (iterator.hasNext()) {
                Toast value = iterator.next();
                ToastComponent toastComponent = new ToastComponent();
                toastComponent.bind(value);
                toastComponent.outputs().addListener((observable, oldValue, newValue) -> {
                    this.events.set(newValue);
                });
                childrens.add(toastComponent.getNode());
            }
            this.pane.getChildren().setAll(childrens);
        });
    }
}