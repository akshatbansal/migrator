package migrator.lib.diff;

import java.util.List;

public class Diff {
    public static <T> ListDiff<T> getSimpleListDiff(List<T> left, List<T> right) {
        return Diff.getListDiff(left, right, new EqualsCompare<>());
    }

    public static <T> ListDiff<T> getListDiff(List<T> left, List<T> right, DiffCompare<T> compare) {
        return new CompareListDiff<>(left, right, compare);
    }
}