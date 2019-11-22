package migrator.app.database.driver;

import javafx.collections.ObservableList;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;

public interface DatabaseDriver {
    public void connect();
    public void disconnect();
    public ObservableList<Table> getTables();
    public ObservableList<Column> getColumns(Table table);
    public ObservableList<Index> getIndexes(Table table);
    public Boolean isConnected();
    public String getError();
}