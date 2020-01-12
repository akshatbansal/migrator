package migrator.app.extension;

import migrator.app.boot.Container;

public interface Extension {
    public void load(Container container);
}