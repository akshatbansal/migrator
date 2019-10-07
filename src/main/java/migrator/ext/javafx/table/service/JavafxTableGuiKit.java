package migrator.ext.javafx.table.service;

import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.model.Column;
import migrator.app.domain.table.model.Index;
import migrator.app.domain.table.model.Table;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.table.component.JavafxColumnForm;
import migrator.ext.javafx.table.component.JavafxColumnList;
import migrator.ext.javafx.table.component.JavafxIndexForm;
import migrator.ext.javafx.table.component.JavafxIndexList;
import migrator.ext.javafx.table.component.JavafxTableForm;
import migrator.ext.javafx.table.component.JavafxTableList;
import migrator.ext.javafx.table.component.JavafxTableView;

public class JavafxTableGuiKit implements TableGuiKit {
    protected ViewLoader viewLoader;
    protected Container container;
    protected Gui gui;

    public JavafxTableGuiKit(ViewLoader viewLoader, Container container, Gui gui) {
        this.gui = gui;
        this.viewLoader  = viewLoader;
        this.container = container;
    }

    @Override
    public TableList createList() {
        return new JavafxTableList(this.viewLoader, this.container, this.gui);
    }

    @Override
    public TableForm createForm(Table table) {
        return new JavafxTableForm(table, this.viewLoader, this.container);
    }

    @Override
    public TableView createView(Table table) {
        return new JavafxTableView(table, this.viewLoader, this.container, this.gui);
    }

    @Override
    public ColumnList createColumnList() {
        return new JavafxColumnList(this.viewLoader, this.container);
    }

    @Override
    public IndexList createIndexList() {
        return new JavafxIndexList(this.viewLoader, this.container);
    }

    @Override
    public ColumnForm createColumnForm(Column column) {
        return new JavafxColumnForm(column, this.viewLoader, this.container);
    }

    @Override
    public IndexForm createIndexForm(Index index) {
        return new JavafxIndexForm(index, this.viewLoader, this.container);
    }
}