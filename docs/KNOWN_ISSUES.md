# Known Issues

## Authentication Security Issues

The application has several security vulnerabilities in its authentication system:

### 1. Minimal Input Validation

The login form has extremely minimal validation:

- **Username field**: Only checks that something is entered (even a single space character)
- **Password field**: Only checks that something is entered (even a single space character)
- **Date of Birth field**: Only checks that a date is selected, without validation against user records

### 2. Improper Session Management

As shown in the `User is logged out when navigating back` test:
- The session state is not properly maintained when navigating through the app
- Users can lose their session by simply pressing the back button

### 3. Missing Authentication Failures

The application doesn't properly reject invalid credentials:
- Login succeeds even with non-existent usernames
- Login succeeds with incorrect passwords
- Login succeeds with any date of birth value

## UI Issues

### 1. Inconsistent UI Elements

- Hotel listings may appear under different selectors (requires multiple selector strategies)
- Navigation tabs may have inconsistent IDs or accessibility labels

## Test Cases for Security Issues

These issues are being explicitly tested with the following scenarios in Login.feature:

```gherkin
@bug @regression
Scenario: Login with space as username
  Given the app is launched
  When I enter username " " and password "password123"
  And I enter date of birth "2025-05-01"
  And I tap on the submit button
  Then I should be logged in despite invalid credentials

@bug @regression
Scenario: Login with space as password
  Given the app is launched
  When I enter username "testuser" and password " "
  And I enter date of birth "2025-05-01"
  And I tap on the submit button
  Then I should be logged in despite invalid credentials

@bug @regression
Scenario: Login with any date of birth
  Given the app is launched
  When I enter username "testuser" and password "password123"
  And I enter date of birth "01/01/2020"
  And I tap on the submit button
  Then I should be logged in without date validation
```

## Impact

These security issues could lead to:
1. Unauthorized access to user accounts
2. Potential data breaches
3. Poor user experience due to unexpected session termination

## Running Tests for Known Issues

To run only the tests that verify these known issues:

```bash
mvn test -Dcucumber.filter.tags="@bug"
```

To run regression tests excluding the known issues:

```bash
mvn test -Dcucumber.filter.tags="@regression and not @bug"
```