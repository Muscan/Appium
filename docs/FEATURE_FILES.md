# Feature Files Documentation

This document provides detailed information about the test scenarios in the Cucumber feature files used in this framework.

## Overview of Feature Files

Based on the test runner and step definition files in our project, we have two main feature files:

1. **Login.feature** - Tests user authentication functionality
2. **HotelSearch.feature** - Tests hotel search and listing functionality

## Login.feature

The Login feature tests the user authentication process in the mobile app.

### Scenarios

#### 1. Login with valid credentials

```gherkin
@smoke @regression
Scenario: Login with valid credentials
  Given the app is launched
  When I enter username "testuser" and password "password123"
  And I enter date of birth "2025-05-01"
  And I tap on the submit button
  Then I should be logged in successfully
```

This scenario tests:
- Basic app launch
- Username and password entry
- Date of birth entry
- Login submission
- Successful login verification

#### 2. Login with invalid credentials

```gherkin
@regression
Scenario: Login with invalid credentials
  Given the app is launched
  When I enter username "wronguser" and password "wrongpass"
  And I enter date of birth "2025-05-01"
  And I tap on the submit button
  Then I should be logged in despite invalid credentials
```

Note: Based on the step definition `iShouldBeLoggedInDespiteInvalidCredentials()`, this test appears to be checking for a bug where the app allows login with invalid credentials.

#### 3. Session management

```gherkin
@regression
Scenario: User is logged out when navigating back
  Given the app is launched
  When I enter username "testuser" and password "password123"
  And I enter date of birth "2025-05-01"
  And I tap on the submit button
  And I navigate to another screen
  And I press the back button
  Then I should be logged out
```

This scenario tests the app's session management by:
- Logging in
- Navigating to another screen
- Pressing the back button
- Verifying user is logged out

## HotelSearch.feature

The HotelSearch feature tests hotel search and listing functionality.

### Scenarios

#### 1. View hotel listings

```gherkin
@smoke @regression
Scenario: View hotel listings
  Given I am logged in with valid credentials
  When I tap on the "Hotels" tab
  Then I should see hotel listings
```

This scenario tests:
- User login as a prerequisite
- Navigation to the Hotels tab
- Verification that hotel listings are displayed

#### 2. View hotel details

```gherkin
@regression
Scenario: View hotel details
  Given I am logged in with valid credentials
  When I tap on the "Hotels" tab
  Then I should see hotel details including name, location, and price
```

This scenario verifies that:
- User can log in
- Navigate to the Hotels tab
- See detailed hotel information including name, location, and price

## Tags Used

The feature files use the following tags to categorize tests:

### @smoke

These are critical test scenarios that validate core functionalities. They are meant to be quick to execute and provide high-level confidence that the application is working as expected.

Example usage:
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### @regression

These are more comprehensive test scenarios that validate the application's functionality in depth. Regression tests ensure that new changes don't break existing functionality.

Example usage:
```bash
mvn test -Dcucumber.filter.tags="@regression"
```

### @bug

These scenarios explicitly test known issues or bugs in the application. These tests are expected to pass (because the bugs exist), but in an ideal implementation, they should fail.

Example usage:
```bash
mvn test -Dcucumber.filter.tags="@bug"
```

### Tag Combinations

You can combine tags using logical operators:

```bash
# Run tests that have either tag
mvn test -Dcucumber.filter.tags="@smoke or @regression"

# Run tests that have both tags
mvn test -Dcucumber.filter.tags="@smoke and @regression"

# Run tests that are not marked as slow
mvn test -Dcucumber.filter.tags="not @slow"
```

## Step Definitions

The step definitions for these features are implemented in:

1. `com.test.stepdefs.LoginSteps` - Handles login-related steps
2. `com.test.stepdefs.HotelSearchSteps` - Handles hotel search-related steps

## Adding New Feature Files

To add a new feature file:

1. Create a new `.feature` file in `src/test/resources/features/`
2. Implement step definitions in `com.test.stepdefs` package
3. Create a new runner class in `com.test.runners` package if needed, or add the feature file to an existing runner

Example of a new runner class:

```java
@CucumberOptions(
    features = {
        "src/test/resources/features/NewFeature.feature"
    },
    glue = {"com.test.stepdefs", "com.test.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/new-feature-cucumber-pretty.html",
        "json:target/cucumber-reports/new-feature-cucumber-report.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    tags = "@smoke or @regression"
)
public class NewFeatureRunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
```
