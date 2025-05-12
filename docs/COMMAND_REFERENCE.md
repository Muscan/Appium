# Command Reference Guide

## Basic Commands

### Appium

```bash
# Start Appium server
appium

# Check Appium version
appium --version

# Install UIAutomator2 driver
appium driver install uiautomator2
```

### Maven

```bash
# Clean and run all tests
mvn clean test

# Run tests with specific tags
mvn test -Dcucumber.filter.tags="@smoke"
mvn test -Dcucumber.filter.tags="@regression"

# Run a specific test class
mvn test -Dtest=LoginRunnerTest
mvn test -Dtest=HotelSearchRunnerTest

# Run tests with specific properties
mvn test -Dandroid.platform.name=Android -Dandroid.device.name=Pixel_5_API_30
```

### Scripts

```bash
# Windows
run-tests.bat @smoke
run-tests.bat @regression

# Linux/Mac
./run-tests.sh @smoke
./run-tests.sh @regression
```

### Git

```bash
# Initialize repository
git init

# Add files
git add .

# Commit changes
git commit -m "Your commit message"

# Create and switch to a new branch
git checkout -b feature/new-feature

# Push to GitHub
git push origin master
```

## Test Execution Flow

1. Start Appium server
2. Execute tests using Maven or scripts
3. View reports in target directory

## Report Locations

- Cucumber HTML: `target/cucumber-reports/hotel-cucumber-pretty.html` and `target/cucumber-reports/login-cucumber-pretty.html`
- Cucumber JSON: `target/cucumber-reports/hotel-cucumber-report.json` and `target/cucumber-reports/login-cucumber-report.json`
- Extent Reports: `target/cucumber-reports/extent-spark.html`
- Screenshots: `target/screenshots/[timestamp]/`

## Environment Variables

```bash
# Set Java home (Windows)
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_XXX

# Set Java home (Linux/Mac)
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk

# Set Android home (Windows)
set ANDROID_HOME=C:\Users\username\AppData\Local\Android\Sdk

# Set Android home (Linux/Mac)
export ANDROID_HOME=$HOME/Android/Sdk
```

## Configuration Settings

Key properties in `config.properties`:

```properties
appium.server.url=http://127.0.0.1:4723
android.platform.name=Android
android.device.name=Pixel_5_API_30
android.automation.name=UiAutomator2
android.app.package=com.tui.qa.challenge
android.app.activity=com.tui.qa.challenge.MainActivity
```
