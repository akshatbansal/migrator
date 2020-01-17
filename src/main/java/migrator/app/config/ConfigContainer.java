package migrator.app.config;

public class ConfigContainer {
    private EnviromentConfig enviromentConfig;

    public ConfigContainer() {
        String env = "production";
        if (System.getenv("MIGRATOR_ENV") != null) {
            env = System.getenv("MIGRATOR_ENV");
        }

        this.enviromentConfig = new EnviromentConfig(env);
    }

    public EnviromentConfig enviroment() {
        return this.enviromentConfig;
    }
}