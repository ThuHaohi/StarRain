package com.example;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
public class Main extends SetupBrowser  {

//    @Test
//    public void DoH() {
//        // 1. Cấu hình Tshark
//        TsharkCapture tshark = new TsharkCapture();
//        String rawPcap = "firefox_doh.pcapng";
//        String interfaceName = "\\Device\\NPF_{E5E2592A-0AA3-4543-A643-0D8B21276DF5}"; // hoặc "Ethernet"
//        try {
//            // 2. Bắt đầu bắt gói
//            tshark.startCapture(rawPcap, interfaceName);
//
//            // 3. Khởi động Firefox (bật DoH)
//            setupFirefox(true);
//
//            // 4. Đọc danh sách website từ CSV
//            List<String> links_firefox = Helper.readTop100DomainsFromCSV(1, 10);
//
//            // 5. Truy cập từng trang
//            for (String url : links_firefox) {
//                try {
//                    System.out.println("Đang truy cập (Firefox - DoH): " + url);
//                    firefoxDriver.get(url);
//                    Helper.delay(5); // đợi 5s
//                } catch (Exception e) {
//                    System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
//                }
//            }
//
//            Helper.delay(2); // đợi thêm 2s
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 6. Dừng bắt gói
//            tshark.stopCapture();
//
//            // 7. Tắt trình duyệt
////            tearDown();
//
//            // 8. Phân loại thành 2 file mới
//            tshark.filterCapture("firefox_doh.pcapng", "doh_only.pcapng", "nondoh_only.pcapng");
//        }
//    }

    @Test
    public void DoH_CloudFlare() {
//        setupChrome(true);
//        chromeDriver.get("https://www.google.com");


        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "cloudflare.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://cloudflare-dns.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(10,25);

            // 5. Truy cập từng trang
            for (String url : links_chrome) {
                try {
                    System.out.println("Đang truy cập (Chrome - DoH): " + url);
                    chromeDriver.get(url);
                    Helper.delay(5);
                } catch (Exception e) {
                    System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
                }
            }

//            Helper.delay(2); // đợi thêm 2s

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Dừng bắt gói
            tshark.stopCapture();

            // 7. Tắt trình duyệt
//            tearDown();

            // 8. Phân loại thành 2 file mới
            tshark.filterCapture("cloudflare.pcap", "cloudflare_doh.pcap", "cloudflare_nondoh.pcap","cloudflare_otherFile.pcap",List.of("1.1.1.1","1.0.0.1"));
        }


    }
    @Test
    public void DoH_16Server() {
        TsharkCapture tshark = new TsharkCapture();
        String interfaceName = "Wi-Fi";
        String filename = "all_doh_traffic.pcap";

        // Danh sách 4 DoH server tiêu biểu
        List<String> dohServers = List.of(
                "https://cloudflare-dns.com/dns-query",
                "https://dns.google/dns-query",
                "https://dns.quad9.net/dns-query",
                "https://dns.adguard.com/dns-query"
        );

        try {
            // Bắt đầu bắt gói tshark (1 lần duy nhất)
            tshark.startCapture(filename, interfaceName);

            // Đọc danh sách website
            List<String> websites = Helper.readTop100DomainsFromCSV(20, 25);  // chỉnh số lượng nếu cần

            // Duyệt từng DoH server
            for (String dohUrl : dohServers) {
                System.out.println("\n🔧 Đang cấu hình Chrome với DoH server: " + dohUrl);

                // Khởi động Chrome với DoH
                setupChrome(true,dohUrl);

                // Truy cập danh sách website
                for (String site : websites) {
                    try {
                        System.out.println("🌐 Truy cập: " + site);
                        chromeDriver.get(site);
                        Helper.delay(4);
                    } catch (Exception e) {
                        System.out.println("⚠️ Lỗi truy cập: " + site + " → " + e.getMessage());
                    }
                }

//                helper.tearDown();  // đóng Chrome để chuẩn bị cho DoH khác
                Helper.delay(2);  // nghỉ chút giữa 2 lần
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tshark.stopCapture();
            System.out.println("✅ Đã lưu toàn bộ traffic vào: " + filename);

            // 8. Phân loại thành 2 file mới
//            tshark.filterCapture("all_doh_traffic.pcap", "doh_only.pcap", "nondoh_only.pcap","otherFile.pcap");
        }


    }
    @Test
    public void DoH_Google() {
//        setupChrome(true);
//        chromeDriver.get("https://www.google.com");


        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "google.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.google/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(1,10);

            // 5. Truy cập từng trang
            for (String url : links_chrome) {
                try {
                    System.out.println("Đang truy cập (Chrome - DoH): " + url);
                    chromeDriver.get(url);
                    Helper.delay(5);
                } catch (Exception e) {
                    System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
                }
            }

//            Helper.delay(2); // đợi thêm 2s

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Dừng bắt gói
            tshark.stopCapture();

            // 7. Tắt trình duyệt
//            tearDown();

            // 8. Phân loại thành 2 file mới
            tshark.filterCapture("google.pcap", "google_doh.pcap", "google_nondoh.pcap","google_otherFile.pcap",List.of("8.8.8.8","8.8.4.4"));
        }


    }
    @Test
    public void DoH_Quad9() {
//        setupChrome(true);
//        chromeDriver.get("https://www.google.com");


        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "quad9.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.quad9.net/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(1,10);

            // 5. Truy cập từng trang
            for (String url : links_chrome) {
                try {
                    System.out.println("Đang truy cập (Chrome - DoH): " + url);
                    chromeDriver.get(url);
                    Helper.delay(5);
                } catch (Exception e) {
                    System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
                }
            }

//            Helper.delay(2); // đợi thêm 2s

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Dừng bắt gói
            tshark.stopCapture();

            // 7. Tắt trình duyệt
//            tearDown();

            // 8. Phân loại thành 2 file mới
            tshark.filterCapture("quad9.pcap", "quad9_doh.pcap", "quad9_nondoh.pcap","quad9_otherFile.pcap",List.of("9.9.9.9","9.9.9.9"));
        }


    }
    @Test
    public void DoH_AdGuard() {
//        setupChrome(true);
//        chromeDriver.get("https://www.google.com");


        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "adguard.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.adguard.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(1,10);

            // 5. Truy cập từng trang
            for (String url : links_chrome) {
                try {
                    System.out.println("Đang truy cập (Chrome - DoH): " + url);
                    chromeDriver.get(url);
                    Helper.delay(5);
                } catch (Exception e) {
                    System.out.println("Lỗi khi truy cập: " + url + " → " + e.getMessage());
                }
            }

//            Helper.delay(2); // đợi thêm 2s

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 6. Dừng bắt gói
            tshark.stopCapture();

            // 7. Tắt trình duyệt
//            tearDown();

            // 8. Phân loại thành 2 file mới
            tshark.filterCapture("adguard.pcap", "adguard_doh.pcap", "adguard_nondoh.pcap","adguard_otherFile.pcap",List.of("185.228.168.168","185.228.168.168"));
        }


    }

}
