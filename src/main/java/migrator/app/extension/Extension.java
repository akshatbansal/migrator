package migrator.app.extension;

import migrator.app.ConfigContainer;

public interface Extension {
    public void load(ConfigContainer config);
}