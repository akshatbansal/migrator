package migrator.app.gui.view.commit;

import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.project.model.Project;
import migrator.app.gui.route.RouteChangeEvent;
import migrator.app.gui.service.toast.Toast;
import migrator.app.gui.view.SimpleView;
import migrator.app.gui.view.View;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.SimpleEvent;

public class CommitFormView extends SimpleView implements View {
    @FXML protected TextField name;

    protected EventDispatcher dispatcher;
    protected ObjectProperty<ProjectContainer> activeProjectContainer;

    public CommitFormView(
        EventDispatcher dispatcher
    ) {
        super();
        this.dispatcher = dispatcher;
        
        this.loadFxml("/layout/project/commit/form.fxml");
    }

    public void bind(ObjectProperty<ProjectContainer> activeProjectContainer) {
        this.activeProjectContainer = activeProjectContainer;
    }

    @FXML public void commit() {
        ProjectContainer projectContainer = this.activeProjectContainer.get();
        Project project = projectContainer.getProject();
        if (project == null) {
            return;
        }

        String outputType = project.getOutputType();
        if (outputType == null || outputType.isEmpty()) {
            this.dispatcher.dispatch(
                new SimpleEvent<>("toast.push", new Toast("Project output type has to be set", "error"))
            );
            return;
        }
        String projectFolder = project.getFolder();
        if (projectFolder == null || projectFolder.isEmpty()) {
            this.dispatcher.dispatch(
                new SimpleEvent<>("toast.push", new Toast("Project folder has to be set", "error"))
            );
            return;
        }
        String commitName = name.textProperty().get();
        if (commitName == null || commitName.isEmpty()) {
            this.dispatcher.dispatch(
                new SimpleEvent<>("toast.push", new Toast("Commit name has to be set", "error"))
            );
            return;
        }

        this.dispatcher.dispatch(
            new SimpleEvent<>("commit", projectContainer)
        );
    }

    @FXML public void close() {
        this.dispatcher.dispatch(
            new RouteChangeEvent("project")
        );
    }
}