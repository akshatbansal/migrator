package migrator.javafx.helpers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class ResourceView implements View {
    @Override
    public Node createFromFxml(Object controller, String fxmlView) {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(fxmlView));
        loader.setController(controller);
        try {
            return loader.load();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }
}