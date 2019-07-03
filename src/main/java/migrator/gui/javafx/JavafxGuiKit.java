package migrator.gui.javafx;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import migrator.gui.Alignment;
import migrator.gui.Dimensions;
import migrator.gui.Form;
import migrator.gui.GuiKit;
import migrator.gui.GuiNode;
import migrator.gui.Label;
import migrator.gui.Line;
import migrator.gui.Password;
import migrator.gui.PrimaryButton;
import migrator.gui.SecondaryButton;
import migrator.gui.Section;
import migrator.gui.SelectInput;
import migrator.gui.StringInput;
import migrator.gui.Title;
import migrator.gui.Window;

public class JavafxGuiKit implements GuiKit {
    @Override
    public PrimaryButton createPrimaryButton(String text) {
        return new JavafxPrimaryButton(text);
    }

    @Override
    public SecondaryButton createSecondaryButton(String text) {
        return new JavafxSecondaryButton(text);
    }

    @Override
    public Title createTitle(String text) {
        return new JavafxTitle(text);
    }

    @Override
    public Window createWindow(String title, Integer width, Integer height) {
        return new JavafxWindow(title, width, height);
    }

    @Override
    public Section createRow(Alignment alignment) {
        return new JavafxRow(alignment);
    }

    @Override
    public Section createColumn(Alignment alignment) {
        return new JavafxColumn(alignment);
    }

    @Override
    public StringInput createStringInput(String value) {
        return new JavafxStringInput(value);
    }

    @Override
    public Label createLabel(String text) {
        return new JavafxLabel(text);
    }

    @Override
    public SelectInput<Object> createSelect(List<Object> options, Object value) {
        return new JavafxSelectInput<Object>(options, value);
    }

    @Override
    public Line drawLine(Double startX, Double startY, Double endX, Double endY) {
        return new JavafxLine(startX, startY, endX, endY);
    }

    @Override
    public Dimensions getDimensionsFor(GuiNode node) {
        return new JavafxDimensions((Node) node.getContent());
    }

    @Override
    public Password createPasswordInput(String value) {
        return new JavafxPassword(value);
    }

    @Override
    public Form createForm() {
        return new JavafxForm(this.createColumn(Alignment.START));
    }
}