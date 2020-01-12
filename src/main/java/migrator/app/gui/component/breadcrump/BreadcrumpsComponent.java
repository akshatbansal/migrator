package migrator.app.gui.component.breadcrump;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;

public class BreadcrumpsComponent extends SimpleComponent implements Component {
    protected ObservableList<Breadcrump> breadcrumpsList;

    @FXML protected FlowPane breadcrumps;

    public BreadcrumpsComponent() {
        super();
        this.loadFxml("/layout/breadcrumps/index.fxml");
    }

    public void bind(ObservableList<Breadcrump> list) {
        this.breadcrumpsList = list;
        this.breadcrumpsList.addListener((Change<? extends Breadcrump> change) -> {
            this.draw();
        });
        this.draw();
    }

    protected void draw() {
        Image arrowImage =  new Image(this.getClass().getResource("/images/arrow_right_black.png").toExternalForm());
        
        List<Node> elements = new ArrayList<>();
        Iterator<Breadcrump> iterator = this.breadcrumpsList.iterator();
        while (iterator.hasNext()) {
            Breadcrump b = iterator.next();
            Button button = new Button();
            button.textProperty().bind(b.nameProperty());
            button.setMnemonicParsing(false);
            button.getStyleClass().add("btn-link");
            button.setOnAction((ActionEvent event) -> {
                b.link();
            });
            elements.add(button);
            if (iterator.hasNext()) {
                elements.add(new ImageView(arrowImage));
            }
        } 

        this.breadcrumps.getChildren().setAll(elements);
        if (this.breadcrumps.getChildren().size() > 0) {
            this.breadcrumps.getChildren().get(this.breadcrumps.getChildren().size() - 1).getStyleClass().add("btn-link--active");
        }
    }
}