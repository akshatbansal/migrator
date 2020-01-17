package migrator.app.domain.modification.action;

import migrator.app.domain.project.model.Project;

public class CommitEventData {
    private Project project;
    private String commitName;

    public CommitEventData(Project project, String commitName) {
        this.project = project;
        this.commitName = commitName;
    }

    public Project getProject() {
        return this.project;
    }

    public String getCommitName() {
        return this.commitName;
    }
}