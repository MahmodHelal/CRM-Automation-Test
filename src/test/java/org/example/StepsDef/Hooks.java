package org.example.StepsDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.Helpers.ScenarioContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Hooks {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    public static ScenarioContext scenarioContext = new ScenarioContext();
    private static Dotenv dotenv = Dotenv.load();


    @Before
    public void setup() {
        // Read browser from system properties, default to "chrome"
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        WebDriver driver = null;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "safari":
                // SafariDriver usually does not require WebDriverManager setup
                driver = new SafariDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        System.out.println("Launching browser: " + browser);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://crmtest.amit-learning.com/login");

        driverThreadLocal.set(driver);
    }

   /* @After
    public void tearDown() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            System.out.println("Closing browser...");
            driver.quit();
            driverThreadLocal.remove();
        }
    }*/

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static String getBaseUrl() {
        return dotenv.get("BASE_URL");
    }

    public static String getOwnerEmail() {
        return dotenv.get("OWNER_EMAIL");
    }

    public static String getOwnerPassword() {
        return dotenv.get("OWNER_PASSWORD");
    }

    public static String getAdminEmail() {
        return dotenv.get("ADMIN_EMAIL");
    }

    public static String getAdminPassword() {
        return dotenv.get("ADMIN_PASSWORD");
    }
}
