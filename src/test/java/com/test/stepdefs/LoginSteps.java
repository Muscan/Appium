package com.test.stepdefs;

import com.test.pages.HomePage;
import com.test.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {
    private LoginPage loginPage;
    private HomePage homePage;

    public LoginSteps() {
        loginPage = new LoginPage();
        homePage = new HomePage();
    }


    @Given("the app is launched")
    public void theAppIsLaunched() {
        System.out.println("App is launched and ready for testing");
        boolean loginVisible = loginPage.isUsernameFieldDisplayed();
        System.out.println("Username field visible: " + loginVisible);

        if (!loginVisible) {
            System.out.println("WARNING: Login screen elements not found");
        }
    }

    @When("I enter username {string} and password {string}")
    public void iEnterUsernameAndPassword(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        System.out.println("Credentials entered");
    }

    @When("I enter date of birth {string}")
    public void iEnterDateOfBirth(String dob) {
        loginPage.enterDateOfBirth(dob);
    }

    @When("I tap on the submit button")
    public void iTapOnTheSubmitButton() {
        loginPage.clickSubmitButton();
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        // Removed verbose page source logging
        boolean isLoggedIn = loginPage.isUserLoggedIn();
        System.out.println("LOGIN STATUS: " + (isLoggedIn ? "SUCCESSFUL" : "FAILED"));

        Assert.assertTrue(isLoggedIn, "User should be successfully logged in");
    }

    @Then("I should be logged in despite invalid credentials")
    public void iShouldBeLoggedInDespiteInvalidCredentials() {
        boolean isLoggedIn = loginPage.isUserLoggedIn();
        System.out.println("LOGIN STATUS (with invalid credentials): " + (isLoggedIn ? "INCORRECTLY SUCCESSFUL" : "CORRECTLY FAILED"));
        Assert.assertTrue(isLoggedIn, "User should not be logged in with invalid credentials, but the app has a bug");
    }

    @When("I navigate to another screen")
    public void iNavigateToAnotherScreen() {
        homePage.clickHotelsTab();
    }

    @When("I press the back button")
    public void iPressTheBackButton() {
        homePage.navigateBack();
    }

    @Then("I should be logged out")
    public void iShouldBeLoggedOut() {
        boolean isStillLoggedIn = loginPage.isUserLoggedIn();
        System.out.println("STILL LOGGED IN: " + isStillLoggedIn);
        Assert.assertFalse(isStillLoggedIn, "User should still logged out after pressing back button");
    }
}