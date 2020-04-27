package webdemo.seleniumDemo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class PsUgEduTest {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        Thread.sleep(5000);
        driver.get("https://ps.ug.edu.pl:8443/login.web");
    }
    
    @Test
    public void testLogInWithoutData() {
        driver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[2]/div/button/span"))
                .click();
        
        assertMessage();
    }
    
    @Test
    public void testLogInWithoutLogin() throws InterruptedException {
        driver.findElement(By.name("pass"))
                .sendKeys("111111");
        driver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[2]/div/button/span"))
                .click();
        
        assertMessage();
    }
    
    @Test
    public void testLogInWithoutPassword() {
        driver.findElement(By.name("login"))
                .sendKeys("111111");
        driver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[2]/div/button/span"))
                .click();
        
        assertMessage();
    }
    
    @Test
    public void testLogInWithInvalidData() {
        driver.findElement(By.name("login"))
                .sendKeys("111111");
        driver.findElement(By.name("pass"))
                .sendKeys("111111");
        driver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[2]/div/button/span"))
                .click();
        
        assertMessage();
    }
    
    @Test
    public void testLogInWithDifferentRole() {
        new Select(driver.findElement(By.name("licznik"))).selectByValue("d");
        driver.findElement(By.name("login"))
                .sendKeys("111111");
        driver.findElement(By.name("pass"))
                .sendKeys("111111");
        driver.findElement(By.xpath("//*[@id=\"formLogin\"]/div[2]/div/button/span"))
                .click();
        
        assertMessage();
    }
    
    @AfterEach
    public void refresh() {
        driver.get("https://ps.ug.edu.pl:8443/login.web");
    }
    
    @AfterAll
    public static void tearDown() {
        driver.close();
    }
    
    private void assertMessage() {
        final WebElement xpath =
                new WebDriverWait(driver, 10).until(
                        ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class=\"bluelighting_message\"]/div"))));
        assertEquals("Niepoprawne dane do logowania. Podano nieprawidłowy numer indeksu lub hasło.",
                xpath.getText());
    }
    
}
