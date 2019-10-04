package migrator.change.javafx;

import migrator.change.component.CommitForm;
import migrator.change.service.GuiKit;
import migrator.javafx.helpers.View;
import migrator.migration.ChangeService;
import migrator.router.Router;

public class JavafxGuiKit implements GuiKit {
    protected View view;
    protected Router router;
    protected ChangeService changeService;

    public JavafxGuiKit(View view, Router router, ChangeService changeService) {
        this.view = view;
        this.router = router;
        this.changeService = changeService;
    }

    @Override
    public CommitForm createCommitForm() {
        return new JavafxCommitForm(this.view, this.router, this.changeService);
    }
}