package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/HOME/AppData/Local/Google/Chrome/User Data");
        options.addArguments("profile-directory=Profile 8");
        options.addArguments("--remote-debugging-port=9222");
        // Initialize the WebDriver
        driver = new ChromeDriver(options);
    }

    @Test
    public void testGoogleNavigation() {
        // Navigate to Google
        driver.get("https://www.google.com/");
        // Additional test actions can be added here
    }

    @AfterEach
    public void tearDown() {
        // Close the driver after the test is finished
        if (driver != null) {
            driver.quit();
        }
    }
} 