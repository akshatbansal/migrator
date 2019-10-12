package migrator.app.database.driver;

import javafx.collections.ObservableList;
import migrator.app.domain.project.model.Project;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;

public interface DatabaseDriver {
    public void connect();
    public void disconnect();
    public ObservableList<Table> getTables(Project project);
    public ObservableList<Column> getColumns(String tableName);
    public ObservableList<Index> getIndexes(String tableName);
    public Boolean isConnected();
    public String getError();
}