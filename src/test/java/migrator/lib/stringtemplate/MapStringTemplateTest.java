package migrator.lib.stringtemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class MapStringTemplateTest {
    @Test
    public void render_emptyMap_equalsTemplateString() {
        String template = "Test {{SOME}} placeholder.";
        StringTemplate stringTemplate = new MapStringTemplate(template, new Hashtable<>());

        String result = stringTemplate.render();

        assertEquals("Test {{SOME}} placeholder.", result);
    }

    @Test
    public void render_mapWithNoMatchingKey_equalsTemplateString() {
        String template = "Test {{SOME}} placeholder.";
        Map<String, String> map = new Hashtable<>();
        map.put("ONE", "number one");
        StringTemplate stringTemplate = new MapStringTemplate(template, map);

        String result = stringTemplate.render();

        assertEquals("Test {{SOME}} placeholder.", result);
    }

    @Test
    public void render_mapWithMatchingKey_equalsReplacedTemplate() {
        String template = "Test {{SOME}} placeholder.";
        Map<String, String> map = new Hashtable<>();
        map.put("SOME", "number one");
        StringTemplate stringTemplate = new MapStringTemplate(template, map);

        String result = stringTemplate.render();

        assertEquals("Test number one placeholder.", result);
    }

    @Test
    public void render_mapWithMatchingKeyAndMultipleOcurences_equalsReplacedTemplate() {
        String template = "Test {{SOME}} placeholder {{SOME}}.";
        Map<String, String> map = new Hashtable<>();
        map.put("SOME", "number one");
        StringTemplate stringTemplate = new MapStringTemplate(template, map);

        String result = stringTemplate.render();

        assertEquals("Test number one placeholder number one.", result);
    }

    @Test
    public void render_mapWithMultipleMatchingKeys_equalsReplacedTemplate() {
        String template = "Test {{SOME}} placeholder. {{ONE}}";
        Map<String, String> map = new Hashtable<>();
        map.put("SOME", "number one");
        map.put("ONE", "what?");
        StringTemplate stringTemplate = new MapStringTemplate(template, map);

        String result = stringTemplate.render();

        assertEquals("Test number one placeholder. what?", result);
    }
}