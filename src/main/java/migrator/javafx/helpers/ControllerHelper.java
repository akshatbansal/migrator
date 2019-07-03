package migrator.javafx.helpers;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import migrator.gui.GuiNode;

public class ControllerHelper {
    public static Node createViewNode(Object controller, String view) {
        FXMLLoader loader = new FXMLLoader(controller.getClass().getResource(view));
        loader.setController(controller);
        try {
            return loader.load();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            return null;
        }   
    }

    public static void replaceNode(Pane pane, GuiNode guiNode) {
        pane.getChildren().setAll((Node) guiNode.getContent());
    }

    public static void addNode(Pane pane, GuiNode guiNode) {
        pane.getChildren().add((Node) guiNode.getContent());
    }
}