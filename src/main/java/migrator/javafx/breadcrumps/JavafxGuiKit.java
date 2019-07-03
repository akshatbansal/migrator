package migrator.javafx.breadcrumps;

import migrator.breadcrumps.BreadcrumpsComponent;
import migrator.breadcrumps.GuiKit;

public class JavafxGuiKit implements GuiKit {
    protected BreadcrumpsService breadcrumpsService;

    public JavafxGuiKit(BreadcrumpsService breadcrumpsService) {
        this.breadcrumpsService = breadcrumpsService;
    }

    @Override
    public BreadcrumpsComponent createBreadcrumps() {
        return new BreadcrumpsController(this.breadcrumpsService);
    }
}