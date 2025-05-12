# Mobile App Automation Framework

A BDD automation framework for testing mobile applications using Appium, Cucumber, and Java.

## Overview

This framework provides automated testing capabilities for mobile applications, specifically designed to test login functionality and hotel search features of a travel application. It uses a behavior-driven development approach with Cucumber to define test scenarios in natural language and Appium for mobile test automation.

## Technologies Used

- **Java 8**: Programming language
- **Appium 8.3.0**: Mobile automation tool
- **Cucumber 7.11.1**: BDD framework
- **TestNG 7.4.0**: Test runner
- **Selenium 4.8.3**: WebDriver implementation
- **Maven**: Build and dependency management
- **Extent Reports 5.0.9**: Test reporting with Cucumber adapter 1.13.0
- **Log4j 2.20.0**: Logging framework

## Project Structure

```
AppiumCucumberFramework/
├── src/
│   ├── main/java/com/test/
│   │   ├── drivers/            - Driver management
│   │   ├── pages/              - Page objects
│   │   ├── repository/         - Element selectors
│   │   └── utils/              - Utility classes
│   └── test/
│       ├── java/com/test/
│       │   ├── hooks/          - Test hooks
│       │   ├── runners/        - Test runners
│       │   └── stepdefs/       - Step definitions
│       └── resources/
│           ├── apps/           - Application APKs
│           ├── config/         - Configuration files
│           ├── features/       - Cucumber feature files
│           ├── extent-config/  - Reporting configuration
│   
└── target/
    ├── cucumber-reports/       - Test reports
    └── screenshots/            - Test screenshots
```

## Setup Instructions

### Prerequisites

- Java JDK 8 or higher
- Maven
- Appium 2.0+
- Android Studio with emulator
- Android SDK

### Environment Setup

1. Install Java JDK and set JAVA_HOME environment variable
2. Install Maven and set MAVEN_HOME environment variable
3. Install Node.js and npm
4. Install Appium using npm:
   ```
   npm install -g appium
   ```
5. Install Appium drivers:
   ```
   appium driver install uiautomator2
   ```
6. Set up Android Studio and Android SDK
7. Create an Android emulator or connect a physical device

## Running Tests

### Starting Appium Server

Before running tests, start the Appium server:

```
appium
```

### Running Tests via IntelliJ IDEA

1. Open the project in IntelliJ IDEA
2. Navigate to the runner classes:
   - `src/test/java/com/test/runners/LoginRunnerTest.java`
   - `src/test/java/com/test/runners/HotelSearchRunnerTest.java`
3. Right-click on the runner class and select "Run" to execute tests

### Running Tests via Terminal

#### Using Maven commands:

```
# Navigate to project root directory
cd path/to/AppiumCucumberFramework

# Clean and run all tests
mvn clean test

# Run tests with specific tags
mvn test -Dcucumber.filter.tags="@smoke"
```

#### Using provided scripts:

**Windows:**
```
# Run smoke tests
run-tests.bat @smoke

# Run regression tests
run-tests.bat @regression
```

**Linux/Mac:**
```
# Make script executable
chmod +x run-tests.sh

# Run smoke tests
./run-tests.sh @smoke

# Run regression tests
./run-tests.sh @regression
```

## Viewing Reports

After test execution, reports are generated in:

- **Cucumber Reports**: `target/cucumber-reports/`
- **HTML Reports**: `target/cucumber-reports/hotel-cucumber-pretty.html` and `target/cucumber-reports/login-cucumber-pretty.html`
- **JSON Reports**: `target/cucumber-reports/hotel-cucumber-report.json` and `target/cucumber-reports/login-cucumber-report.json`
- **Extent Reports**: `target/cucumber-reports/extent-spark.html`
- **Screenshots**: `target/screenshots/[timestamp]/`

The report files can be opened in any web browser.

## Configuration

The framework can be configured using:

- `src/test/resources/config/config.properties`: Basic configuration for Appium and test execution
- `src/test/resources/extent-config/spark-config.xml`: Reporting configuration

## Test Features

### Login Feature
Tests login functionality with various credentials and scenarios.

### Hotel Search Feature
Tests hotel search functionality and verifies hotel listings and details.

## Documentation

For more detailed information, please refer to:

- [Command Reference Guide](docs/COMMAND_REFERENCE.md) - Quick reference for common commands
- [Feature Files Documentation](docs/FEATURE_FILES.md) - Details on test scenarios
- [Git Integration Guide](docs/GIT_CLOUD_INTEGRATION.md) - Setting up Git and CI/CD
- [Known Issues](docs/KNOWN_ISSUES.md) - Documentation of known bugs and security issues