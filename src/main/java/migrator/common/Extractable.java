package migrator.common;

import javafx.beans.Observable;

public interface Extractable {
    public Observable[] extract();
}