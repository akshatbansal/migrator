package migrator.lib.diff;

import java.util.Collection;
import java.util.List;

public interface ListDiff<T> {
    public Collection<T> getLeftMissing();
    public Collection<T> getRightMissing();
    public Collection<List<T>> getCommon();
}