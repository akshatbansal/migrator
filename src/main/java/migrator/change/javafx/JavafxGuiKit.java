package migrator.change.javafx;

import migrator.app.migration.Migration;
import migrator.change.component.CommitForm;
import migrator.change.service.GuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.javafx.helpers.View;
import migrator.migration.ChangeService;
import migrator.router.Router;

public class JavafxGuiKit implements GuiKit {
    protected ViewLoader viewLoader;
    protected Router router;
    protected ChangeService changeService;
    protected Migration migration;

    public JavafxGuiKit(ViewLoader viewLoader, Router router, ChangeService changeService, Migration migration) {
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