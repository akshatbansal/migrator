package migrator.lib.stringtemplate;

import java.util.Map;
import java.util.Map.Entry;

public class MapStringTemplate implements StringTemplate {
    protected String template;
    protected Map<String, String> replaceMap;

    public MapStringTemplate(String template, Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
        this.template = template;
    }

    @Override
    public String render() {
        String rendered = this.template;
        for (Entry<String, String> entry : this.replaceMap.entrySet()) {
            rendered = rendered.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return rendered;
    }
}