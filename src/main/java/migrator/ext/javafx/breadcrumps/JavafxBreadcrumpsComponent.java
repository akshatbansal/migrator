package migrator.ext.javafx.breadcrumps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.ext.javafx.component.ViewComponent;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxBreadcrumpsComponent extends ViewComponent implements BreadcrumpsComponent {
    protected List<Breadcrump> breadcrumpsList;

    @FXML protected FlowPane breadcrumps;

    public JavafxBreadcrumpsComponent(List<Breadcrump> breadcrumpsList, ViewLoader viewLoader) {
        super(viewLoader);
        this.breadcrumpsList = breadcrumpsList;

        this.loadView("/layout/breadcrumps/index.fxml");
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

    @FXML 
    public void initialize() {
        this.draw();
    }
}