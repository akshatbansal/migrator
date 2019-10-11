package migrator.ext.javafx.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import migrator.app.toast.ToastService;
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
        List<Node> childrens = new ArrayList<>();
        this.pane.getChildren().clear();
        Iterator<Toast> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            Toast value = iterator.next();
            ToastComponent toastComponent = new ToastComponent(value, this.toastService , this.viewLoader);
            childrens.add((Node) toastComponent.getContent());
        }
        this.pane.getChildren().setAll(childrens);
    }
}