package migrator.lib.stringformatter;

import java.util.Arrays;
import java.util.List;

public class PascalCaseFormatter implements StringFormatter {
    @Override
    public String format(String input) {
        String pascalCase = "";
        Boolean toUpper = true;
        List<Character> skipChars = Arrays.asList(
            ' ',
            '-',
            '_',
            '.'
        );
        for (char ch : input.toCharArray()) {
            if (skipChars.contains(ch)) {
                toUpper = true;
                continue;
            }
            if (ch >= '0' && ch <= '9') {
                toUpper = true;
                pascalCase += ch;
                continue;
            }

            if (toUpper) {
                ch = Character.toUpperCase(ch);
                toUpper = false;
            }
            pascalCase += ch;
        }
        return pascalCase;
    }
}