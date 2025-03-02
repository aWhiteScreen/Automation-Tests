package com.example;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab2_2 {

    public static void main(String[] args) {
        
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Atidaryti https://demoqa.com/

        driver.get("https://demoqa.com/");
        driver.manage().window().maximize();

        // 2. Cookies neduoda

        // 3. Atsidarome elements
        driver.findElement(By.xpath("//div[@class='card mt-4 top-card'][1]")).click();

        // 4. Pasirenkame Web tables elementa
        WebElement webTables = driver.findElement(By.xpath("//span[text()='Web Tables']"));
        //js.executeScript("arguments[0].scrollIntoView();", webTables);
        webTables.click();

        // 5. Prideti pakankamai elementu, kad atsirastu antras puslapis

        for(int i = 0; i < 8; i ++) {
            driver.findElement(By.id("addNewRecordButton")).click();
            driver.findElement(By.id("firstName")).sendKeys("Guilherme");
            driver.findElement(By.id("lastName")).sendKeys("Cunha");
            driver.findElement(By.id("userEmail")).sendKeys("gcunha17@gmail.com");
            driver.findElement(By.id("age")).sendKeys("22");
            driver.findElement(By.id("salary")).sendKeys("0");
            driver.findElement(By.id("department")).sendKeys("Unemployed");
            driver.findElement(By.id("submit")).click();
        }

        // 6. Pasirinkti antra puslapi spaudziant next
        WebElement nextButton = driver.findElement(By.className("-next"));
        js.executeScript("arguments[0].scrollIntoView();", nextButton);
        nextButton.click();

        // 7. Istrinti elementa

        WebElement deleteButton = driver.findElement(By.id("delete-record-11"));
        js.executeScript("arguments[0].scrollIntoView();", deleteButton);
        deleteButton.click();

        if(driver.findElement(By.xpath("//input[@aria-label='jump to page']")).getDomAttribute("value").equals("1")) {
            System.out.println("Pagination has moved to 1");
        } else {
            System.out.println("Pagination has not moved to 1");
        }

        if(driver.findElement(By.className("-totalPages")).getText().equals("1")) {
            System.out.println("Number of pages has been reduced to 1");
        } else {
            System.out.println("Number of pages has not been reduced to 1");
        }





    }

}
