package com.example;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import javax.swing.text.html.parser.Element;
import java.time.Duration;
import java.util.List;

public class Helper {
    WebDriver driver;
    public Helper(WebDriver _driver) {
        this.driver= _driver;
    }

    public void clickElement(By by){
        //set timeout for WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        //Chờ đợi để click
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        //click
        driver.findElement(by).click();
    }
    public static void delay(int second){
        try{Thread.sleep(second *1000); //convert ms to s
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }



}
