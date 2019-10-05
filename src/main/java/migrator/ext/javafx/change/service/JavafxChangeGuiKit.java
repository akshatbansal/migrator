package migrator.ext.javafx.change.service;

import migrator.app.domain.change.component.CommitForm;
import migrator.app.domain.change.service.ChangeGuiKit;
import migrator.app.domain.change.service.ChangeService;
import migrator.app.migration.Migration;
import migrator.ext.javafx.change.component.JavafxCommitForm;
import migrator.ext.javafx.component.ViewLoader;
import migrator.router.Router;

public class JavafxChangeGuiKit implements ChangeGuiKit {
    protected ViewLoader viewLoader;
    protected Router router;
    protected ChangeService changeService;
    protected Migration migration;

    public JavafxChangeGuiKit(ViewLoader viewLoader, Router router, ChangeService changeService, Migration migration) {
        this.viewLoader = viewLoader;
        this.router = router;
        this.changeService = changeService;
        this.migration = migration;
    }

    @Override
    public CommitForm createCommitForm() {
        return new JavafxCommitForm(this.viewLoader, this.router, this.changeService, this.migration);
    }
}