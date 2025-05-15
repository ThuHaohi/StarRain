package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class SetupBrowser {
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;

    public static JavascriptExecutor js;
    public static Helper helper;

    // Setup Firefox cho DoH
    public static void setupFirefox() {
        if (firefoxDriver == null) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("network.trr.mode", 2);
            options.addPreference("network.trr.uri", "https://dns.cloudflare.com/dns-query");
            firefoxDriver = new FirefoxDriver(options);
            firefoxDriver.manage().window().maximize();
            helper = new Helper(firefoxDriver);
            js = (JavascriptExecutor) firefoxDriver;
            System.out.println("Đã khởi động Firefox với DoH");
        }
    }

    // Setup Chrome cho non-DoH
    public static void setupChrome() {
        if (chromeDriver == null) {
            WebDriverManager.chromedriver().setup();
            chromeDriver = new ChromeDriver();
            chromeDriver.manage().window().maximize();
            helper = new Helper(chromeDriver);
            js = (JavascriptExecutor) chromeDriver;
            System.out.println("Đã khởi động Chrome (non-DoH)");
        }
    }

    @AfterClass
    public static void tearDown() {
        if (firefoxDriver != null) {
            firefoxDriver.quit();
            System.out.println("Đã đóng Firefox");
        }
        if (chromeDriver != null) {
            chromeDriver.quit();
            System.out.println("Đã đóng Chrome");
        }
    }
}
