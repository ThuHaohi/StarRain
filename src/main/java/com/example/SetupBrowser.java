package com.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SetupBrowser {
    public static WebDriver chromeDriver;
    public static JavascriptExecutor js;
    public static Helper helper;

    public void setupChrome(boolean enableDoH, String dohServerUrl) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (enableDoH) {
            System.out.println("🔧 Bật DNS over HTTPS (DoH) cho Chrome...");
            options.addArguments("--user-data-dir=/Users/vinbrain/Desktop/Selenium/ChromeProfile");
            options.addArguments("--dns-over-https-mode=secure");
            options.addArguments("--dns-over-https-templates=" + dohServerUrl);
            options.addArguments("--enable-features=UseDnsHttpsSvcbAlpn");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        } else {
            System.out.println("⚙️  Đang tắt DoH trên Chrome (sử dụng DNS hệ điều hành)");
            options.addArguments("--dns-over-https-mode=off");
        }

        chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().window().maximize();
        helper = new Helper(chromeDriver);
        js = (JavascriptExecutor) chromeDriver;
    }




    public WebDriver getDriver() {
        return chromeDriver;
    }




//
//    @After
//    public static void tearDown() {
//        if (firefoxDriver != null) {
//            firefoxDriver.quit();
//            System.out.println("Đã đóng Firefox");
//        }
//        if (chromeDriver != null) {
//            chromeDriver.quit();
//            System.out.println("Đã đóng Chrome");
//        }
//    }
}
