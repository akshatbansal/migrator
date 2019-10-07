package migrator.app.domain.project.component;

import migrator.app.gui.GuiNode;

public interface ProjectForm extends GuiNode {
    public void close();
    public void delete();
    public void open();
}