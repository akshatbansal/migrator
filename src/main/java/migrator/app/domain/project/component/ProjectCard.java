package migrator.app.domain.project.component;

import migrator.app.domain.project.model.Project;
import migrator.app.gui.GuiNode;
import migrator.lib.emitter.Subscriber;
import migrator.lib.emitter.Subscription;

public interface ProjectCard extends GuiNode {
    public Subscription<Project> onSelect(Subscriber<Project> subscriber);
    public Subscription<Project> onOpen(Subscriber<Project> subscriber);
}