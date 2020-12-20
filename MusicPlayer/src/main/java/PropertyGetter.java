import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyGetter {

    public static String getDatabaseURL() {
        InputStream inputStream;
        String result = "";
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = PropertyGetter.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            result = prop.getProperty("database");
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        
        return result;
    }
}
