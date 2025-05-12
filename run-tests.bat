@echo off
REM Run Appium Cucumber Tests

echo Starting Appium tests...

REM Default values
set tag=@smoke

REM Parse arguments (if provided)
if not "%~1"=="" set tag=%~1

echo Running tests with tag: %tag%

REM Set the full path to Maven
set MAVEN_CMD=C:\Users\mimus\Downloads\maven-mvnd-1.0.2-windows-amd64\maven-mvnd-1.0.2-windows-amd64\mvn\bin\mvn.cmd

echo Using Maven command: %MAVEN_CMD%

REM Run tests
echo Running: %MAVEN_CMD% clean test -Dandroid.platform.name=Android -Dandroid.device.name=Pixel_5_API_30 -Dandroid.automation.name=UiAutomator2 -Dcucumber.filter.tags=%tag%
call %MAVEN_CMD% clean test -Dandroid.platform.name=Android -Dandroid.device.name=Pixel_5_API_30 -Dandroid.automation.name=UiAutomator2 -Dcucumber.filter.tags=%tag%

echo Tests completed.

REM Try to open the report
if exist "target\reports\extent-report.html" (
    start "" "target\reports\extent-report.html"
) else if exist "target\cucumber-reports\extent-spark.html" (
    start "" "target\cucumber-reports\extent-spark.html"
) else if exist "target\extent-report.html" (
    start "" "target\extent-report.html"
) else (
    echo Report not found in common locations.
    echo Check manually in the target directory.
)

echo Done.