package com.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TsharkCapture {

    private Process process;

    // ======== Bắt đầu bắt gói ========
    public void startCapture(String filename, String interfaceName) throws IOException {
        Path outputPath = getOutputPath(filename);
        System.out.println("➡️ Bắt đầu ghi file tại: " + outputPath);

        List<String> command = new ArrayList<>(Arrays.asList(
                "tshark",
                "-i", interfaceName,
                "-w", outputPath.toString(),
                "-l" // ghi theo thời gian thực
        ));

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        process = builder.start();

        // Ghi log tshark ở luồng riêng (không block chương trình)
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Tshark: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    // ======== Dừng bắt gói ========
    public void stopCapture() {
        if (process != null && process.isAlive()) {
            process.destroy();
            try {
                // ⏳ Chờ 2 giây cho tshark flush dữ liệu
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("🛑 Tshark đã bị dừng.");
        }
    }



    // ======== Phân loại DoH và Non-DoH ========
    public void filterCapture(String inputFileName, String dohOutputFileName, String nonDohOutputFileName, String otherFileName, List<String> dohIps) {
        try {
            Path inputPath = getOutputPath(inputFileName);
            Path dohPath = getOutputPath(dohOutputFileName);
            Path nonDohPath = getOutputPath(nonDohOutputFileName);
            Path otherPath = getOutputPath(otherFileName);

            // 🎯 Ghép các IP lại thành biểu thức filter tshark
            String joinedIpFilter = dohIps.stream()
                    .map(ip -> "ip.dst == " + ip)
                    .collect(Collectors.joining(" || "));

            // 🎯 Lọc DoH: TCP 443 + IP thuộc danh sách
            String dohFilter = "tcp.port == 443 and (" + joinedIpFilter + ")";

            Process dohProcess = new ProcessBuilder(
                    "tshark",
                    "-r", inputPath.toString(),
                    "-Y", dohFilter,
                    "-w", dohPath.toString()
            ).inheritIO().start();
            dohProcess.waitFor();

            // 🎯 Lọc Non-DoH: TCP 443 nhưng không phải IP trong danh sách
            String nonDohFilter = "tcp.port == 443 and !(" + joinedIpFilter + ")";

            Process nonDohProcess = new ProcessBuilder(
                    "tshark",
                    "-r", inputPath.toString(),
                    "-Y", nonDohFilter,
                    "-w", nonDohPath.toString()
            ).inheritIO().start();
            nonDohProcess.waitFor();

            // 🎯 Gói khác
            Process otherProcess = new ProcessBuilder(
                    "tshark",
                    "-r", inputPath.toString(),
                    "-Y", "!(tcp.port == 443)",
                    "-w", otherPath.toString()
            ).inheritIO().start();
            otherProcess.waitFor();

            System.out.println("✅ Lọc xong cho server DoH với các IP: " + dohIps);
            System.out.println("   • DoH     → " + dohPath);
            System.out.println("   • Non-DoH → " + nonDohPath);
            System.out.println("   • Khác    → " + otherPath);

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Có lỗi xảy ra:");
            e.printStackTrace();
        }
    }


    // ======== Hàm phụ: Tạo đường dẫn đầu ra ========
//    private Path getOutputPath(String filename) {
//        String baseDir = System.getProperty("user.dir");
//        Path fullPath = Paths.get(baseDir, "src", "main", "resources", "tshark", "output",filename);
//
//        // Tạo thư mục nếu chưa tồn tại
//        File dir = fullPath.getParent().toFile();
//        if (!dir.exists()) {
//            boolean created = dir.mkdirs();
//            if (created) {
//                System.out.println("📁 Đã tạo thư mục: " + dir.getAbsolutePath());
//            } else {
//                System.err.println("❌ Không thể tạo thư mục: " + dir.getAbsolutePath());
//            }
//        }
//
//        return fullPath;
//    }
    private Path getOutputPath(String filename) {
        String baseDir = System.getProperty("user.dir");

        // Phân loại thư mục con dựa vào tên file
        String subFolder;
        if (filename.toLowerCase().contains("cloudflare")) {
            subFolder = "cloudflare";
        } else if (filename.toLowerCase().contains("google")) {
            subFolder = "google";
        } else if (filename.toLowerCase().contains("quad9")) {
            subFolder = "quad9";
        } else if (filename.toLowerCase().contains("adguard")) {
            subFolder = "adguard";
        } else if (filename.toLowerCase().contains("cleanbrowsing_family")) {
            subFolder = "cleanbrowsing_family";
        } else if (filename.toLowerCase().contains("opendns")) {
            subFolder = "opendns";
        } else if (filename.toLowerCase().contains("lirbredns")) {
            subFolder = "lirbredns";
        } else if (filename.toLowerCase().contains("yandex")) {
            subFolder = "yandex";
        } else if (filename.toLowerCase().contains("uncensoreddns")) {
            subFolder = "uncensoreddns";
        } else if (filename.toLowerCase().contains("mullvad")) {
            subFolder = "mullvad";
        } else if (filename.toLowerCase().contains("dnssb")) {
            subFolder = "dnssb";
        } else if (filename.toLowerCase().contains("alidns")) {
            subFolder = "alidns";
        } else if (filename.toLowerCase().contains("dnswatch")) {
            subFolder = "dnswatch";
        } else if (filename.toLowerCase().contains("dnshome")) {
            subFolder = "dnshome";
        } else {
            subFolder = "other";
        }

        // Tạo đường dẫn đầy đủ
        Path fullPath = Paths.get(baseDir, "src", "main", "resources", "tshark", "output", subFolder, filename);

        // Tạo thư mục nếu chưa tồn tại
        File dir = fullPath.getParent().toFile();
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("📁 Đã tạo thư mục: " + dir.getAbsolutePath());
            } else {
                System.err.println("❌ Không thể tạo thư mục: " + dir.getAbsolutePath());
            }
        }

        return fullPath;
    }

}