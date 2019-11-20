package migrator.lib.repository.diff;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CompareListDiff<T> implements ListDiff<T> {
    protected List<List<T>> common;
    protected List<T> leftMissing;
    protected List<T> rightMissing;
    protected DiffCompare<T> compare;

    public CompareListDiff(List<T> leftList, List<T> rightList, DiffCompare<T> compare) {
        this.common = new LinkedList<>();
        this.leftMissing = new LinkedList<>();
        this.rightMissing = new LinkedList<>();
        this.compare = compare;

        for (T l : leftList) {
            for (T r : rightList) {
                if (this.compare.compare(r, l)) {
                    common.add(Arrays.asList(l, r));
                }
            }
        }

        for (T l : leftList) {
            Boolean rightMissing = true;
            for (T r : rightList) {
                if (this.compare.compare(r, l)) {
                    rightMissing = false;
                }
            }
            if (rightMissing) {
                this.rightMissing.add(l);
            }
        }

        for (T r : rightList) {
            Boolean leftMissing = true;
            for (T l : leftList) {
                if (this.compare.compare(r, l)) {
                    leftMissing = false;
                }
            }
            if (leftMissing) {
                this.leftMissing.add(r);
            }
        }
    } 

    @Override
    public Collection<List<T>> getCommon() {
        return this.common;
    }

    @Override
    public Collection<T> getLeftMissing() {
        return this.leftMissing;
    }

    @Override
    public Collection<T> getRightMissing() {
        return this.rightMissing;
    }
}