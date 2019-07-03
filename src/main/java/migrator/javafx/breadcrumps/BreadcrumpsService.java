package migrator.javafx.breadcrumps;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import migrator.breadcrumps.Breadcrump;

public class BreadcrumpsService {
    protected ObservableList<Breadcrump> list;

    public BreadcrumpsService() {
        this.list = FXCollections.observableArrayList();
    }

    public ObservableList<Breadcrump> getList() {
        return this.list;
    }

    public void add(Breadcrump breadcrump) {
        this.list.add(breadcrump);
    }

    public void set(Breadcrump ...breadcrumps) {
        this.list.setAll(breadcrumps);
    }

    public void clear() {
        this.list.clear();
    }
}