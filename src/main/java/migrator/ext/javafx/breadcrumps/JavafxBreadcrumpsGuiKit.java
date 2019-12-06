package migrator.ext.javafx.breadcrumps;

import java.util.Arrays;
import java.util.List;

import migrator.app.Container;
import migrator.app.breadcrumps.Breadcrump;
import migrator.app.breadcrumps.BreadcrumpsComponent;
import migrator.app.breadcrumps.BreadcrumpsGuiKit;
import migrator.app.breadcrumps.VoidBreadcrump;
import migrator.app.domain.project.breadcrumps.ProjectsBreadcrump;
import migrator.app.domain.project.breadcrumps.SingleProjectBreadcrump;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;
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

    @Override
    public BreadcrumpsComponent createBreadcrumps(Table table, Project project) {
        return this.createBreadcrumps(
            Arrays.asList(
                new ProjectsBreadcrump(
                    this.container.getProjectService(),
                    this.container.getTableActiveState()
                ),
                new SingleProjectBreadcrump(
                    this.container.getProjectService(),
                    project,
                    this.container.getTableActiveState()
                ),
                new VoidBreadcrump(table.nameProperty())
            )
        );
    }

    @Override
    public BreadcrumpsComponent createBreadcrumps(Project project) {
        return this.createBreadcrumps(
            Arrays.asList(
                new ProjectsBreadcrump(
                    this.container.getProjectService(),
                    this.container.getTableActiveState()
                ),
                new VoidBreadcrump(project.nameProperty())
            )
        );
    }

    @Override
    public BreadcrumpsComponent createBreadcrumpsForCommit(Project project) {
        return this.createBreadcrumps(
            Arrays.asList(
                new ProjectsBreadcrump(
                    this.container.getProjectService(),
                    this.container.getTableActiveState()
                ),
                new SingleProjectBreadcrump(
                    this.container.getProjectService(),
                    project,
                    this.container.getTableActiveState()
                ),
                new VoidBreadcrump("commit changes")
            )
        );
    }
}