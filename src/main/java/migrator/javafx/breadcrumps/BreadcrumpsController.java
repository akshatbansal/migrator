package migrator.javafx.breadcrumps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import migrator.breadcrumps.Breadcrump;
import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.javafx.helpers.ControllerHelper;

public class BreadcrumpsController implements BreadcrumpsComponent {
    protected Node node;
    protected BreadcrumpsService breadcrumpsService;
    @FXML protected FlowPane breadcrumps;

    public BreadcrumpsController(BreadcrumpsService breadcrumpsService) {
        this.breadcrumpsService = breadcrumpsService;
        this.node = ControllerHelper.createViewNode(this, "/layout/breadcrumps/index.fxml");

        this.breadcrumpsService.getList().addListener((Change<? extends Breadcrump> change) -> {
            this.draw();
        });
    }

    protected void draw() {
        Image arrowImage =  new Image(this.getClass().getResource("/images/arrow_right_black.png").toExternalForm());
        
        List<Node> elements = new ArrayList<>();
        Iterator<Breadcrump> iterator = this.breadcrumpsService.getList().iterator();
        while (iterator.hasNext()) {
            Breadcrump b = iterator.next();
            Button button = new Button(b.getName());
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

    @Override
    public Object getContent() {
        return this.node;
    }

    @FXML 
    public void initialize() {
        this.draw();
    }
}