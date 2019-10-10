package migrator.lib.stringformatter;

public class UnderscoreFormatter implements StringFormatter {
    @Override
    public String format(String input) {
        String underscore = "";
        Boolean prevLower = false;
        for (char ch : input.toCharArray()) {
            if (prevLower && ch >= 'A' && ch <= 'Z') {
                underscore += "_";
                prevLower = false;
            }
            if (ch >= 'a' && ch <= 'z') {
                prevLower = true;
            }
            underscore += ch;
        }

        underscore = underscore.toLowerCase().trim();
        underscore = underscore.replace(' ', '_');
        underscore = underscore.replace('-', '_');
        underscore = underscore.replace('.', '_');
        underscore = underscore.replaceAll("_+", "_");
        return underscore;
    }
}