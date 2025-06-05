package com.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class SetupBrowser {
    public static WebDriver chromeDriver;
    public static WebDriver firefoxDriver;

    public static JavascriptExecutor js;
    public static Helper helper;

    // Setup Firefox cho DoH
//    public static void setupFirefox(boolean enableDoH) {
//        if (firefoxDriver == null) {
//            WebDriverManager.firefoxdriver().setup();
//            FirefoxOptions options = new FirefoxOptions();
//
//            if (enableDoH) {
//                // DoH Mode: 3 - Chỉ dùng DoH
//                options.addPreference("network.trr.mode",3);
//                options.addPreference("network.trr.uri", "https://mozilla.cloudflare-dns.com/dns-query");
//                 options.addPreference("network.trr.bootstrapAddress", "162.159.61.4");
//                System.out.println("Khởi động Firefox với DoH");
//            } else {
//                // Non-DoH: tắt hoàn toàn TRR (mode 5)
//                options.addPreference("network.trr.mode", 5);
//                System.out.println("Khởi động Firefox với Non-DoH");
//            }
//
//            firefoxDriver = new FirefoxDriver(options);
//            firefoxDriver.manage().window().maximize();
//            helper = new Helper(firefoxDriver);
//            js = (JavascriptExecutor) firefoxDriver;
//        }
//
//    }
    public void setupChrome(boolean enableDoH, String dohServerUrl) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/Program Files/Google/Chrome/Application/chrome.exe"); // Đảm bảo dùng đúng binary nếu bạn dùng nhiều bản Chrome

        if (enableDoH) {
            System.out.println("🔧 Bật DNS over HTTPS (DoH) cho Chrome...");
            options.addArguments("--user-data-dir=C:/Selenium/ChromeProfile");
            options.addArguments("--dns-over-https-mode=secure");
            options.addArguments("--dns-over-https-templates=" + dohServerUrl);
            options.addArguments("--enable-features=UseDnsHttpsSvcbAlpn");
//            options.addArguments("--force-fieldtrials=*");  // ⭐ Thêm dòng này
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");


//            if(dohServerUrl != null && !dohServerUrl.isEmpty()){
//                options.addArguments("--dns-over-https-templates=" + dohServerUrl);
//            }

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





//    @AfterClass
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
