package com.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
public class Helper {
    WebDriver driver;

    public Helper(WebDriver _driver) {
        this.driver = _driver;
    }

    public void clickElement(By by) {
        //set timeout for WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Chờ đợi để click
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        //click
        driver.findElement(by).click();
    }

    public static void delay(int second) {
        try {
            Thread.sleep(second * 1000); //convert ms to s
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static List<String> readTop100DomainsFromCSV(int startLine, int endLine) {
        List<String> links = new ArrayList<>();
        try {
            InputStream is = Helper.class.getClassLoader().getResourceAsStream("data.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (lineNumber < startLine) continue;
                if (lineNumber > endLine) break;

                String[] parts = line.split(",");
                if (parts.length > 1) {
                    links.add("https://" + parts[1].trim());
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return links;
    }
}

