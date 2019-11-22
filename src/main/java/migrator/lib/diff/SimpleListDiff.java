package migrator.lib.diff;

import java.util.Collection;
import java.util.List;

public class SimpleListDiff<T> implements ListDiff<T> {
    protected CompareListDiff<T> compareDiff;

    public SimpleListDiff(List<T> leftList, List<T> rightList) {
        this.compareDiff = new CompareListDiff<>(
            leftList,
            rightList,
            new EqualsCompare<>()
        );
    } 

    @Override
    public Collection<List<T>> getCommon() {
        return this.compareDiff.getCommon();
    }

    @Override
    public Collection<T> getLeftMissing() {
        return this.compareDiff.getLeftMissing();
    }

    @Override
    public Collection<T> getRightMissing() {
        return this.compareDiff.getRightMissing();
    }
}