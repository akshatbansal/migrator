package migrator.lib.diff;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EqualsCompareTest {
    @Test public void testEmptyStringEquals() {
        EqualsCompare<String> compare = new EqualsCompare<>();

        assertTrue(compare.compare("", ""));
    }

    @Test public void testDifferentStringsDontEquals() {
        EqualsCompare<String> compare = new EqualsCompare<>();

        assertFalse(compare.compare("something", "something else"));
    }

    @Test public void testNullLeftArgumentThrowsException() {
        EqualsCompare<String> compare = new EqualsCompare<>();
        assertThrows(NullPointerException.class, () -> {
            compare.compare(null, "something else");
        });
    }

    @Test public void testNullRightArgumentIsFalse() {
        EqualsCompare<String> compare = new EqualsCompare<>();

        assertFalse(compare.compare("something else", null));
    }

    @Test public void testObjectsEquals() {
        EqualsCompare<MockObject> compare = new EqualsCompare<>();

        assertTrue(compare.compare(new MockObject("something"), new MockObject("something")));
    }

    @Test public void testObjectsDontEqual() {
        EqualsCompare<MockObject> compare = new EqualsCompare<>();

        assertFalse(compare.compare(new MockObject("something"), new MockObject("something else")));
    }

    protected class MockObject {
        protected String text;
        protected MockObject(String text) {
            this.text = text;
        }

        @Override
        public boolean equals(Object obj) {
            MockObject cmp = (MockObject) obj;
            return this.text.equals(cmp.getText());
        }

        public String getText() {
            return this.text;
        }
    }
}