package com.test.drivers;

import com.test.utils.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.time.Duration;

public class DriverManager {
    private static AppiumDriver driver;
    private static boolean isInitialized = false;

    public static AppiumDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    private static void setDriver(AppiumDriver appiumDriver) {
        driver = appiumDriver;
    }

    public static void initializeDriver() {
        if (isInitialized) {
            return;
        }

        try {
            System.out.println("Starting driver initialization...");

            DesiredCapabilities caps = new DesiredCapabilities();

            caps.setCapability("platformName", ConfigManager.getProperty("platform.name", "Android"));
            caps.setCapability("appium:deviceName", ConfigManager.getProperty("device.name", "emulator-5554"));
            caps.setCapability("appium:automationName", ConfigManager.getProperty("automation.name", "UiAutomator2"));

            caps.setCapability("appium:appPackage", ConfigManager.getProperty("app.package", "com.tui.qa.challenge"));
            caps.setCapability("appium:appActivity", ConfigManager.getProperty("app.activity", "com.tui.qa.challenge.MainActivity"));

            caps.setCapability("appium:newCommandTimeout", 180);
            caps.setCapability("appium:autoGrantPermissions", true);

            // Connect to Appium
            URL url = new URL(ConfigManager.getProperty("appium.server.url", "http://127.0.0.1:4723"));
            System.out.println("Connecting to Appium server at: " + url);

            // Create driver
            System.out.println("Creating Android driver with capabilities: " + caps);
            AndroidDriver androidDriver = new AndroidDriver(url, caps);

            if (androidDriver != null) {
                androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                setDriver(androidDriver);
                isInitialized = true;
                System.out.println("Driver initialized successfully");
            }
        } catch (Exception e) {
            System.out.println("Failed to initialize driver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize driver: " + e.getMessage());
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                System.out.println("Quitting driver...");
                driver.quit();
                System.out.println("Driver quit successfully");
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver = null;
                isInitialized = false;
            }
        }
    }
}