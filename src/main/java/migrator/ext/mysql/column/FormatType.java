package migrator.ext.mysql.column;

import java.util.List;

import migrator.lib.adapter.Adapter;

public interface FormatType extends Adapter<List<String>, String> {
    public boolean matchesConcretize(String format);
    public boolean matchesGeneralize(String format);
} 