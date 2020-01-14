package migrator.app.gui.component.route;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import migrator.app.gui.component.Component;
import migrator.app.gui.component.SimpleComponent;
import migrator.app.gui.route.RouteView;
import migrator.app.gui.view.View;

public class RouterComponent extends SimpleComponent implements Component {
    private ObjectProperty<RouteView> activeView;
    private VBox pane;

    public RouterComponent() {
        super();
        this.pane = new VBox();
        VBox.setVgrow(this.pane, Priority.ALWAYS);
    }

    public void bind(ObjectProperty<RouteView> activeView) {
        this.activeView = activeView;
        this.activeView.addListener((observable, oldValue, newValue) -> {
            this.onViewChange(newValue.getView());
        });
        if (this.activeView.get() == null) {
            return;
        }
        this.onViewChange(this.activeView.get().getView());
    }

    protected void onViewChange(View view) {
        this.pane.getChildren().clear();
        if (view == null) {
            return;
        }
        this.pane.getChildren().setAll(view.getNode());
    }

    @Override
    public Node getNode() {
        return this.pane;
    }
}