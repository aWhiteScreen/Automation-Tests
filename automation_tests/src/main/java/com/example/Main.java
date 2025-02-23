package com.example;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Main {
    public static void main(String[] args) {

        //System.setProperty("webdriver.firefox.driver", "/home/Augustas/Atsiuntimai/automation_tests/automation_tests/geckodriver.exe");

        FirefoxDriver driver = new FirefoxDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Atidaryti tinklapį

        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        //driver.close();


        // 2. Spausti ant giftcard
        driver.findElement(By.linkText("Gift Cards")).click();

        // 3. Pasirenkam prekę, kurios kaina didesne negu 99
        List<WebElement> giftCards = driver.findElements(By.className("item-box"));
        for(WebElement giftCard : giftCards) {
            WebElement price = giftCard.findElement(By.className("actual-price"));
            if(Float.parseFloat(price.getText()) > 99) {
                giftCard.findElement(By.className("product-box-add-to-cart-button")).click();
                break;
            }
        }

        // 4. Užpildom vardo laukus savo nuožiūra

        WebElement recipientName = driver.findElement(By.className("recipient-name"));
        recipientName.sendKeys("Felipe Motta");

        WebElement senderName = driver.findElement(By.className("sender-name"));
        senderName.sendKeys("Fernando Laprovitola");

        // 5. Qty ivedam reikšmę 5000

        WebElement giftCardQty = driver.findElement(By.className("qty-input"));
        giftCardQty.clear();
        giftCardQty.sendKeys("5000");

        // 6. Spausti "Add to cart"

        driver.findElement(By.className("add-to-cart-button")).click();

        // 7. Spausti "Add to wishlist"

        driver.findElement(By.className("add-to-wishlist-button")).click();

        // 8. Spausti ant jewelry

        driver.findElement(By.linkText("Jewelry")).click();

        // 9. Spausti "Create Your Own Jewelry"

        driver.findElement(By.linkText("Create Your Own Jewelry")).click();

        // 10. Pasirinkti reikšmes

        WebElement material = driver.findElement(By.id("product_attribute_71_9_15"));
        Select materialDropDown = new Select(material);
        //System.out.println(materialDropDown.getOptions());
        materialDropDown.selectByValue("47");

        WebElement length = driver.findElement(By.id("product_attribute_71_10_16"));
        length.sendKeys("80");

        driver.findElement(By.id("product_attribute_71_11_17_50")).click();

        // 11. Į qty įvesti 26

        WebElement jewelryQty = driver.findElement(By.className("qty-input"));
        jewelryQty.clear();
        jewelryQty.sendKeys("26");


        // 12. Spausti "Add to cart"

        driver.findElement(By.className("add-to-cart-button")).click();

        // 13. Spausti "Add to wishlist"

        driver.findElement(By.className("add-to-wishlist-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bar-notification")));

        // 14. Spausti ant wishlist

        driver.findElement(By.className("ico-wishlist")).click();

        // 15. Uždedam varneles ant add to card

        List<WebElement> wishlistItems = driver.findElements(By.name("addtocart"));
        for(WebElement wishlistItem : wishlistItems) {
            wishlistItem.click();
        }

        // 16. Spaudžiam add to card

        driver.findElement(By.name("addtocartbutton")).click();

        // 17. Patvirtinam subtotal vertę

        WebElement subtotal = driver.findElement(By.className("product-price"));
        if(Float.parseFloat(subtotal.getText()) == 1002600.00) {
            System.out.println("Price is as expected: " + subtotal.getText());
        } else {
            System.out.println("Price is not as expected");
        }

    }
}