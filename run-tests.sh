#!/bin/bash
# Run Appium Cucumber Tests

echo "Starting Appium tests..."

# Default values
tag="@smoke"

# Parse arguments (if provided)
if [ ! -z "$1" ]; then
  tag="$1"
fi

echo "Running tests with tag: $tag"

# Run tests
mvn clean test -Dandroid.platform.name=Android -Dandroid.device.name=Pixel_5_API_30 -Dandroid.automation.name=UiAutomator2 -Dcucumber.filter.tags="$tag"

echo "Tests completed."

# Open report if on Mac
if [[ "$OSTYPE" == "darwin"* ]]; then
  open target/reports/extent-report.html
fi