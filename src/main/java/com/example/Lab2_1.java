package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab2_1 {

    public static void main(String[] args) {
        
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Atidaryti https://demoqa.com/

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        // 2. Cookies neduoda

        // 3. Atsidarome widgets
        driver.findElement(By.xpath("//div[@class='card mt-4 top-card'][4]")).click();

        // 4. Pasirenkame Progress bar elementa
        WebElement progressBar = driver.findElement(By.xpath("//span[text()='Progress Bar']"));
        js.executeScript("arguments[0].scrollIntoView();", progressBar);
        progressBar.click();


        // 5. Spaudziam ant start

        driver.findElement(By.id("startStopButton")).click();

        // 6. Laukiam, kol uzsipildys progress bar ir spaudziam reset
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("resetButton")));
        driver.findElement(By.id("resetButton")).click();

        // 7. Patikrinam ar progress bar verte yra 0%
        if(driver.findElement(By.className("progress-bar")).getText().equals("0%")) {
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }

        driver.close();

    }

}
