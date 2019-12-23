package migrator.app.gui.component.card;

import migrator.lib.adapter.Adapter;

public class SimpleCardComponentFactory<T> implements CardComponentFactory<T> {
    protected Adapter<T, CardGuiModel<T>> adapter;

    public SimpleCardComponentFactory(Adapter<T, CardGuiModel<T>> adapter) {
        this.adapter = adapter;
    }

    @Override
    public CardComponent<T> create(T item) {
        return new SimpleCardComponent<T>(
            this.adapter.generalize(item)
        );
    }
}