package migrator.app.domain.table.action;

import javafx.beans.property.ObjectProperty;
import migrator.app.domain.project.ProjectContainer;
import migrator.app.domain.table.TableContainer;
import migrator.app.domain.table.model.Table;
import migrator.lib.dispatcher.Event;
import migrator.lib.dispatcher.EventDispatcher;
import migrator.lib.dispatcher.EventHandler;
import migrator.lib.dispatcher.SimpleEvent;

public class TableNewHandler implements EventHandler {
    private TableContainer tableContainer;
    private ObjectProperty<ProjectContainer> projectContainer;
    private EventDispatcher dispatcher;

    public TableNewHandler(
        TableContainer tableContainer,
        ObjectProperty<ProjectContainer> projectContainer,
        EventDispatcher dispatcher
    ) {
        this.tableContainer = tableContainer;
        this.projectContainer = projectContainer;
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(Event<?> event) {
        Table newTable = this.tableContainer.tableFactory().createWithCreateChange(this.projectContainer.get().getProject().getId(), "new_table");
        this.tableContainer.tableStore().add(newTable);
        this.tableContainer.tableRepository().addWith(newTable);

        this.dispatcher.dispatch(
            new SimpleEvent<>("column.create.id", newTable.getUniqueKey())
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.create.created_at", newTable.getUniqueKey())
        );
        this.dispatcher.dispatch(
            new SimpleEvent<>("column.create.modified_at", newTable.getUniqueKey())
        );

        this.dispatcher.dispatch(
            new SimpleEvent<>("table.select", newTable)
        );
    }
}