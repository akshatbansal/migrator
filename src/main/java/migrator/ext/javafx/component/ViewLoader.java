package migrator.ext.javafx.component;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class ViewLoader {
    public Node load(Object controller, String viewLocation) {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(viewLocation));
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