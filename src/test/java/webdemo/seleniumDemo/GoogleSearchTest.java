package webdemo.seleniumDemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class GoogleSearchTest {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeEach
    public void setUp() {
        driver.get("https://www.google.pl");
    }
    
    @Test
    public void testGetLogo() {
        WebElement element = driver.findElement(By.id("hplogo"));
        
        assertTrue(element.isDisplayed());
    }
    
    @Test
    public void testGetFirstResult() {
        driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys("xd");
        driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]"))
                .click();
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[1]/div/div[1]/a/h3"));
        
        assertNotNull(element.getText());
    }
    
    @Test
    public void testGetThirdResult() {
        driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys("xd");
        driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[3]/center/input[1]"))
                .click();
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[3]/div/div[1]/a/h3"));
        
        assertNotNull(element.getText());
    }
    
    @Test
    public void testClickWithoutClick() {
        driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input"))
                .sendKeys("xd");
        driver.findElement(By.cssSelector("#tsf > div:nth-child(2) > div.A8SBwf > div.FPdoLc.tfB0Bf > center > input.gNO89b"))
                .sendKeys(Keys.RETURN);
        
        WebElement element = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[3]/div/div[1]/a/h3"));
        
        assertNotNull(element.getText());
    }
    
    @Test
    public void testUnableToLocateElement() {
        try {
            driver.findElement(By.xpath("dgfhfgdhfgh"))
                    .sendKeys("xd");
            fail();
        } catch ( NoSuchElementException e ) {
            assertTrue(true);
        }
        
    }
    
}
