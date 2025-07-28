package org.utils.helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

    public class ConfigReader {

        private static Properties properties;

        static {
            String path = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            try (FileInputStream fis = new FileInputStream(path)) {
                properties = new Properties();
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
            }
        }

        public static String get(String key) {
            String value = properties.getProperty(key);
            if (value == null) {
                throw new RuntimeException("Property '" + key + "' not found in config.properties");
            }
            return value;
        }

        // Optional: In Java Properties, a default value is used when the key is not found in the .properties file.
        public static String get(String key, String defaultValue) {
            return properties.getProperty(key, defaultValue);
        }
    }


