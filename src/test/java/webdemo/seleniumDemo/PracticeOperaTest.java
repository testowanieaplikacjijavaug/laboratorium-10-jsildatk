package webdemo.seleniumDemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PracticeOperaTest {
    
    private static WebDriver driver;
    
    @BeforeAll
    public static void setUpDriver() {
        System.setProperty("webdriver.opera.driver", "resources/operadriver");
        OperaOptions operaOptions = new OperaOptions();
        operaOptions.addArguments("--headless");
        driver = new OperaDriver(operaOptions);
        // Implicity wait -> max czas na znalezienie elementu na stronie
        driver.manage()
                .timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeEach
    public void setUp() {
        driver.get("http://automationpractice.com/index.php");
    }
    
    @Test
    public void clickLinkByText() {
        driver.findElement(By.linkText("Selenium Framework"))
                .click();
        assertTrue(driver.getTitle()
                .contains("Selenium Framework"));
    }
    
    @Test
    public void getH1Text() {
        assertEquals("Automation Practice Website", driver.findElement(By.xpath("//*[@id=\"editorial_block_center\"]/h1"))
                .getText());
    }
    
    @Test
    public void checkIfIsDisplayed() {
        assertTrue(driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[6]/div/div[1]/div/a[1]/img"))
                .isDisplayed());
    }
    
}
