package migrator.ext.javafx.toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import migrator.app.toast.ToastService;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;
import migrator.app.toast.Toast;

public class ToastListComponent extends ViewComponent {
    protected ObservableList<Toast> list;
    protected ToastService toastService;
    protected ViewLoader viewLoader;

    @FXML protected VBox pane;

    public ToastListComponent(ToastService toastService, ViewLoader viewLoader) {
        super(viewLoader);
        this.toastService = toastService;
        this.list = toastService.getList();
        this.viewLoader = viewLoader;

        this.loadView("/layout/toast/list.fxml");

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
                ToastComponent toastComponent = new ToastComponent(value, this.toastService , this.viewLoader);
                childrens.add((Node) toastComponent.getContent());
            }
            this.pane.getChildren().setAll(childrens);
        });
    }
}