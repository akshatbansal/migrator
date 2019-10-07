package migrator.ext.javafx.change.service;

import migrator.app.Container;
import migrator.app.domain.change.component.CommitForm;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.ext.javafx.change.component.JavafxCommitForm;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxChangeGuiKit implements ChangeGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;

    public JavafxChangeGuiKit(ViewLoader viewLoader, Container container) {
        this.viewLoader = viewLoader;
        this.container = container;
    }

    @Override
    public CommitForm createCommitForm() {
        return new JavafxCommitForm(this.viewLoader, this.container);
    }
}