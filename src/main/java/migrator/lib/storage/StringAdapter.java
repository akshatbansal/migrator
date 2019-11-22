package migrator.lib.storage;

import migrator.lib.adapter.Adapter;

public class StringAdapter implements Adapter<String, String> {
    @Override
    public String concretize(String item) {
        return item;
    }

    @Override
    public String generalize(String item) {
        return item;
    }
}