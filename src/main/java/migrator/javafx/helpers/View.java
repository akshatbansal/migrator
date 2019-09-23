package migrator.javafx.helpers;

import javafx.scene.Node;

public interface View {
    public Node createFromFxml(Object controller, String fxmlView); 
}