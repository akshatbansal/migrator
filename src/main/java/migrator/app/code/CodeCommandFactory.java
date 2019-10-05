package migrator.app.code;

import migrator.app.migration.model.ColumnChange;
import migrator.app.migration.model.IndexChange;
import migrator.app.migration.model.TableChange;

public interface CodeCommandFactory {
    public CodeCommand column(ColumnChange columnChange);
    public CodeCommand index(IndexChange indexChange);
    public CodeCommand table(TableChange tableChange);
}