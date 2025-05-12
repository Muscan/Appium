package com.test.hooks;

import com.test.drivers.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Hooks {
    private static final int RETRY_DELAY_MS = 3000;
    private static final int MAX_DRIVER_INIT_RETRIES = 2;

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());

        for (int attempt = 0; attempt <= MAX_DRIVER_INIT_RETRIES; attempt++) {
            try {
                AppiumDriver driver = DriverManager.getDriver();

                try {
                    System.out.println("Restarting app for clean state...");

                    String appPackage = (String) driver.getCapabilities().getCapability("appium:appPackage");
                    String appActivity = (String) driver.getCapabilities().getCapability("appium:appActivity");

                    if (appPackage == null) appPackage = "com.tui.qa.challenge";
                    if (appActivity == null) appActivity = "com.tui.qa.challenge.MainActivity";

                    System.out.println("App package: " + appPackage + ", activity: " + appActivity);

                    if (driver instanceof AndroidDriver) {
                        try {
                            Map<String, Object> args = new HashMap<>();
                            args.put("command", "am force-stop " + appPackage);
                            driver.executeScript("mobile: shell", args);

                            Thread.sleep(1000);

                            args = new HashMap<>();
                            args.put("command", "am start -n " + appPackage + "/" + appActivity);
                            driver.executeScript("mobile: shell", args);

                            new WebDriverWait(driver, Duration.ofSeconds(5))
                                    .until(d -> d.getPageSource().length() > 100);

                            System.out.println("App restarted via shell command");
                        } catch (Exception shellEx) {
                            System.out.println("Shell commands failed, using driver reinitialization");

                            DriverManager.quitDriver();

                            driver = DriverManager.getDriver();

                            new WebDriverWait(driver, Duration.ofSeconds(5))
                                    .until(d -> d.getPageSource().length() > 100);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not restart app: " + e.getMessage() + " - continuing with test");
                }

                System.out.println(
                        "Driver initialized successfully" +
                                (attempt > 0 ? " on attempt " + (attempt + 1) : "")
                );
                break;
            } catch (Exception e) {
                if (attempt == MAX_DRIVER_INIT_RETRIES) {
                    System.out.println("Failed to initialize driver after " + MAX_DRIVER_INIT_RETRIES +
                            " attempts. Error: " + e.getMessage());
                } else {
                    System.out.println("Retrying driver initialization...");
                    DriverManager.quitDriver();
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    @AfterStep
    public void addScreenshotOnFailedStep(Scenario scenario) {
        // take screenshots for failed steps or final steps of each scenario
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Finished scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());

        try {
            // Take a final screenshot regardless of pass/fail
            final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Final Screenshot");

            DriverManager.quitDriver();
        } catch (Exception e) {
            System.out.println("Error during teardown: " + e.getMessage());
        }
    }
}