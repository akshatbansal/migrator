package migrator.lib.uid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SessionIncrementalGeneratorTest {
    @Test
    public void next_construct_equalsOneInFirstRun() {
        Generator generator = new SessionIncrementalGenerator("aaaa");

        String result = generator.next();

        assertEquals("aaaa-1", result);
    }

    @Test
    public void next_construct_equalsTwoInSecondRun() {
        Generator generator = new SessionIncrementalGenerator("aaaa");

        generator.next();
        String result = generator.next();

        assertEquals("aaaa-2", result);
    }

    @Test
    public void next_setInitialId_equalsPlusOne() {
        Generator generator = new SessionIncrementalGenerator("aaaa", Long.valueOf(100));

        generator.next();
        String result = generator.next();

        assertEquals("aaaa-101", result);
    }
}