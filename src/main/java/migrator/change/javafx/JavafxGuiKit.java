package migrator.change.javafx;

import migrator.change.component.CommitForm;
import migrator.change.service.GuiKit;
import migrator.javafx.helpers.View;

public class JavafxGuiKit implements GuiKit {
    protected View view;

    public JavafxGuiKit(View view) {
        this.view = view;
    }

    @Override
    public CommitForm createCommitForm() {
        return new JavafxCommitForm(this.view);
    }
}