package migrator.lib.stringformatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class PascalCaseFormatterTest {
    protected StringFormatter formatter;

    @BeforeEach public void setUp() {
        this.formatter = new PascalCaseFormatter();
    }

    @Test public void testFirsCharToUpperCase() {
        assertEquals("Test", this.formatter.format("test"));
    }

    @Test public void testCamelCaseKeepsMiddleUpperCase() {
        assertEquals("TestCamelCase", this.formatter.format("testCamelCase"));
    }

    @Test public void testSpacesToUpperCase() {
        assertEquals("TestSpacesInText", this.formatter.format("test spaces in text"));
    }

    @Test public void testMultipleSpacesToUpperCase() {
        assertEquals("TestSpacesInText", this.formatter.format("test    spaces    in    text    "));
    }

    @Test public void testNumbersAreKeptInPascalOutput() {
        assertEquals("TestWith44Numbers", this.formatter.format("test with 44numbers"));
    }

    @Test public void testSkipDash() {
        assertEquals("TestDashed", this.formatter.format("test-dashed"));
    }

    @Test public void testSkipUnderscore() {
        assertEquals("TestUnderscore", this.formatter.format("test_underscore"));
    }

    @Test public void testSkipDot() {
        assertEquals("TestDotted", this.formatter.format("test.dotted"));
    }

    @Test public void testKeepAllCaps() {
        assertEquals("TESTALLCAPS", this.formatter.format("TEST ALL CAPS"));
    }
}