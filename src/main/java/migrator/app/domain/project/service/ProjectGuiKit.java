package migrator.app.domain.project.service;

import migrator.app.domain.project.component.CommitForm;
import migrator.app.domain.project.component.CommitView;
import migrator.app.domain.project.component.ProjectForm;
import migrator.app.domain.project.component.ProjectList;

public interface ProjectGuiKit {
    public ProjectForm createForm();
    public ProjectList createList();
    public CommitForm createCommitForm();
    public CommitView createCommitView();
}