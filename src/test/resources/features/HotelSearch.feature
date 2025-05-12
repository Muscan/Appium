Feature: Hotel Search and Display
  As a user of the application
  I want to browse hotels and holidays
  So that I can view available options

  Background:
    Given the app is launched
    And   I am logged in with valid credentials

  @smoke
  Scenario: Browse hotels by clicking on Hotels tab
    When I tap on the "Hotels" tab
    Then I should see hotel listings
    And  I should see hotel details including name, location, and price

  @smoke
  Scenario: Browse hotels by clicking on Holidays tab
    When I tap on the "Holidays" tab
    Then I should see hotel listings
    And  I should see hotel details including name, location, and price

  @smoke
  Scenario: Browse all listings by clicking on All tab
    When I tap on the "All" tab
    Then I should see hotel listings
    And  I should see hotel details including name, location, and price