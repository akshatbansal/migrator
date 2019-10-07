package migrator.app.breadcrumps;

import java.util.List;

import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Table;

public interface BreadcrumpsGuiKit {
    public BreadcrumpsComponent createBreadcrumps(List<Breadcrump> breadcrumps);
    public BreadcrumpsComponent createBreadcrumps(Table table);
    public BreadcrumpsComponent createBreadcrumps(Project project);
}