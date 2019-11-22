package migrator.lib.diff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompareListDiffTest {
    @BeforeEach public void setUp() {}

    @Test public void testCompareRightArrayEmptyShowAllMissingRight() {
        List<String> left = Arrays.asList(
            "one",
            "two",
            "three"
        );

        ListDiff<String> diff = new CompareListDiff<>(left, new ArrayList<>(), new EqualsCompare<>());

        assertEquals(3, diff.getRightMissing().size());
        assertEquals(0, diff.getLeftMissing().size());
        assertEquals(0, diff.getCommon().size());
    }

    @Test public void testCompareLeftArrayEmptyShowAllMissingLeft() {
        List<String> right = Arrays.asList(
            "one",
            "two",
            "three"
        );

        ListDiff<String> diff = new CompareListDiff<>(new ArrayList<>(), right, new EqualsCompare<>());

        assertEquals(0, diff.getRightMissing().size());
        assertEquals(3, diff.getLeftMissing().size());
        assertEquals(0, diff.getCommon().size());
    }

    @Test public void testSameValuesInArraysAllInCommon() {
        List<String> left = Arrays.asList(
            "one",
            "two",
            "three"
        );
        List<String> right = Arrays.asList(
            "one",
            "two",
            "three"
        );

        ListDiff<String> diff = new CompareListDiff<>(left, right, new EqualsCompare<>());

        assertEquals(0, diff.getRightMissing().size());
        assertEquals(0, diff.getLeftMissing().size());
        assertEquals(3, diff.getCommon().size());
    }

    @Test public void testCommonArrayHasDifferentObjectPairs() {
        List<String> left = Arrays.asList(
            new String("one")
        );
        List<String> right = Arrays.asList(
            new String("one")
        );

        ListDiff<String> diff = new CompareListDiff<>(left, right, new EqualsCompare<>());

        assertEquals(1, diff.getCommon().size());
        for (List<String> pair : diff.getCommon()) {
            assertEquals(pair.get(0), pair.get(1));
            assertFalse(pair.get(0) == pair.get(1));
        }
    }

    @Test public void testSameValuesInArraysDifferentOrderAllInCommon() {
        List<String> left = Arrays.asList(
            "two",
            "three",
            "one"
        );
        List<String> right = Arrays.asList(
            "one",
            "two",
            "three"
        );

        ListDiff<String> diff = new CompareListDiff<>(left, right, new EqualsCompare<>());

        assertEquals(0, diff.getRightMissing().size());
        assertEquals(0, diff.getLeftMissing().size());
        assertEquals(3, diff.getCommon().size());
    }

    @Test public void testOneLeftOneRighOneCommon() {
        List<String> left = Arrays.asList(
            "two",
            "three"
        );
        List<String> right = Arrays.asList(
            "one",
            "three"
        );

        ListDiff<String> diff = new CompareListDiff<>(left, right, new EqualsCompare<>());

        assertEquals(1, diff.getRightMissing().size());
        assertEquals(1, diff.getLeftMissing().size());
        assertEquals(1, diff.getCommon().size());
    }
}