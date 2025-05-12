package com.test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = {
        "src/test/resources/features/HotelSearch.feature"
    },
    glue = {"com.test.stepdefs", "com.test.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/hotel-cucumber-pretty.html",
        "json:target/cucumber-reports/hotel-cucumber-report.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    tags = "@smoke or @regression"
)
public class HotelSearchRunnerTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}