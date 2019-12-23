package migrator.app.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CommandSequenceTest {
    private CommandSequence createSequence(List<CodeCommand> commands) {
        return new CommandSequence(commands);
    }

    @Test public void toCode_null_equalsEmptyString() {
        CommandSequence command = this.createSequence(null);

        String result = command.toCode();

        assertEquals("", result);
    }

    @Test public void toCode_emptyArray_equalsEmptyString() {
        CommandSequence command = this.createSequence(new LinkedList<>());

        String result = command.toCode();

        assertEquals("", result);
    }

    @Test public void toCode_oneCommand_equalsCommandCode() {
        CommandSequence command = this.createSequence(Arrays.asList(
            new MockCodeCommand("test code;")
        ));

        String result = command.toCode();

        assertEquals("test code;", result);
    }

    @Test public void toCode_multipleCommands_equalsCommandsJoinTogether() {
        CommandSequence command = this.createSequence(Arrays.asList(
            new MockCodeCommand("test code;"),
            new MockCodeCommand("second;")
        ));

        String result = command.toCode();

        assertEquals("test code;second;", result);
    }

    @Test public void toCode_commandAndSequence_equalsCommandAndSequenceJoinTogether() {
        CommandSequence command = this.createSequence(Arrays.asList(
            new MockCodeCommand("test code;"),
            this.createSequence(Arrays.asList(
                new MockCodeCommand("seq-first;"),
                new MockCodeCommand("seq-second;")
            ))
        ));

        String result = command.toCode();

        assertEquals("test code;seq-first;seq-second;", result);
    }

    public class MockCodeCommand implements CodeCommand {
        private String code;

        public MockCodeCommand(String code) {
            this.code = code;
        }

        @Override
        public String toCode() {
            return this.code;
        }
    }
}