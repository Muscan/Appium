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
            DesiredCapabilities caps = new DesiredCapabilities();

            // Basic capabilities from config
            caps.setCapability("platformName", ConfigManager.getProperty("platform.name", "Android"));
            caps.setCapability("appium:deviceName", ConfigManager.getProperty("device.name", "emulator-5554"));
            caps.setCapability("appium:automationName", ConfigManager.getProperty("automation.name", "UiAutomator2"));
            caps.setCapability("appium:noReset", ConfigManager.getBooleanProperty("no.reset", true));

            // Basic app info
            caps.setCapability("appium:appPackage", ConfigManager.getProperty("app.package", "com.tui.qa.challenge"));
            caps.setCapability("appium:appActivity", ConfigManager.getProperty("app.activity", "com.tui.qa.challenge.MainActivity"));

            // Some fireworks to improve the speed...
            caps.setCapability("appium:ignoreUnimportantViews", ConfigManager.getBooleanProperty("ignore.unimportant.views", true));
            caps.setCapability("appium:disableWindowAnimation", ConfigManager.getBooleanProperty("disable.window.animation", true));

            caps.setCapability("appium:newCommandTimeout", 180);
            caps.setCapability("appium:adbExecTimeout", 60000);
            caps.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
            caps.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
            caps.setCapability("appium:androidInstallTimeout", 90000);
            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:autoGrantPermissions", true);
            caps.setCapability("appium:skipUnlock", true);

            // Connect to Appium
            URL url = new URL(ConfigManager.getProperty("appium.server.url", "http://127.0.0.1:4723"));

            // Create driver
            AndroidDriver androidDriver = new AndroidDriver(url, caps);

            if (androidDriver != null) {
                int implicitWaitMs = ConfigManager.getIntProperty("implicit.wait.ms", 500);
                androidDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(implicitWaitMs));
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
                driver.quit();
            } catch (Exception e) {
                System.out.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver = null;
                isInitialized = false;
            }
        }
    }
}