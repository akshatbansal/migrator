package migrator.lib.stringformatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class UnderscoreFormatterTest {
    protected StringFormatter formatter;

    @BeforeEach public void setUp() {
        this.formatter = new UnderscoreFormatter();
    }

    @Test public void testAllLowercaseStaysLowercase() {
        assertEquals("test", this.formatter.format("test"));
    }

    @Test public void testFirstUpperCaseTurnsToLowerCase() {
        assertEquals("test", this.formatter.format("Test"));
    }

    @Test public void testSpacesToUndeerscore() {
        assertEquals("test_spaces_in_text", this.formatter.format("test spaces in text"));
    }

    @Test public void testMultipleSpacesToUnderscore() {
        assertEquals("test_spaces_in_text", this.formatter.format("test    spaces    in    text    "));
    }

    @Test public void testNumbersAreKeptInPascalOutput() {
        assertEquals("test_with_44numbers", this.formatter.format("test with 44numbers"));
    }

    @Test public void testReplaceDash() {
        assertEquals("test_dashed", this.formatter.format("test-dashed"));
    }

    @Test public void testKeepUnderscore() {
        assertEquals("test_underscore", this.formatter.format("test_underscore"));
    }

    @Test public void testAllCapsToLowerCase() {
        assertEquals("test_all_caps", this.formatter.format("TEST ALL CAPS"));
    }

    @Test public void testReplaceDot() {
        assertEquals("test_dotted", this.formatter.format("test.dotted"));
    }

    @Test public void testCamelCaseToDashed() {
        assertEquals("test_camel_case", this.formatter.format("testCamelCase"));
    }

    @Test public void testPascalCaseToDashed() {
        assertEquals("test_pascal_case", this.formatter.format("TestPascalCase"));
    }
}