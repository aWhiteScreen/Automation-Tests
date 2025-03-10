import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Random;
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

public class Lab3Test {

    private static FirefoxDriver driver;
    //private static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private static Random rand = new Random();
    private static String email = String.format("bolsonarista%d@smartmail.com", rand.nextInt(1000));
    private static String password = "jair4eva";

    @BeforeClass // Creates a user before any test
    public static void UserCreation() {
        // 1. Open the website: https://demowebshop.tricentis.com/
        driver = new FirefoxDriver();
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        // 2. Click "Log in"
        driver.findElement(By.linkText("Log in")).click();
        // 3. Click "Register" in the "New Customer" section
        driver.findElement(By.className("register-button")).click();
        // 4. Fill in the registration form fields
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Fernando");
        driver.findElement(By.id("LastName")).sendKeys("Laprovitola");
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        // 5. Click "Register"
        driver.findElement(By.id("register-button")).click();
        // 6. Click "Continue"
        driver.findElement(By.className("register-continue-button")).click();
        driver.quit();
    }

    @Before
    public void setUp() {
      driver = new FirefoxDriver();
      driver.manage().window().maximize();
      // 1. Open the website: https://demowebshop.tricentis.com/
      driver.get("https://demowebshop.tricentis.com/");

    }

    @After
    public void tearDown() {
        driver.quit();
    } 

    public void testScenario(String dataFile) throws IOException, InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // 2. Click "Log in"
        driver.findElement(By.linkText("Log in")).click();
        // 3. Fill in the "Email" and "Password" fields and click "Log in"
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.className("login-button")).click();
        // 4. In the sidebar menu, select "Digital downloads"
        driver.findElement(By.linkText("Digital downloads")).click();

        // 5. Add products to the cart by reading from a text file:
        Path filePath = Paths.get(dataFile);
        List<String> items = Files.readAllLines(filePath); // Reads Items from list
        for (String item : items) {
            WebElement digitalDownload = driver.findElement(By.linkText(item));
            wait.until(ExpectedConditions.elementToBeClickable(digitalDownload));
            WebElement itemBox = digitalDownload.findElement(By.xpath("./ancestor::div[contains(@class, 'item-box')]"));
            wait.until(ExpectedConditions.elementToBeClickable(itemBox));
            itemBox.findElement(By.className("product-box-add-to-cart-button")).click();
        }

        // 6. Open "Shopping cart"
        driver.findElement(By.className("cart-label")).click();
        // 7. Check "I agree" and click "Checkout"
        WebElement acceptTerms = driver.findElement(By.id("termsofservice"));
        wait.until(ExpectedConditions.elementToBeClickable(acceptTerms));
        acceptTerms.click();
        Thread.sleep(1000);
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        checkoutButton.click();

        // 8. In "Billing Address", select an existing address or fill in a new one, then click "Continue"
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

        // 9. In "Payment Method", click "Continue"
        WebElement paymentMethod = driver.findElement(By.className("payment-method-next-step-button"));
        wait.until(ExpectedConditions.elementToBeClickable(paymentMethod));
        paymentMethod.click();

        // 10. In "Payment Information", click "Continue"
        WebElement paymentInfo = driver.findElement(By.className("payment-info-next-step-button"));
        wait.until(ExpectedConditions.elementToBeClickable(paymentInfo));
        paymentInfo.click();

        // 11. In "Confirm Order", click "Confirm"
        WebElement confirmOrder = driver.findElement(By.className("confirm-order-next-step-button"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrder));
        js.executeScript("arguments[0].scrollIntoView(true);", confirmOrder);
        confirmOrder.click();

        // 12. Ensure the order is successfully placed.
        wait.until(ExpectedConditions.elementToBeClickable(By.className("order-completed-continue-button")));
        String result = driver.findElement(By.className("title")).getText();
        assertEquals(result, "Your order has been successfully processed!");

    }

    @Test // o Test 1: Read from data1.txt
    public void testCaseOne() throws IOException, InterruptedException {
        testScenario("test1.txt");
    }
    @Test // o Test 2: Read from data2.txt
    public void testCaseTwo() throws IOException, InterruptedException {
        testScenario("test2.txt");
    }


}
