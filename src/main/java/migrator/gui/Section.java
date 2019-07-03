package migrator.gui;

public interface Section extends GuiNode {
    public void addChild(GuiNode child);
    public void growChild(GuiNode child);
    public void setAlignment(Alignment alignment);
    public void setWidth(Double width);
}