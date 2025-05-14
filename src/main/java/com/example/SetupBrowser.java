package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class SetupBrowser {
    public static WebDriver driver;
    public static JavascriptExecutor js;
    public static Helper helper;
    public static ObjectRepository_Chrome obj;

    @BeforeClass
    public static void createDriver() {
        // Chọn trình duyệt muốn sử dụng ở đây (Chrome hoặc Firefox)
        String browser = "chrome"; // hoặc "chrome"

        if (browser.equalsIgnoreCase("chrome")) {
            // Cấu hình cho Chrome
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("Started Chrome Driver");
        } else if (browser.equalsIgnoreCase("firefox")) {
            // Cấu hình cho Firefox
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            // Bật DoH (DNS over HTTPS) nếu muốn, chỉ cần bỏ chú thích dòng dưới
            options.addPreference("network.trr.mode", 2);
            options.addPreference("network.trr.uri", "https://dns.cloudflare.com/dns-query");
            driver = new FirefoxDriver(options);
            System.out.println("Started Firefox Driver");
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Cấu hình chung cho trình duyệt
        driver.manage().window().maximize(); // Mở cửa sổ trình duyệt ở chế độ toàn màn hình
        js = (JavascriptExecutor) driver;
        helper = new Helper(driver);
        obj = new ObjectRepository_Chrome();
    }

    //    @AfterClass
//    public void closeDriver(){
//        driver.quit();
//        System.out.println("Closed driver");
//    }
}
