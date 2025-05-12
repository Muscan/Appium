package com.test.repository;

import org.openqa.selenium.By;

public class SelectorRepository {

    public static class Common {
        public static final By BACK_BUTTON = By.id("back_button");
        public static final By LOADING_INDICATOR = By.id("loading_indicator");
        public static final By ANY_EDIT_TEXT = By.className("android.widget.EditText");
        public static final By ANY_BUTTON = By.className("android.widget.Button");
    }

    // Navigation elements
    public static class Navigation {
        // Tab selectors
        public static final By ALL_TAB = By.xpath("//android.view.View[@content-desc='All']");
        public static final By HOTELS_TAB = By.xpath("//android.view.View[@content-desc='Hotels']");
        public static final By HOLIDAYS_TAB = By.xpath("//android.view.View[@content-desc='Holidays']");
        public static final By TABS_CONTAINER = By.xpath("//android.view.View[@content-desc='All' or @content-desc='Hotels' or @content-desc='Holidays']");

        // Tab selectors with ID
        public static final By ALL_TAB_BY_ID = By.id("top_app_bar_all_tab");
        public static final By HOTELS_TAB_BY_ID = By.id("top_app_bar_hotels_tab");
        public static final By HOLIDAYS_TAB_BY_ID = By.id("top_app_bar_holidays_tab");

        // Content root
        public static final By CONTENT_ROOT = By.id("search_result_content_screen_root");
    }

    // Login page selectors
    public static class Login {
        // ID-based locators
        public static final By USERNAME_FIELD = By.id("username_input_field");
        public static final By PASSWORD_FIELD = By.id("password_input_field");
        public static final By DATE_OF_BIRTH_FIELD = By.id("date_of_birth_field");
        public static final By SUBMIT_BUTTON = By.id("login_form_submit_button");

        // XPath-based locators (fallbacks)
        public static final By USERNAME_FIELD_XPATH = By.xpath("//android.widget.EditText[contains(@text, 'Username') or contains(@hint, 'Username')]");
        public static final By PASSWORD_FIELD_XPATH = By.xpath("//android.widget.EditText[contains(@text, 'Password') or contains(@hint, 'Password')]");
        public static final By DATE_OF_BIRTH_FIELD_XPATH = By.xpath("//android.widget.EditText[contains(@text, 'Date of birth') or contains(@hint, 'Date of birth')]");
        public static final By SUBMIT_BUTTON_XPATH = By.xpath("//android.view.View[.//android.widget.TextView[@text='Submit']]");
        public static final By SUBMIT_TEXT = By.xpath("//*[@text='Submit']");

        // Calendar related
        public static final By CALENDAR_ICON = By.xpath("//android.view.View[@resource-id='date_of_birth_field_calendar_icon']");
        public static final By DAY_SELECTOR = By.xpath("//android.widget.TextView[contains(@text, 'day')]");
        public static final By CONFIRM_BUTTON = By.xpath("//*[contains(@text, 'Confirm') or contains(@text, 'OK')]");

        // Login status indicators
        public static final By LOGIN_TITLE = By.xpath("//android.widget.TextView[@text='Log in']");
        public static final By ERROR_MESSAGE = By.xpath("//*[contains(@text, 'Error') or contains(@text, 'Failed') or contains(@text, 'Invalid')]");
        public static final By WELCOME_ELEMENT = By.xpath("//*[contains(@text, 'Welcome') or contains(@text, 'Dashboard') or contains(@text, 'Home')]");
    }

    // Listing selectors
    public static class Listings {
        // Hotel card selectors
        public static final By HOTEL_CARD = By.xpath("//android.view.ViewGroup[.//android.widget.TextView[contains(@text, 'Hotel:') or contains(@text, 'H10 London')]]");
        public static final By HOTEL_CARD_BY_ID = By.xpath("//android.view.View[contains(@resource-id, 'content_card_root_')]");

        // Hotel details
        public static final By HOTEL_NAME = By.xpath("//android.widget.TextView[contains(@text, 'Hotel:') or contains(@text, 'H10 London')]");
        public static final By HOTEL_NAME_BY_ID = By.xpath("//android.widget.TextView[contains(@resource-id, 'content_card_hotel_name_')]");

        public static final By LOCATION = By.xpath("//android.widget.TextView[contains(@text, 'in ')]");
        public static final By LOCATION_BY_ID = By.xpath("//android.widget.TextView[contains(@resource-id, 'content_card_destination_')]");

        public static final By PRICE = By.xpath("//android.widget.TextView[contains(@text, '$')]");

        public static final By RATING = By.xpath("//android.widget.TextView[contains(@resource-id, 'content_card_rating_')]");
        public static final By BOARD_TYPE = By.xpath("//android.widget.TextView[contains(@resource-id, 'content_card_board_type_')]");

        // Generic content card element for checking loading
        public static final By CONTENT_CARD = By.xpath("//*[contains(@resource-id, 'content_card_')]");
    }
}