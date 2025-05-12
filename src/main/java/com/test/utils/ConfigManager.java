package com.test.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static boolean initialized = false;
    private static final String CONFIG_FILE = "src/test/resources/config/config.properties";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        if (initialized) {
            return;
        }

        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            properties.load(input);
            initialized = true;
            System.out.println("Configuration loaded successfully");
        } catch (IOException e) {
            System.out.println("Failed to load configuration: " + e.getMessage());
            // Continue with defaults rather than failing the tests
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            System.out.println("Invalid integer format for property " + key + ": " + value);
            return defaultValue;
        }
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
}