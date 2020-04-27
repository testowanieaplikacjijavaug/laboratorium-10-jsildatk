package webdemo.seleniumDemo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestInfUg {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);
        driver = new ChromeDriver(chromeOptions);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        driver.get("https://inf.ug.edu.pl");
    }
    
    @Test
    public void countLinks() {
        List<WebElement> list = driver.findElements(By.xpath(".//a"));
        assertEquals(58, list.size());
    }
    
    @Test
    public void countImages() {
        List<WebElement> list = driver.findElements(By.xpath(".//img"));
        assertEquals(15, list.size());
    }
    
    @Test
    public void testLinks() {
        String startTitle = driver.getTitle();
        List<String> hrefs = driver.findElements(By.xpath("//a[@href and string-length(@href)!=0 ]"))
                .stream()
                .map(x -> x.getAttribute("href"))
                .filter(x -> !x.endsWith("xml"))
                .filter(x -> !x.startsWith("mailto"))
                .collect(Collectors.toList());
        
        for ( String href : hrefs ) {
            driver.get(href);
            driver.navigate()
                    .back();
        }
        assertEquals(startTitle, driver.getTitle());
    }
    
    @Test
    public void countInputs() {
        driver.get("https://inf.ug.edu.pl/awaria");
        List<WebElement> formElements = driver.findElement(By.xpath("//form"))
                .findElements(By.xpath("./*"));
        assertFalse(formElements.isEmpty());
    }
    
    @AfterAll
    public static void tearDown() {
        driver.close();
    }
    
}
