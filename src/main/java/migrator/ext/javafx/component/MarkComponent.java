package migrator.ext.javafx.component;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class MarkComponent extends ViewComponent {
    @FXML protected HBox pane;

    public MarkComponent(ViewLoader viewLoader, String changeType) {
        super(viewLoader);

        this.loadView("/layout/component/mark.fxml");

        this.pane.getStyleClass().add("marks__square--" + changeType);
    }
}