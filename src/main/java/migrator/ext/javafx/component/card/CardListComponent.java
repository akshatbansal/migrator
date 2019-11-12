package migrator.ext.javafx.component.card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class CardListComponent<T> extends ViewComponent {
    protected ObservableList<T> list;
    protected ViewLoader viewLoader;
    protected CardComponentFactory<T> cardComponentFactory;

    @FXML protected FlowPane pane;

    public CardListComponent(ObservableList<T> list, CardComponentFactory<T> cardComponentFactory, ViewLoader viewLoader) {
        super(viewLoader);
        this.list = list;
        this.cardComponentFactory = cardComponentFactory;
        this.viewLoader = viewLoader;

        this.loadView("/layout/component/cardlist.fxml");

        this.list.addListener((Change<? extends T> change) -> {
            this.render();
        });
        this.render();
    }

    protected void render() {
        List<Node> childrens = new ArrayList<>();
        this.pane.getChildren().clear();
        Iterator<T> iterator = this.list.iterator();
        while (iterator.hasNext()) {
            T value = iterator.next();
            CardComponent<T> cardComponent = this.cardComponentFactory.create(value);
            childrens.add((Node) cardComponent.getContent());
        }
        this.pane.getChildren().setAll(childrens);
    }
}