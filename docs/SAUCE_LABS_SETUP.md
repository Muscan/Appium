# Running Sauce Labs Tests

## Free Tier Limitations

With the free tier of Sauce Labs, you have:

1. **Limited concurrent sessions** (typically 1)
2. **Limited total minutes** (approximately 30 minutes/month)
3. **Limited device availability**

Since your total test suite runs for about 6 minutes, you'll only be able to run the complete suite about 16 times per month. This document provides guidance on how to efficiently use your free tier minutes.

## Setup Instructions for Team Members

1. **Create a Sauce Labs account**:
    - Sign up at https://saucelabs.com/sign-up

2. **Get your credentials**:
    - Find your username and access key in Account → User Settings

3. **Upload the app**:
    - Go to App Management → Mobile Apps → Upload
    - Upload your APK file
    - Copy the app ID (looks like `sauce-storage:yourapp.apk`)

4. **Set up GitHub Secrets**:
    - In your GitHub repository, go to Settings → Secrets
    - Add `SAUCE_USERNAME` and `SAUCE_ACCESS_KEY` secrets

5. **Update configuration files**:
    - Update `sauce-config.properties` with your app ID
    - Ensure proper device capabilities are set

## Running Tests Locally with Sauce Labs

```bash
# Set environment variables for local runs
export SAUCE_USERNAME=your_username
export SAUCE_ACCESS_KEY=your_access_key

# Copy the sauce config
cp src/test/resources/config/sauce-config.properties src/test/resources/config/config.properties

# Run a single test class to conserve minutes
mvn test -Dtest=LoginRunnerTest

# Run only smoke tests
mvn test -Dcucumber.filter.tags="@smoke"
```

## Testing Strategies for Free Tier

### 1. Run Subset of Tests

Instead of running all tests on every integration, consider these strategies:

- Run only smoke tests in CI/CD
- Rotate test coverage (run different tests each time)
- Use tags to organize tests by priority

Example tag structure:
```
@smoke      - Most critical tests (run in CI)
@regression - Full test suite (run locally before releases)
@priority1  - High priority tests
@priority2  - Medium priority tests 
@priority3  - Low priority tests
```

### 2. Optimize GitHub Actions Workflow

The included workflow file (`sauce-labs-demo.yml`) is configured to:

- Run only on manual trigger (not on every push)
- Run only a single test class with the `@smoke` tag
- Run on a weekly schedule for monitoring

This prevents accidental over-usage of your free minutes.

### 3. Device Selection

The free tier has limited device availability. The configuration uses common devices that are consistently available:

- Android GoogleAPI Emulator with Android 11.0
- This combination is usually available on Sauce Labs free tier

If you encounter device unavailability, try:
- Using an older Android version (9, 10, 11)
- Using generic emulator names instead of specific device models
- Running tests during non-peak hours

## Troubleshooting Common Issues

### 1. Concurrent Session Limit

Error: `Your organization has reached its concurrent session limit`

Solution:
- Make sure no other tests are running in your account
- Wait a few minutes and try again
- Check if teammates are using the same account

### 2. Device Not Available

Error: `We couldn't find a MATCHING device in our EU-Central data center`

Solution:
- Try a different device/OS combination
- Update `sauce-config.properties` with more commonly available devices

### 3. Connection Issues

Solution:
- Check your credentials are correct
- Verify network connectivity
- Ensure app ID is correct and app is properly uploaded

## Upgrading

If you need more capacity, consider:
- Upgrading to a paid Sauce Labs plan
- Using BrowserStack or another alternative
- Setting up a local emulator farm

For questions, contact the repository owner.