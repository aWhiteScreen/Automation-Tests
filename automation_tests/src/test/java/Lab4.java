import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Lab4 {

    private static FirefoxDriver driver;
    //private static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static String email = "bolsonarista3061@gmail.com";
    private static String password = "jair4eva";

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

    public void testScenario(String dataFile) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Login
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();
        //wait.until(ExpectedConditions.elementToBeClickable(By.linkText("DigitalDo")))
        driver.findElement(By.linkText("Digital downloads")).click();

        Path filePath = Paths.get(dataFile);
        List<String> items = Files.readAllLines(filePath);
        for (String item : items) {
            WebElement digitalDownload = driver.findElement(By.linkText(item));
            WebElement itemBox = digitalDownload.findElement(By.xpath("./ancestor::div[contains(@class, 'item-box')]"));
            itemBox.findElement(By.className("product-box-add-to-cart-button")).click();
        }

        driver.findElement(By.className("cart-label")).click();
        WebElement acceptTerms = driver.findElement(By.id("termsofservice"));
        wait.until(ExpectedConditions.elementToBeClickable(acceptTerms));
        acceptTerms.click();
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();

        List<WebElement> buttons = driver.findElements(By.id("billing-address-select"));

        if (!buttons.isEmpty()) {
            driver.findElement(By.className("new-address-next-step-button")).click();
        } else {
            WebElement country = driver.findElement(By.id("BillingNewAddress_CountryId"));
            Select countryDropDown = new Select(country);
            //System.out.println(materialDropDown.getOptions());
            countryDropDown.selectByValue("156");
            driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Vilnius");
            driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Balandžių Kapinės");
            driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("LT-“0666");
            driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("+37043212123");
    
            driver.findElement(By.className("new-address-next-step-button")).click();
        }

        WebElement paymentMethod = driver.findElement(By.className("payment-method-next-step-button"));
        wait.until(ExpectedConditions.elementToBeClickable(paymentMethod));
        paymentMethod.click();

        WebElement paymentInfo = driver.findElement(By.className("payment-info-next-step-button"));
        wait.until(ExpectedConditions.elementToBeClickable(paymentInfo));
        paymentInfo.click();

        WebElement confirmOrder = driver.findElement(By.className("confirm-order-next-step-button"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", confirmOrder);
        confirmOrder.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.className("order-completed-continue-button")));
        String result = driver.findElement(By.className("title")).getText();
        assertEquals(result, "Your order has been successfully processed!");

    }

    @Test
    public void testCaseOne() throws IOException {
        testScenario("test1.txt");
    }
    @Test
    public void testCaseTwo() throws IOException {
        testScenario("test2.txt");
    }


}
