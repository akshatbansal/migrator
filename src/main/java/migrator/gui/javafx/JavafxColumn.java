package migrator.gui.javafx;

import migrator.gui.Alignment;
import migrator.gui.GuiNode;
import migrator.gui.Section;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class JavafxColumn implements Section {
    protected VBox vbox;

    public JavafxColumn(Alignment alignment) {
        this.vbox = new VBox();
        this.setAlignment(alignment);
    }

    @Override
    public void addChild(GuiNode child) {
        this.vbox.getChildren().add((Node) child.getContent()); 
    }

    @Override
    public Node getContent() {
        return this.vbox;
    }

    @Override
    public void setAlignment(Alignment alignment) {
        this.vbox.setAlignment(Pos.BASELINE_LEFT);
    }

    @Override
    public void setWidth(Double width) {
        this.vbox.setPrefWidth(width);
    }

    @Override
    public void growChild(GuiNode child) {
        this.addChild(child);
        VBox.setVgrow((Node) child.getContent(), Priority.ALWAYS);
    }
}