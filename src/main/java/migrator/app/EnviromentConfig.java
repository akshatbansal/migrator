package migrator.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnviromentConfig {
    protected String enviroment;

    public EnviromentConfig(String enviroment) {
        this.enviroment = enviroment;
    }

    public Properties getProperties(String fileName) {
        String configPath = "config" + File.separator + this.enviroment + File.separator + fileName + ".properties";
        Properties properties = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream(configPath);
        if (input == null) {
            System.err.println("Config path does not exists '" + configPath + "'");
            return properties;
        }
        try {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return properties;
    }
}