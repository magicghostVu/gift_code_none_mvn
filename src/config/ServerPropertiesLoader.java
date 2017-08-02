package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by magic_000 on 20/06/2017.
 */
public class ServerPropertiesLoader {
    public Properties properties;
    public ServerPropertiesLoader() {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + File.separator + "config/server.properties");
            this.properties= new Properties();
            properties.load(fileInputStream);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Integer getInt(String key){
        String tmpVal= properties.getProperty(key);
        try {
            Integer res= Integer.parseInt(tmpVal);
            return res;
        }catch (NumberFormatException ne){
            ne.printStackTrace();
            return null;
        }
    }

    public String getString(String key){
        return properties.getProperty(key);
    }

    public Long getLong(String key){
        String tmpVal= properties.getProperty(key);
        try {
            Long res= Long.parseLong(tmpVal);
            return res;
        }catch (NumberFormatException ne){
            ne.printStackTrace();
            return null;
        }
    }
}
