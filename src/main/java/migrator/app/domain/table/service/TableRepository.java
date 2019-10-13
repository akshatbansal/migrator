package migrator.app.domain.table.service;

import migrator.app.domain.table.model.Table;
import migrator.lib.modelstorage.ObservableListRepository;

public class TableRepository extends ObservableListRepository<Table> {
    public TableRepository() {
        super();
    }

   @Override
   protected String getId(Table value) {
       return value.getOriginal().getName();
   }
}