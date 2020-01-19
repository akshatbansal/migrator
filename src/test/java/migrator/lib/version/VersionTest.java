package migrator.lib.version;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class VersionTest {
    @Test
    public void comapreTo_patchSmaller_isGreater() {
        Version smaller = new Version("0.0.1");
        Version version = new Version("0.0.2");

        assertEquals(1, version.compareTo(smaller));
    }

    @Test
    public void comapreTo_minorSmaller_isGreater() {
        Version smaller = new Version("0.0.1");
        Version version = new Version("0.1.1");

        assertEquals(1, version.compareTo(smaller));
    }

    @Test
    public void comapreTo_majorSmaller_isGreater() {
        Version smaller = new Version("1.0.0");
        Version version = new Version("2.0.0");

        assertEquals(1, version.compareTo(smaller));
    }

    @Test
    public void comapreTo_patchGreater_isSmaller() {
        Version greater = new Version("0.0.2");
        Version version = new Version("0.0.1");

        assertEquals(-1, version.compareTo(greater));
    }

    @Test
    public void comapreTo_minorGreater_isSmaller() {
        Version greater = new Version("0.1.1");
        Version version = new Version("0.0.1");

        assertEquals(-1, version.compareTo(greater));
    }

    @Test
    public void comapreTo_majorGreater_isSmaller() {
        Version greater = new Version("2.0.0");
        Version version = new Version("1.0.0");

        assertEquals(-1, version.compareTo(greater));
    }

    @Test
    public void comapreTo_equal_isEqual() {
        Version equal = new Version("2.4.1");
        Version version = new Version("2.4.1");

        assertEquals(0, version.compareTo(equal));
    }
}