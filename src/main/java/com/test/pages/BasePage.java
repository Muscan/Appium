package com.test.pages;

import com.test.drivers.DriverManager;
import com.test.utils.ConfigManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;
    private static final boolean VERBOSE_LOGGING = false;

    public BasePage() {
        this.driver = DriverManager.getDriver();

        int defaultWaitSeconds = ConfigManager.getIntProperty("default.wait.seconds", 5);
        int shortWaitSeconds = ConfigManager.getIntProperty("short.wait.seconds", 2);

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitSeconds));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(shortWaitSeconds));
    }

    protected <T> T waitUntil(Function<AppiumDriver, T> condition, String description, Duration timeout) {
        return new FluentWait<>(driver)
            .withTimeout(timeout)
            .pollingEvery(Duration.ofMillis(250))
            .ignoring(NoSuchElementException.class)
            .ignoring(StaleElementReferenceException.class)
            .withMessage("Waiting for: " + description)
            .until(condition);
    }

    protected void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
            logAction("Clicked: " + locator);
        } catch (Exception e) {
            try {
                driver.findElement(locator).click();
                logAction("Clicked (retry): " + locator);
            } catch (Exception ex) {
                logError("Failed to click: " + locator, ex);
            }
        }
    }

    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.clear();
            element.sendKeys(text);
            logAction("Entered text: " + maskSensitiveData(locator, text));
        } catch (Exception e) {
            try {
                WebElement element = driver.findElement(locator);
                element.clear();
                element.sendKeys(text);
                logAction("Entered text (retry): " + maskSensitiveData(locator, text));
            } catch (Exception ex) {
                logError("Failed to enter text: " + locator, ex);
            }
        }
    }

    protected String getText(By locator) {
        try {
            String text = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
            logAction("Got text: " + text);
            return text;
        } catch (Exception e) {
            logError("Failed to get text: " + locator, e);
            return "";
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            boolean displayed = shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            logAction("Element display check: " + locator + ", displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresent(By locator, int timeoutSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            customWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected void navigateBack() {
        try {
            driver.navigate().back();
            logAction("Navigated back");
        } catch (Exception e) {
            logError("Failed to navigate back", e);
        }
    }

    public void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            if (VERBOSE_LOGGING) {
                logAction("Waited for " + milliseconds + " ms");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logError("Wait interrupted", e);
        }
    }

    private String maskSensitiveData(By locator, String text) {
        String locatorString = locator.toString().toLowerCase();
        if (locatorString.contains("password")) {
            return "********";
        }
        return text;
    }

    // Condensed logging
    protected void logAction(String message) {
        if (VERBOSE_LOGGING) {
            System.out.println("[ACTION] " + message);
        }
    }

    protected void logError(String message, Exception e) {
        System.out.println("[ERROR] " + message + " - " + e.getMessage());
    }
}