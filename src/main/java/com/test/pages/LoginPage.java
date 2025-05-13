package com.test.pages;

import com.test.repository.SelectorRepository.Common;
import com.test.repository.SelectorRepository.Login;
import com.test.repository.SelectorRepository.Navigation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage extends BasePage {

    public LoginPage() {
        super();
        logAction("LoginPage initialized");
    }

    public boolean isUsernameFieldDisplayed() {
        logAction("Checking if username field is displayed");

        By[] locators = {Login.USERNAME_FIELD, Login.USERNAME_FIELD_XPATH, Common.ANY_EDIT_TEXT};
        for (By locator : locators) {
            if (isElementPresent(locator, 2)) {
                return true;
            }
        }

        return false;
    }

    private WebElement findElementUsingMultipleStrategies(By... locators) {
        for (By locator : locators) {
            try {
                if (isElementPresent(locator, 1)) {
                    return driver.findElement(locator);
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    public void enterUsername(String username) {
        logAction("Attempting to enter username: " + username);

        try {
            // Try ID approach first
            if (isElementPresent(Login.USERNAME_FIELD, 2)) {
                sendKeys(Login.USERNAME_FIELD, username);
                return;
            }

            // Try XPath approach
            if (isElementPresent(Login.USERNAME_FIELD_XPATH, 2)) {
                sendKeys(Login.USERNAME_FIELD_XPATH, username);
                return;
            }

            // Try generic EditText approach
            if (isElementPresent(Common.ANY_EDIT_TEXT, 2)) {
                List<WebElement> editTexts = driver.findElements(Common.ANY_EDIT_TEXT);
                if (!editTexts.isEmpty()) {
                    editTexts.get(0).clear();
                    editTexts.get(0).sendKeys(username);
                    return;
                }
            }

            logError("Failed to enter username with all strategies",
                    new RuntimeException("All username entry attempts failed"));
        } catch (Exception e) {
            logError("Error entering username", e);
        }
    }

    public void enterPassword(String password) {
        logAction("Attempting to enter password");

        try {
            // Try ID approach first
            if (isElementPresent(Login.PASSWORD_FIELD, 2)) {
                sendKeys(Login.PASSWORD_FIELD, password);
                return;
            }

            // Try XPath approach
            if (isElementPresent(Login.PASSWORD_FIELD_XPATH, 2)) {
                sendKeys(Login.PASSWORD_FIELD_XPATH, password);
                return;
            }

            // Try generic EditText approach
            if (isElementPresent(Common.ANY_EDIT_TEXT, 2)) {
                List<WebElement> editTexts = driver.findElements(Common.ANY_EDIT_TEXT);
                if (editTexts.size() > 1) {
                    editTexts.get(1).clear();
                    editTexts.get(1).sendKeys(password);
                    return;
                }
            }

            logError("Failed to enter password with all strategies",
                    new RuntimeException("All password entry attempts failed"));
        } catch (Exception e) {
            logError("Error entering password", e);
        }
    }

    public void enterDateOfBirth(String dateOfBirth) {
        logAction("Entering date of birth: " + dateOfBirth);

        // Find date field using multiple strategies
        WebElement dateField = findElementUsingMultipleStrategies(
                Login.DATE_OF_BIRTH_FIELD,
                Login.DATE_OF_BIRTH_FIELD_XPATH,
                By.xpath("//android.widget.EditText[3]")
        );

        if (dateField == null) {
            logAction("Could not find date field");
            return;
        }

        // 1. Try direct text entry first
        try {
            dateField.clear();
            dateField.sendKeys(dateOfBirth);
            waitFor(500);
            return;
        } catch (Exception e) {
            logAction("Direct text entry failed, trying calendar picker");
        }

        // 2. Try clicking to open calendar
        try {
            dateField.click();
            waitFor(500);

            if (isElementPresent(Login.CALENDAR_ICON, 1)) {
                click(Login.CALENDAR_ICON);
            }

            if (isElementPresent(Login.DAY_SELECTOR, 3)) {
                click(Login.DAY_SELECTOR);

                if (isElementPresent(Login.CONFIRM_BUTTON, 2)) {
                    click(Login.CONFIRM_BUTTON);
                }
            }
        } catch (Exception e) {
            logAction("Calendar interaction failed");
        }
    }

    public void clickSubmitButton() {
        logAction("Attempting to click submit button");

        try {
            if (isElementPresent(Login.SUBMIT_BUTTON, 2)) {
                click(Login.SUBMIT_BUTTON);
                return;
            }

            if (isElementPresent(Login.SUBMIT_BUTTON_XPATH, 2)) {
                click(Login.SUBMIT_BUTTON_XPATH);
                return;
            }

            if (isElementPresent(Login.SUBMIT_TEXT, 2)) {
                click(Login.SUBMIT_TEXT);
                return;
            }

            if (isElementPresent(Common.ANY_BUTTON, 2)) {
                List<WebElement> buttons = driver.findElements(Common.ANY_BUTTON);
                if (!buttons.isEmpty()) {
                    buttons.get(buttons.size() - 1).click();
                    return;
                }
            }

            logError("Failed to click submit button with all strategies",
                    new RuntimeException("All submit button click attempts failed"));
        } catch (Exception e) {
            logError("Error clicking submit button", e);
        }
    }

    public boolean isUserLoggedIn() {
        logAction("Checking if user is logged in");

        boolean isLoggedIn = isElementPresent(Navigation.TABS_CONTAINER, 3);

        if (!isLoggedIn) {
            By[] postLoginIndicators = {
                    By.xpath("//*[contains(@text, 'All')]"),
                    By.xpath("//*[contains(@text, 'Hotels')]"),
                    By.xpath("//*[contains(@text, 'Holidays')]"),
                    Login.WELCOME_ELEMENT
            };

            for (By indicator : postLoginIndicators) {
                if (isElementPresent(indicator, 1)) {
                    isLoggedIn = true;
                    break;
                }
            }
        }

        return isLoggedIn;
    }

    public boolean login(String username, String password, String dateOfBirth) {
        logAction("Performing login with username: " + username);

        if (isUserLoggedIn()) {
            logAction("User already appears to be logged in");
            return true;
        }

        if (!isUsernameFieldDisplayed()) {
            logAction("Login screen not detected, attempting restart");
            restartApp();
            if (!isUsernameFieldDisplayed()) {
                logAction("Still can't find login screen after restart");
                return false;
            }
        }

        enterUsername(username);
        enterPassword(password);
        enterDateOfBirth(dateOfBirth);
        clickSubmitButton();

        return waitForLoginResult();
    }

    private boolean waitForLoginResult() {
        try {
            return waitUntil(driver -> {
                if (isElementPresent(Navigation.TABS_CONTAINER, 1)) {
                    return true;
                }

                By anyTab = By.xpath("//*[contains(@text, 'All') or contains(@text, 'Hotels') or contains(@text, 'Holidays')]");
                if (isElementPresent(anyTab, 1)) {
                    return true;
                }

                By errorMessage = By.xpath("//*[contains(@text, 'Error') or contains(@text, 'Failed') or contains(@text, 'Invalid')]");
                if (isElementPresent(errorMessage, 1)) {
                    return false;
                }

                By usernameField = By.id("username_input_field");
                if (isElementPresent(usernameField, 1)) {
                    return null; // Still on login screen, keep waiting
                }

                By welcomeElement = By.xpath("//*[contains(@text, 'Welcome') or contains(@text, 'Dashboard') or contains(@text, 'Home')]");
                if (isElementPresent(welcomeElement, 1)) {
                    return true;
                }

                return null;
            }, "login result", Duration.ofSeconds(5)); // Reduced from 8 seconds
        } catch (Exception e) {
            return isUserLoggedIn();
        }
    }

    public void restartApp() {
        logAction("Trying to reset app state");
        try {
            String appPackage = (String) driver.getCapabilities().getCapability("appium:appPackage");
            String appActivity = (String) driver.getCapabilities().getCapability("appium:appActivity");

            if (appPackage == null) appPackage = "com.tui.qa.challenge";
            if (appActivity == null) appActivity = "com.tui.qa.challenge.MainActivity";

            if (driver instanceof AndroidDriver) {
                try {
                    HashMap<String, String> args = new HashMap<>();
                    args.put("command", "am force-stop " + appPackage);
                    driver.executeScript("mobile: shell", args);
                    waitFor(1000);

                    HashMap<String, String> startArgs = new HashMap<>();
                    startArgs.put("command", "am start -n " + appPackage + "/" + appActivity);
                    driver.executeScript("mobile: shell", startArgs);

                    wait.until(d -> d.getPageSource().length() > 100);

                } catch (Exception e) {
                    logAction("Shell commands failed: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            logError("Failed to reset app", e);
        }
    }
}