package migrator.ext.javafx.database.service;

import migrator.app.Container;
import migrator.app.Gui;
import migrator.app.domain.database.component.DatabaseCard;
import migrator.app.domain.database.component.DatabaseList;
import migrator.app.domain.database.model.DatabaseConnection;
import migrator.app.domain.database.service.GuiKit;
import migrator.ext.javafx.component.ViewLoader;
import migrator.ext.javafx.database.component.JavafxDatabaseCard;
import migrator.ext.javafx.database.component.JavafxDatabaseList;

public class JavafxGuiKit implements GuiKit {
    protected Container container;
    protected ViewLoader viewLoader;
    protected Gui gui;

    public JavafxGuiKit(ViewLoader viewLoader, Container container, Gui gui) {
        this.viewLoader = viewLoader;
        this.container = container;
        this.gui = gui;
    }

    @Override
    public DatabaseCard createCard(DatabaseConnection databaseConnection) {
        return new JavafxDatabaseCard(databaseConnection, this.viewLoader);
    }

    @Override
    public DatabaseList createList() {
        return new JavafxDatabaseList(this.viewLoader, this.container, this.gui);
    }
}