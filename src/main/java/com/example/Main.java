package com.example;

import org.junit.Test;
import java.util.List;

public class Main extends SetupBrowser {

    @Test
    public void DoH() {
        setupFirefox(); // Chỉ khởi động Firefox khi cần
        List<String> links_firefox = Helper.readTop100DomainsFromCSV();
        for (String url : links_firefox) {
            try {
                System.out.println("Đang truy cập (Firefox - DoH): " + url);
                firefoxDriver.get(url);
                Helper.delay(5);
            } catch (Exception e) {
                System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
            }
        }
    }

    @Test
    public void Non_DoH() {
        setupChrome(); // Chỉ khởi động Chrome khi cần
        List<String> links_chrome = Helper.readTop100to200DomainsFromCSV();
        for (String url : links_chrome) {
            try {
                System.out.println("Đang truy cập (Chrome - Non-DoH): " + url);
                chromeDriver.get(url);
                Helper.delay(5);
            } catch (Exception e) {
                System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
            }
        }
    }
}
