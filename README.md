# Mobile App Automation Framework

A BDD automation framework for testing mobile applications using Appium, Cucumber, and Java.

## Overview

This framework provides automated testing for mobile applications, designed to test login functionality and hotel search features of a travel app. It uses behavior-driven development with Cucumber and Appium for mobile automation.

## Technologies Used

- **Java 8** - Programming language
- **Appium 8.3.0** - Mobile automation tool
- **Cucumber 7.11.1** - BDD framework
- **TestNG 7.4.0** - Test runner
- **Selenium 4.8.3** - WebDriver implementation
- **Maven** - Build and dependency management
- **Extent Reports 5.0.9** - Test reporting

## Setup Instructions

### Prerequisites

- Java JDK 8 or higher
- Maven
- Appium 2.0+
- Android Studio with emulator
- Android SDK

### Quick Start

1. Start the Appium server:
   ```
   appium --relaxed-security
   ```

2. Run tests via IntelliJ IDEA:
    - Navigate to `src/test/java/com/test/runners/`
    - Run `LoginRunnerTest.java` or `HotelSearchRunnerTest.java`

3. Run tests via Terminal:
   ```
   # Run all tests
   mvn clean test

   # Run specific tags
   mvn test -Dcucumber.filter.tags="@smoke"
   ```

4. Run using provided scripts:
   ```
   # Windows
   run-tests.bat @smoke

   # Linux/Mac
   ./run-tests.sh @smoke
   ```

## Project Structure

```
src/
├── main/java/com/test/
│   ├── drivers/       - Driver management
│   ├── pages/         - Page objects
│   ├── repository/    - Element selectors
│   └── utils/         - Utility classes
└── test/
    ├── java/com/test/
    │   ├── hooks/     - Test hooks
    │   ├── runners/   - Test runners
    │   └── stepdefs/  - Step definitions
    └── resources/
        ├── apps/      - Application APKs
        ├── config/    - Configuration files
        ├── features/  - Cucumber feature files
        └── extent-config/ - Reporting configuration
```

## Test Features

### Login Feature
Tests login functionality with various credentials and scenarios

### Hotel Search Feature
Tests hotel search functionality and verifies hotel listings and details

## Viewing Reports

After test execution, reports are generated in:

- HTML Reports: `target/cucumber-reports/*.html`
- JSON Reports: `target/cucumber-reports/*.json`
- Extent Reports: `target/cucumber-reports/extent-spark.html`
- Screenshots: `target/screenshots/[timestamp]/`

## Configuration

The framework can be configured using:

- `config.properties`: Basic configuration for Appium and test execution
- `spark-config.xml`: Reporting configuration

## Documentation

For more detailed information, please refer to:

- [Command Reference Guide](docs/COMMAND_REFERENCE.md) - Quick reference for common commands
- [Feature Files Documentation](docs/FEATURE_FILES.md) - Details on test scenarios
- [Git Integration Guide](docs/GIT_INTEGRATION.md) - Setting up Git