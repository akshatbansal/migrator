package migrator.app.gui;

import javafx.stage.Window;
import migrator.app.boot.Container;
import migrator.app.database.DatabaseContainer;
import migrator.app.gui.column.ColumnChangePropertyAdapter;
import migrator.app.gui.column.format.ColumnFormatCollection;
import migrator.app.gui.component.ComponentFactories;
import migrator.app.gui.view.ViewFactories;

public class GuiContainer {
    protected ColumnFormatCollection columnFormatCollection;
    protected ViewFactories viewFactoriesValue;
    protected ComponentFactories componentFactoriesValue;
    protected HotkeyContainer hotkeyContainerValue;

    public GuiContainer(
        Container container,
        Window window
    ) {
        DatabaseContainer databaseContainer = container.databaseContainer();
        this.hotkeyContainerValue = new HotkeyContainer();

        this.columnFormatCollection = new ColumnFormatCollection(
            databaseContainer.getApplicationColumnFormatCollection().getObservable()
        );
        this.componentFactoriesValue = new ComponentFactories();
        this.viewFactoriesValue = new ViewFactories(
            container,
            this.componentFactories(),
            window,
            this.getColumnFormatCollection(),
            new BindedObservableList<>(
                container.columnContainer().columnStore().getList(),
                new ColumnChangePropertyAdapter()
            ),
            this.hotkeyContainer().hotkeysService()
        );
    }

    public ColumnFormatCollection getColumnFormatCollection() {
        return this.columnFormatCollection;
    }

    public ViewFactories viewFactories() {
        return this.viewFactoriesValue;
    }

    public ComponentFactories componentFactories() {
        return this.componentFactoriesValue;
    }

    public HotkeyContainer hotkeyContainer() {
        return this.hotkeyContainerValue;
    }
}