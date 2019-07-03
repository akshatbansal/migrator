package migrator.gui.javafx;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import migrator.gui.Alignment;
import migrator.gui.GuiNode;
import migrator.gui.Section;

public class JavafxRow implements Section {
    protected HBox hBox;

    public JavafxRow(Alignment alignment) {
        this.hBox = new HBox();
        this.setAlignment(alignment);
    }
    
    @Override
    public void addChild(GuiNode child) {
        this.hBox.getChildren().add((Node) child.getContent());
    }

    @Override
    public Node getContent() {
        return this.hBox;
    }

    @Override
    public void setAlignment(Alignment alignment) {
        this.hBox.setAlignment(Pos.BASELINE_LEFT);
    }

    @Override
    public void setWidth(Double width) {
        this.hBox.setPrefWidth(width);
    }

    @Override
    public void growChild(GuiNode child) {
        this.addChild(child);
        HBox.setHgrow((Node) child.getContent(), Priority.ALWAYS);
    }
}