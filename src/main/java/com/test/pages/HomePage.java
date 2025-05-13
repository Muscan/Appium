package com.test.pages;

import com.test.repository.SelectorRepository.Navigation;
import com.test.repository.SelectorRepository.Listings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HomePage extends BasePage {

    public HomePage() {
        super();
        logAction("HomePage initialized");
    }

    @Override
    public void navigateBack() {
        try {
            driver.navigate().back();
            logAction("Navigated back");
            waitFor(500); // Small wait after navigation
        } catch (Exception e) {
            logError("Failed to navigate back", e);
        }
    }

    /**
     * Verifies if the search results page is displayed
     */
    public boolean isSearchResultsDisplayed() {
        // Check if any of the tab elements are visible with short timeouts
        boolean isDisplayed = isElementPresent(Navigation.ALL_TAB, 2) ||
                isElementPresent(Navigation.HOTELS_TAB, 2) ||
                isElementPresent(Navigation.HOLIDAYS_TAB, 2) ||
                isElementPresent(Navigation.ALL_TAB_BY_ID, 2) ||
                isElementPresent(Navigation.HOTELS_TAB_BY_ID, 2) ||
                isElementPresent(Navigation.HOLIDAYS_TAB_BY_ID, 2);

        // Additional check to ensure we're actually on results screen and not just seeing tabs
        if (isDisplayed) {
            // If we see tabs but no content, we might not be fully loaded
            boolean hasContent = isElementPresent(Listings.CONTENT_CARD, 2) ||
                    getHotelCardCount() > 0;

            if (!hasContent) {
                logAction("Tabs visible but no content found - might not be on results page");
                return false;
            }
        }

        return isDisplayed;
    }

    /**
     * Unified tab navigation method
     */
    public void clickTab(String tabName) {
        logAction("Clicking on " + tabName + " tab");

        By primarySelector;
        By backupSelector;

        switch (tabName.toLowerCase()) {
            case "all":
                primarySelector = Navigation.ALL_TAB_BY_ID;
                backupSelector = Navigation.ALL_TAB;
                break;
            case "hotels":
                primarySelector = Navigation.HOTELS_TAB_BY_ID;
                backupSelector = Navigation.HOTELS_TAB;
                break;
            case "holidays":
                primarySelector = Navigation.HOLIDAYS_TAB_BY_ID;
                backupSelector = Navigation.HOLIDAYS_TAB;
                break;
            default:
                throw new IllegalArgumentException("Unknown tab: " + tabName);
        }

        try {
            if (isElementDisplayed(primarySelector)) {
                click(primarySelector);
            } else if (isElementDisplayed(backupSelector)) {
                click(backupSelector);
            } else {
                logAction(tabName + " tab not found with any selectors");
            }

            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(Listings.CONTENT_CARD));
            } catch (Exception e) {
                waitFor(500);
            }

        } catch (Exception e) {
            logError("Failed to click " + tabName + " tab", e);
        }
    }

    // Other methods remain the same
    public void clickAllTab() {
        clickTab("all");
    }

    public void clickHotelsTab() {
        clickTab("hotels");
    }

    public void clickHolidaysTab() {
        clickTab("holidays");
    }


    public int getHotelCardCount() {
        try {
            List<WebElement> cards = driver.findElements(Listings.HOTEL_CARD_BY_ID);

            if (cards.isEmpty()) {
                cards = driver.findElements(Listings.HOTEL_CARD);
            }

            return cards.size();
        } catch (Exception e) {
            logError("Failed to get hotel card count", e);
            return 0;
        }
    }

    public String getFirstHotelInfo(String infoType) {
        By selector;
        By fallbackSelector;

        switch (infoType) {
            case "name":
                selector = Listings.HOTEL_NAME_BY_ID;
                fallbackSelector = Listings.HOTEL_NAME;
                break;
            case "destination":
                selector = Listings.LOCATION_BY_ID;
                fallbackSelector = Listings.LOCATION;
                break;
            case "price":
                selector = Listings.PRICE;
                fallbackSelector = null;
                break;
            case "rating":
                selector = Listings.RATING;
                fallbackSelector = null;
                break;
            case "boardType":
                selector = Listings.BOARD_TYPE;
                fallbackSelector = null;
                break;
            default:
                throw new IllegalArgumentException("Unknown info type: " + infoType);
        }

        try {
            if (isElementPresent(selector, 2)) {
                return getText(selector);
            }

            if (fallbackSelector != null && isElementPresent(fallbackSelector, 1)) {
                return getText(fallbackSelector);
            }

            return "";
        } catch (Exception e) {
            logError("Failed to get " + infoType, e);
            return "";
        }
    }

    public String getFirstHotelName() {
        return getFirstHotelInfo("name");
    }

    public String getFirstHotelDestination() {
        return getFirstHotelInfo("destination");
    }

    public String getFirstHotelPrice() {
        return getFirstHotelInfo("price");
    }
}