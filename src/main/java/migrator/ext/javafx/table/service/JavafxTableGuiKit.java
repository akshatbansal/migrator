package migrator.ext.javafx.table.service;

import javafx.collections.ObservableList;
import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.table.component.ColumnForm;
import migrator.app.domain.table.component.ColumnList;
import migrator.app.domain.table.component.IndexForm;
import migrator.app.domain.table.component.IndexList;
import migrator.app.domain.table.component.TableForm;
import migrator.app.domain.table.component.TableList;
import migrator.app.domain.table.component.TableView;
import migrator.app.domain.table.service.TableGuiKit;
import migrator.app.gui.BindedObservableList;
import migrator.app.gui.column.ColumnOptionAdapter;
import migrator.app.migration.model.ColumnProperty;
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
        return new JavafxTableList(this.container, this.container.getProjectService().getOpened());
    }

    @Override
    public TableForm createForm() {
        JavafxTableForm form = new JavafxTableForm(this.container);
        form.bind(this.container.getTableActiveState().getActive());
        return form;
    }

    @Override
    public TableView createView() {
        return new JavafxTableView(
            this.container,
            this.gui,
            this.container.getProjectService().getOpened(),
            this.container.getTableActiveState().getActive()
        );
    }

    @Override
    public ColumnList createColumnList() {
        JavafxColumnList list = new JavafxColumnList(this.container);
        list.bind(this.container.getColumnActiveState().getList());
        return list;
    }

    @Override
    public IndexList createIndexList() {
        JavafxIndexList list = new JavafxIndexList(this.container);
        list.bind(this.container.getIndexActiveState().getList());
        return list;
    }

    @Override
    public ColumnForm createColumnForm() {
        JavafxColumnForm form = new JavafxColumnForm(this.container);
        form.bind(this.container.getColumnActiveState().getActive());
        form.bindFormats(
            this.container.getGuiContainer().getColumnFormatCollection().getObservable()
        );
        return form;
    }

    @Override
    public IndexForm createIndexForm() {
        JavafxIndexForm form = new JavafxIndexForm(this.container);
        form.bind(this.container.getIndexActiveState().getActive());

        ObservableList<ColumnProperty> columns = new BindedObservableList<>(
            this.container.getColumnActiveState().getList(),
            new ColumnOptionAdapter()
        );

        form.bindColumns(columns);
        return form;
    }
}