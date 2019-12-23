package migrator.app.gui.project;

import migrator.app.project.property.ProjectProperty;
import migrator.lib.adapter.Adapter;

public class ProjectGuiAdapter implements Adapter<ProjectGuiModel, ProjectProperty> {
    @Override
    public ProjectGuiModel concretize(ProjectProperty item) {
        return new ProjectGuiModel(item);
    }

    @Override
    public ProjectProperty generalize(ProjectGuiModel item) {
        return item.getProjectProperty();
    }
}