package com.test.stepdefs;

import com.test.pages.HomePage;
import com.test.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;


public class HotelSearchSteps {
    private HomePage homePage;
    private LoginPage loginPage;

    public HotelSearchSteps() {
        homePage = new HomePage();
        loginPage = new LoginPage();
    }

    @Given("I am logged in with valid credentials")
    public void i_am_logged_in_with_valid_credentials() {
        System.out.println("Logging in with valid credentials...");

        // Using the login method from LoginPage
        loginPage.login("testuser", "password123", "2025-05-01");

        // Wait for login to complete and verify
        loginPage.waitFor(2000);
        boolean isLoggedIn = loginPage.isUserLoggedIn();
        Assert.assertTrue(isLoggedIn, "User should be logged in successfully");
        System.out.println("Login successful with valid credentials");
    }

    @When("I tap on the {string} tab")
    public void i_tap_on_the_tab(String tabName) {
        System.out.println("Tapping on the " + tabName + " tab");

        // Click on the appropriate tab based on the name
        switch (tabName.toLowerCase()) {
            case "all":
                homePage.clickAllTab();
                break;
            case "hotels":
                homePage.clickHotelsTab();
                break;
            case "holidays":
                homePage.clickHolidaysTab();
                break;
            default:
                throw new IllegalArgumentException("Unknown tab name: " + tabName);
        }

        // Wait again for pages to load...
        homePage.waitFor(2000);
        System.out.println("Tapped on " + tabName + " tab");
    }

    @Then("I should see hotel listings")
    public void i_should_see_hotel_listings() {
        System.out.println("Verifying hotel listings are displayed");

        // Check if there are hotel cards displayed
        int hotelCount = homePage.getHotelCardCount();
        Assert.assertTrue(hotelCount > 0, "At least one hotel should be displayed");
        System.out.println("Found " + hotelCount + " hotel listings");
    }

    @Then("I should see hotel details including name, location, and price")
    public void i_should_see_hotel_details_including_name_location_and_price() {
        System.out.println("Verifying hotel details are displayed");

        // Get details of the first hotel
        String hotelName = homePage.getFirstHotelName();
        String hotelDestination = homePage.getFirstHotelDestination();
        String hotelPrice = homePage.getFirstHotelPrice();

        // Verify the details are not empty
        Assert.assertFalse(hotelName.isEmpty(), "Hotel name should not be empty");
        Assert.assertFalse(hotelDestination.isEmpty(), "Hotel destination should not be empty");
        Assert.assertFalse(hotelPrice.isEmpty(), "Hotel price should not be empty");

        System.out.println("Hotel details verified:");
        System.out.println("- Name: " + hotelName);
        System.out.println("- Destination: " + hotelDestination);
        System.out.println("- Price: " + hotelPrice);
    }
}