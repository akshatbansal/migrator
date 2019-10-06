package migrator.ext.javafx.breadcrumps;

import java.util.List;

import migrator.app.Container;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.ext.javafx.component.ViewLoader;

public class JavafxBreadcrumpsGuiKit implements BreadcrumpsGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;

    public JavafxBreadcrumpsGuiKit(ViewLoader viewLoader, Container container) {
        this.viewLoader = viewLoader;
        this.container = container;
    }

    @Override
    public BreadcrumpsComponent createBreadcrumps(List<Breadcrump> breadcrumps) {
        return new JavafxBreadcrumpsComponent(breadcrumps, this.viewLoader);
    }
}