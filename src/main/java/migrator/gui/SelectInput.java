package migrator.gui;

import java.util.List;

public interface SelectInput<T> extends Input<T> {
    public void setOptions(List<T> options);
}