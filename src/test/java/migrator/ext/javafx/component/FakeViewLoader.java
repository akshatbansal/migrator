package migrator.ext.javafx.component;

import javafx.scene.Node;
import migrator.ext.javafx.component.ViewLoader;

public class FakeViewLoader extends ViewLoader {
    @Override
    public Node load(Object controller, String viewLocation) {
        return null;
    }
}