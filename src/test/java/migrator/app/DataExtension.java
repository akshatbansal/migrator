package migrator.app;

import migrator.app.ConfigContainer;
import migrator.app.extension.Extension;

public class DataExtension implements Extension {
    protected ConfigContainer dataConfig;

    public DataExtension(ConfigContainer dataConfig) {
        this.dataConfig = dataConfig;
    }

    @Override
    public void load(ConfigContainer config) {
        if (this.dataConfig.databaseDriverManagerConfig().get() != null) {
            config.databaseDriverManagerConfig().set(
                this.dataConfig.databaseDriverManagerConfig().get()
            );
        }   
    }
}