import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab4Test { // 4) change password, confirm new password works and old one does not.

    private static FirefoxDriver driver;
    //private static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static Random rand = new Random();
    private static String email = String.format("bolsonarista%d@latviamail.com", rand.nextInt(1000));
    private static String password = "jair4eva";
    private static String newPassword = "lula4eva";

    @BeforeClass // Creates a user before any test
    public static void UserCreation() {
        driver = new FirefoxDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.className("register-button")).click();
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Fernando");
        driver.findElement(By.id("LastName")).sendKeys("Laprovitola");
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();
        driver.findElement(By.className("register-continue-button")).click();
        driver.findElement(By.className("account")).click();
        driver.findElement(By.linkText("Change password")).click();
        driver.findElement(By.id("OldPassword")).sendKeys(password);
        driver.findElement(By.id("NewPassword")).sendKeys(newPassword);
        driver.findElement(By.id("ConfirmNewPassword")).sendKeys(newPassword);
        driver.findElement(By.className("change-password-button")).click();


        driver.quit();
    }

    @Before
    public void setUp() {
      driver = new FirefoxDriver();
      driver.manage().window().maximize();
      driver.get("https://demowebshop.tricentis.com/");

    }

    @After
    public void tearDown() {
        driver.quit();
    } 

    @Test // Test with the new password
    public void newPasswordTest() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(newPassword);
        driver.findElement(By.className("login-button")).click();

        WebElement accountButton = driver.findElement(By.linkText(email));
        wait.until(ExpectedConditions.elementToBeClickable(accountButton));
        assertTrue(accountButton.isDisplayed());
    }

    @Test // Test with the old password
    public void oldPasswordTest() {
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();

        String result = driver.findElement(By.xpath("//div[@class='validation-summary-errors']//li")).getText();
        assertEquals("The credentials provided are incorrect", result);
        
    }



}
