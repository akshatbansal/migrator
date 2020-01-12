package migrator.app.gui.component.mark;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;

public class MarkComponent extends SimpleComponent implements Component {
    @FXML protected HBox pane;

    public MarkComponent(String changeType) {
        super();
        this.loadFxml("/layout/component/mark.fxml");
        this.pane.getStyleClass().add("marks__square--" + changeType);
    }
}