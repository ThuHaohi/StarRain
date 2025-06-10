package com.example;
import java.util.List;
import org.junit.Test;

public class Main extends SetupBrowser  {

    @Test
    public void DoH_CloudFlare_50() {
        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "cloudflare.pcap";
        String interfaceName = "Wi-Fi";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
            setupChrome(true,"https://cloudflare-dns.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(1,50);

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
            tshark.filterCapture("cloudflare.pcap",
                    "cloudflare_doh.pcap",
                    "cloudflare_nondoh.pcap",
                    "cloudflare_otherFile.pcap",
                    List.of("1.1.1.1","1.0.0.1"));
        }


    }
    @Test
    public void DoH_Google_100() {
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
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(51,100);

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
            tshark.filterCapture("google.pcap",
                    "google_doh.pcap",
                    "google_nondoh.pcap",
                    "google_otherFile.pcap",
                    List.of("8.8.8.8","8.8.4.4"));
        }


    }
    @Test
    public void DoH_Quad9_150() {
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
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(101,150);

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
            tshark.filterCapture("quad9.pcap",
                    "quad9_doh.pcap",
                    "quad9_nondoh.pcap",
                    "quad9_otherFile.pcap",
                    List.of("9.9.9.9","9.9.9.9"));
        }


    }
    @Test
    public void DoH_AdGuard_200() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "adguard.pcap";
        String interfaceName = "Wi-Fi";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.adguard.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(151,200);

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
            tshark.filterCapture("adguard.pcap",
                    "adguard_doh.pcap",
                    "adguard_nondoh.pcap",
                    "adguard_otherFile.pcap",
                    List.of("94.140.14.14","94.140.15.15"));
        }


    }
    @Test
    public void DoH_CleanBrowsing_Family_250() {
        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "cleanbrowsing_family.pcap";
        String interfaceName = "Wi-Fi";

        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://doh.cleanbrowsing.org/doh/family-filter/");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(200,250);

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
            tshark.filterCapture("cleanBrowsing_Family.pcap",
                    "cleanBrowsing_Family_doh.pcap",
                    "cleanBrowsing_Family_nondoh.pcap",
                    "cleanBrowsing_Family_otherFile.pcap",
                    List.of("185.228.168.168","185.228.169.168"));
        }


    }
    @Test
    public void DoH_YanDex_300() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "yandex.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://safe.dot.dns.yandex.net/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(251,300);

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
            tshark.filterCapture("yandex.pcap",
                    "yandex_doh.pcap",
                    "yandex_nondoh.pcap",
                    "yandex_otherFile.pcap",
                    List.of("77.88.8.88","77.88.8.2"));
        }


    }
    @Test
    public void DoH_OpenDNS_350() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "opendns.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://doh.opendns.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(301,350);

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
            tshark.filterCapture("openDNS.pcap",
                    "openDNS_doh.pcap",
                    "openDNS_nondoh.pcap",
                    "openDNS_otherFile.pcap",
                    List.of("146.112.41.2","146.112.41.2"));
        }


    }
    @Test
    public void DoH_LibreDNS_400() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "lirbredns.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://doh.libredns.gr/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(351,400);

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
            tshark.filterCapture("lirbredns.pcap",
                    "lirbredns_doh.pcap",
                    "lirbredns_nondoh.pcap",
                    "lirbredns_otherFile.pcap",
                    List.of("116.202.176.26","116.202.176.26"));
        }


    }
    @Test
    public void DoH_Mullvad_450() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "mullvad.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.mullvad.net/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(401,450);

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
            tshark.filterCapture("mullvad.pcap",
                    "mullvad_doh.pcap",
                    "mullvad_nondoh.pcap",
                    "mullvad_otherFile.pcap",
                    List.of("194.242.2.2","194.242.2.2"));
        }


    }
    @Test
    public void DoH_DNShome_500() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "dnshome.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.dnshome.de/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(451,500);

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
            tshark.filterCapture("dnshome.pcap",
                    "dnshome_doh.pcap",
                    "dnshome_nondoh.pcap",
                    "dnshome_otherFile.pcap",
                    List.of("45.86.125.58","45.86.125.59"));
        }


    }
    @Test
    public void DoH_DNS_sb_550() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "dnssb.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://doh.sb/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(501,550);

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
            tshark.filterCapture("dnssb.pcap",
                    "dnssb_doh.pcap",
                    "dnssb_nondoh.pcap",
                    "dnssb_otherFile.pcap",
                    List.of("185.222.222.222","185.222.222.222"));
        }


    }
    @Test
    public void DoH_AliDNS_600() {

        // 1. Cấu hình Tshark
        TsharkCapture tshark = new TsharkCapture();
        String rawPcap = "alidns.pcap";
        String interfaceName = "Wi-Fi";
//        String filename = "doh_all_16.pcap";
        try {
            // 2. Bắt đầu bắt gói
            tshark.startCapture(rawPcap, interfaceName);
//            tshark.startCapture(filename, interfaceName);
            // 3. Khởi động Firefox (bật DoH)
            setupChrome(true,"https://dns.alidns.com/dns-query");

            // 4. Đọc danh sách website từ CSV
            List<String> links_chrome = Helper.readTop100DomainsFromCSV(551,600);

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
            tshark.filterCapture("alidns.pcap",
                    "alidns_doh.pcap",
                    "alidns_nondoh.pcap",
                    "alidns_otherFile.pcap",
                    List.of("223.5.5.5","223.6.6.6"));
        }


    }


}

