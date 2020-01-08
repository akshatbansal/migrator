package migrator.app.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import migrator.lib.adapter.Adapter;

public class BindedObservableList<T, U> implements ObservableList<T> {
    private Adapter<T, U> adapter;
    private ObservableList<T> bindedList;

    public BindedObservableList(ObservableList<U> originalList, Adapter<T, U> adapter) {
        this.adapter = adapter;
        this.bindedList = FXCollections.observableArrayList();

        originalList.addListener((Change<? extends U> change) -> {
            while (change.next()) {
                this.removeItems(change.getRemoved());
                this.addItems(change.getAddedSubList());
            }
        });
        this.addItems(originalList);
    }

    private void removeItems(List<? extends U> removed) {
        List<T> toRemove = new LinkedList<>();
        for (U item : removed) {
            toRemove.add(
                this.adapter.concretize(item)
            );
        }
        this.removeAll(toRemove);
    }

    private void addItems(List<? extends U> added) {
        List<T> toAdd = new LinkedList<>();
        for (U item : added) {
            toAdd.add(this.adapter.concretize(item));
        }
        this.addAll(toAdd);
    }

    @Override
    public int size() {
        return this.bindedList.size();
    }

    @Override
    public boolean isEmpty() {
        return this.bindedList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.bindedList.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return this.bindedList.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.bindedList.toArray();
    }

    @Override
    public <V> V[] toArray(V[] a) {
        return this.bindedList.toArray(a);
    }

    @Override
    public boolean add(T e) {
        return this.bindedList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return this.bindedList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.bindedList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return this.bindedList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return this.bindedList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.bindedList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.bindedList.retainAll(c);
    }

    @Override
    public void clear() {
        this.bindedList.clear();
    }

    @Override
    public T get(int index) {
        return this.bindedList.get(index);
    }

    @Override
    public T set(int index, T element) {
        return this.bindedList.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        this.bindedList.add(index, element);
    }

    @Override
    public T remove(int index) {
        return this.bindedList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.bindedList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.bindedList.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return this.bindedList.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return this.bindedList.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return this.bindedList.subList(fromIndex, toIndex);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        this.bindedList.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.bindedList.removeListener(listener);
    }

    @Override
    public void addListener(ListChangeListener<? super T> listener) {
        this.bindedList.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super T> listener) {
        this.bindedList.removeListener(listener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(T... elements) {
        return this.bindedList.addAll(elements);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean setAll(T... elements) {
        return this.bindedList.setAll(elements);
    }

    @Override
    public boolean setAll(Collection<? extends T> col) {
        return this.bindedList.setAll(col);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeAll(T... elements) {
        return this.bindedList.removeAll(elements);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(T... elements) {
        return this.bindedList.retainAll(elements);
    }

    @Override
    public void remove(int from, int to) {
        this.bindedList.remove(from, to);
    }
}