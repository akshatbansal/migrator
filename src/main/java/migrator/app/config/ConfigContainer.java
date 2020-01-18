package migrator.app.config;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigContainer {
    private EnviromentConfig enviromentConfig;
    private Path storagePathValue;

    public ConfigContainer() {
        String env = "production";
        if (System.getenv("MIGRATOR_ENV") != null) {
            env = System.getenv("MIGRATOR_ENV");
        }

        this.enviromentConfig = new EnviromentConfig(env);

        this.storagePathValue = Paths.get(System.getProperty("user.home"), ".migrator");
        if (Files.notExists(this.storagePathValue)) {
            this.storagePathValue.toFile().mkdirs();
        }
    }

    public EnviromentConfig enviroment() {
        return this.enviromentConfig;
    }

    public Path storagePath() {
        return this.storagePathValue;
    }
}