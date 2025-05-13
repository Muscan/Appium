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
│   │   │   └── DriverManager   - Mobile driver configuration
│   │   ├── pages/              - Page objects
│   │   │   ├── BasePage        - Common page functions
│   │   │   ├── HomePage        - Home screen interactions
│   │   │   └── LoginPage       - Login screen interactions
│   │   ├── repository/         - Element selectors
│   │   │   └── SelectorRepository - Element identifiers
│   │   └── utils/              - Utility classes
│   │       ├── ConfigManager   - Configuration file handling
│   │       └── Main            - Entry point utilities
│   └── test/
│       ├── java/com/test/
│       │   ├── hooks/          - Test hooks
│       │   │   └── Hooks       - Test setup and teardown
│       │   ├── runners/        - Test runners
│       │   │   ├── HotelSearchRunnerTest - Runner for hotel tests
│       │   │   └── LoginRunnerTest       - Runner for login tests
│       │   └── stepdefs/       - Step definitions
│       │       ├── HotelSearchSteps      - Hotel search steps
│       │       └── LoginSteps           - Login steps
│       └── resources/
│           ├── apps/           - Application APKs
│           │   └── TUIChallengeApp.apk  - Test application
│           ├── config/         - Configuration files
│           │   ├── config.properties         - Base configuration
│           │   ├── github-actions-config.properties - CI configuration
│           │   ├── sauce-config.properties   - SauceLabs configuration
│           │   └── cloud-config.properties   - Cloud testing configuration
│           ├── features/       - Cucumber feature files
│           │   ├── HotelSearch.feature   - Hotel search scenarios
│           │   └── Login.feature         - Login scenarios
│           ├── extent-config/  - Reporting configuration
│           │   └── spark-config.xml      - Extent reports configuration
│   
└── target/
    ├── cucumber-reports/       - Test reports
    │   ├── hotel-cucumber-pretty.html    - HTML report for hotel tests
    │   ├── hotel-cucumber-report.json    - JSON report for hotel tests
    │   ├── login-cucumber-pretty.html    - HTML report for login tests
    │   ├── login-cucumber-report.json    - JSON report for login tests
    │   └── extent-spark.html             - Consolidated Extent report
    ├── test-output/            - TestNG output
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

## Cloud Testing Integration

### GitHub Actions Integration

This project is configured to run tests automatically in the cloud using GitHub Actions. The workflow runs tests on Android emulators hosted on GitHub's macOS runners.

#### How the Integration Works

1. **Automated Runs**: Tests automatically run on:
   - Every push to master/main branch
   - Every pull request to master/main branch
   - Manual triggers from GitHub UI

2. **Test Environment**:
   - Tests run on a macOS GitHub-hosted runner
   - Uses Android emulator with API level 30 (Android 11)
   - Appium server is automatically installed and started
   - The app is installed on the emulator

3. **Configuration**:
   - Uses dedicated configuration properties for CI environment
   - No local Appium server needed for cloud runs

#### Manually Triggering Tests in GitHub

To manually run tests in GitHub Actions:

1. Go to your GitHub repository
2. Click on the "Actions" tab
3. Select "Appium Tests" from the workflows list
4. Click the "Run workflow" button
5. Select the branch to run tests on
6. Click "Run workflow"

#### Viewing Test Results in GitHub Actions

After tests complete in GitHub Actions:

1. Go to the Actions tab in your GitHub repository
2. Click on the completed workflow run
3. Scroll down to the "Artifacts" section
4. Download the "test-results" artifact to view reports

#### Configuration Files

- Workflow definition: `.github/workflows/appium-tests.yml`
- CI configuration: `src/test/resources/config/github-actions-config.properties`

### SauceLabs Integration

This framework also supports running tests on SauceLabs cloud testing platform, allowing for wider device coverage and parallel test execution.

#### SauceLabs Setup

1. Create a SauceLabs account
2. Set up your SauceLabs credentials in `sauce-config.properties`
3. Configure desired capabilities for SauceLabs in `DriverManager.java`

For detailed instructions, refer to the [SAUCE_LABS_SETUP.md](docs/SAUCE_LABS_SETUP.md) documentation.

## Viewing Reports

After test execution, reports are generated in:

- **Cucumber Reports**: `target/cucumber-reports/`
- **HTML Reports**:
   - `target/cucumber-reports/hotel-cucumber-pretty.html`
   - `target/cucumber-reports/login-cucumber-pretty.html`
- **JSON Reports**:
   - `target/cucumber-reports/hotel-cucumber-report.json`
   - `target/cucumber-reports/login-cucumber-report.json`
- **Extent Reports**: `target/cucumber-reports/extent-spark.html`
- **Screenshots**: `target/screenshots/[timestamp]/`

The report files can be opened in any web browser.

## Configuration

The framework can be configured using:

- `src/test/resources/config/config.properties`: Basic configuration for Appium and test execution
- `src/test/resources/config/github-actions-config.properties`: Configuration for GitHub Actions CI environment
- `src/test/resources/config/sauce-config.properties`: Configuration for SauceLabs cloud testing
- `src/test/resources/config/cloud-config.properties`: General cloud testing configuration
- `src/test/resources/extent-config/spark-config.xml`: Reporting configuration

### Key Configuration Parameters

#### Base Configuration (config.properties)
- `appium.server.url`: URL for Appium server connection
- `platform.name`: Target mobile platform (Android/iOS)
- `device.name`: Name of the target device or emulator
- `app.path`: Path to the application APK/IPA file
- `implicit.wait`: Default wait time in seconds

#### SauceLabs Configuration (sauce-config.properties)
- `saucelabs.username`: SauceLabs account username
- `saucelabs.accesskey`: SauceLabs access key
- `saucelabs.url`: SauceLabs hub URL
- `saucelabs.device`: Target device on SauceLabs

## Test Features

### Login Feature
Tests login functionality with various credentials and scenarios:
- Valid login
- Invalid login (wrong username/password)
- Empty credentials
- Password recovery flow

### Hotel Search Feature
Tests hotel search functionality and verifies:
- Search by location
- Search by date range
- Filter by price
- Filter by amenities
- Verify hotel listings and details

## Key Components

### Page Objects
- **BasePage**: Contains common methods used across all pages like waiting for elements, click actions, and input text
- **HomePage**: Contains methods for interacting with the home screen elements
- **LoginPage**: Contains methods for performing login operations

### Step Definitions
- **LoginSteps**: Implements the steps described in the Login.feature file
- **HotelSearchSteps**: Implements the steps described in the HotelSearch.feature file

### Hooks
The `Hooks` class manages test lifecycle events:
- `@Before`: Sets up the test environment
- `@After`: Performs cleanup and takes screenshots on test failure

## Documentation

For more detailed information, please refer to:

- [Command Reference Guide](docs/COMMAND_REFERENCE.md) - Quick reference for common commands
- [Feature Files Documentation](docs/FEATURE_FILES.md) - Details on test scenarios
- [Git Integration Guide](docs/GIT_CLOUD_INTEGRATION.md) - Setting up Git and CI/CD
- [SauceLabs Setup Guide](docs/SAUCE_LABS_SETUP.md) - Setting up SauceLabs integration
- [Known Issues](docs/KNOWN_ISSUES.md) - Documentation of known bugs and security issues