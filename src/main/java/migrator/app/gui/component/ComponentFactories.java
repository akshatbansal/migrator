package migrator.app.gui.component;

import migrator.app.gui.component.breadcrump.BreadcrumpsComponent;
import migrator.app.gui.component.card.CardComponentFactory;
import migrator.app.gui.component.card.CardListComponent;
import migrator.app.gui.component.column.ColumnListComponent;
import migrator.app.gui.component.toast.ToastListComponent;

public class ComponentFactories {
    public ToastListComponent createToastList() {
        return new ToastListComponent();
    }

    public <T> CardListComponent<T> createCardList(CardComponentFactory<T> cardComponentFactory) {
        return new CardListComponent<T>(cardComponentFactory);
    }

    public ColumnListComponent createColumnList() {
        return new ColumnListComponent();
    }

    public BreadcrumpsComponent createBreadcrumps() {
        return new BreadcrumpsComponent();
    }
}