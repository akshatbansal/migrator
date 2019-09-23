package migrator.mock;

import javafx.scene.Node;
import migrator.javafx.helpers.View;

public class FakeView implements View {
    @Override
    public Node createFromFxml(Object controller, String fxmlView) {
        return null;
    }
}