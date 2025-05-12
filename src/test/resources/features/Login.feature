Feature: Login Functionality
  As a user of the application
  I want to login with my credentials
  So that I can access my account

  Background:
    Given the app is launched

  @smoke
  Scenario: Successful login with valid credentials
    When I enter username "testuser" and password "password123"
    And  I enter date of birth "2025-05-01"
    And  I tap on the submit button
    Then I should be logged in successfully

  @regression @bug @authentication
  Scenario: Login with invalid credentials is incorrectly accepted
    When I enter username "xzxz" and password "***"
    And  I enter date of birth "2025-05-01"
    And  I tap on the submit button
    Then I should be logged in despite invalid credentials

  @regression @bug @authentication
  Scenario: Login with space as username
    When I enter username " " and password "password123"
    And  I enter date of birth "2025-05-01"
    And  I tap on the submit button
    Then I should be logged in despite invalid credentials

  @regression @bug @authentication
  Scenario: Login with space as password
    When I enter username "testuser" and password " "
    And  I enter date of birth "2025-05-01"
    And  I tap on the submit button
    Then I should be logged in despite invalid credentials

  @regression @bug @session-management
  Scenario: Back button log out user
    When I enter username "testuser" and password "password123"
    And  I enter date of birth "2025-05-01"
    And  I tap on the submit button
    Then I should be logged in successfully
    When I navigate to another screen
    And  I press the back button
    Then I should be logged out