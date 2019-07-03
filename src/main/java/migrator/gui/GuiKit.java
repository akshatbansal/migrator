package migrator.gui;

import java.util.List;

import migrator.emitter.Subscriber;

public interface GuiKit {
    public PrimaryButton createPrimaryButton(String text);
    public SecondaryButton createSecondaryButton(String text);
    public Title createTitle(String text);
    public Window createWindow(String title, Integer width, Integer height);
    public Section createRow(Alignment alignment);
    public Section createColumn(Alignment alignment);
    public StringInput createStringInput(String value);
    public Label createLabel(String text);
    public SelectInput<Object> createSelect(List<Object> options, Object value);
    public Line drawLine(Double startX, Double startY, Double endX, Double endY);
    public Dimensions getDimensionsFor(GuiNode node);
    public Password createPasswordInput(String value);
    public Form createForm();
}