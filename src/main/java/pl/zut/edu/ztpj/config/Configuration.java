package pl.zut.edu.ztpj.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    
    protected Configuration() {}
    
    private static Configuration instance = null;
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        
        return instance;
    }
    
    private Properties properties = new Properties();
    private InputStream input = null;
    
    private void init() {
        try {
            input = new FileInputStream("config.properties");
            
            properties.load(input);
        } catch(IOException e) {
            // TODO handle error
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO handle error
                }
            }
        }
    }
    
    public String getProperty(String key) {
        if (properties.isEmpty())
            init();
        
        return properties.getProperty(key);
    }
}
