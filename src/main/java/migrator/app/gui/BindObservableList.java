package migrator.app.gui;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.lib.adapter.Adapter;

public class BindObservableList<T, U> {
    protected Adapter<T, U> adapter;
    protected ObservableList<T> originalList;
    protected ObservableList<U> transformedList;

    public BindObservableList(ObservableList<T> originalList, ObservableList<U> transformedList, Adapter<T, U> adapter) {
        this.adapter = adapter;
        this.originalList = originalList;
        this.transformedList = transformedList;

        this.originalList.addListener((Change<? extends T> change) -> {
            while (change.next()) {
                this.removeOriginal(change.getRemoved());
                this.addOriginal(change.getAddedSubList());
            }
        });
        this.addOriginal(originalList);
    }

    public void add(T item) {
        this.originalList.add(item);
    }

    public void remove(T item) {
        this.originalList.remove(item);
    }

    public ObservableList<U> getList() {
        return this.transformedList;
    }

    public ObservableList<T> getOriginalList() {
        return this.originalList;
    }

    private void addOriginal(List<? extends T> added) {
        List<U> listToAdd = new LinkedList<>();
        for (T item : added) {
            listToAdd.add(
                this.adapter.generalize(item)
            );
        }
        this.transformedList.addAll(listToAdd);
    }

    private void removeOriginal(List<? extends T> removed) {
        List<U> listToRemove = new LinkedList<>();
        for (T item : removed) {
            for (U uItem : this.transformedList) {
                if (item != this.adapter.concretize(uItem)) {
                    continue;
                }
                listToRemove.add(uItem);
            }
        }
        this.transformedList.removeAll(listToRemove);
    }
}