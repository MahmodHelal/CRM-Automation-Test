package org.example.Pages.Employees.EmployementParent;

import com.github.javafaker.Faker;
import org.example.Helpers.HelperMethods;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * Parent class for Employee-related pages.
 * Provides common utilities for creating, searching, and handling employees.
 */
public class EmployeeParent {
    // ✅ Project Paths
    protected final String PROJ_PATH = System.getProperty("user.dir");
    protected final Random random = new Random();
    protected final String PDF_PATH = PROJ_PATH + File.separator + "PDF";
    protected final String IMG_PATH = PROJ_PATH + File.separator + "Images";

    // ✅ URLs
    public static final String CREATE_PAGE_URL = "https://crmtest.amit-learning.com/employees/create-employee-profile";
    public static final String EMPLOYEES_REPORT_PAGE = "https://crmtest.amit-learning.com/employees";

    // ✅ WebDriver utilities
    protected final WebDriver driver;
    protected final WebDriverWait WAIT;
    protected final WebDriverWait SHORT_WAIT;
    protected final Actions ACTIONS;
    protected final JavascriptExecutor JS;
    protected final Faker faker = new Faker();
    protected final HelperMethods helperMethods = new HelperMethods();

    public EmployeeParent() {
        this.driver = Hooks.getDriver();

        // Ensure the driver is correctly cast to JavascriptExecutor
        if (!(driver instanceof JavascriptExecutor)) {
            throw new IllegalStateException("Driver is not a valid JavascriptExecutor");
        }

        this.JS = (JavascriptExecutor) driver;
        this.WAIT = new WebDriverWait(driver, Duration.ofSeconds(100));
        this.SHORT_WAIT = new WebDriverWait(driver, Duration.ofSeconds(5));
        this.ACTIONS = new Actions(driver);
    }

    // ✅ Common Locators
    public static final By HEADER_OF_SECTION = By.xpath("//div[@class='section-content']//h3");

    /**
     * Clicks the "Create Employee" button and waits for the "Employee Information" section to appear.
     */
    public void clickCreateEmployeeButton() {
        String currentUrl = driver.getCurrentUrl();
        WAIT.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[text()='Add New']]"))).click();
        WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl)));
//        WAIT.until(ExpectedConditions.textToBe(HEADER_OF_SECTION, "Employee Information"));
    }

    /**
     * Searches for an employee using the search input field.
     */
    public void searchFor(String target) {
        WebElement searchField = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Search...']")));
        searchField.clear();
        searchField.sendKeys(target);
        driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
    }

    /**
     * Retrieves the "Edit Employee" button.
     */
    public WebElement editEmployeeButton() {
        return driver.findElement(By.xpath("//button[@title='Edit']"));
    }

    /**
     * Retrieves the "View Employee" button.
     */
    public WebElement viewEmployeeButton() {
        return driver.findElement(By.xpath("//button[@title='Show One']"));
    }

    /**
     * Checks if a given input field is empty.
     */
    public boolean isFieldEmpty(WebElement field) {
        String value = field.getAttribute("value");
        return value == null || value.trim().isEmpty();
    }




    /**
     * Sets a value in an input field.
     */
    protected void setInputField(By locator, String value) {
        WebElement inputField = WAIT.until(ExpectedConditions.presenceOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(value);
    }

    /**
     * Clicks the "Next" button.
     */
    public void nextButton() {
        driver.findElement(By.xpath("//button[text()='Save and Next']")).click();
    }

    /**
     * Retrieves the "Previous" button.
     */
    public WebElement previousButton() {
        return driver.findElement(By.xpath("//button[text()=' Previous ']"));
    }

    /**
     * Clicks the "Submit" button and waits for the URL to change.
     */
    public void submitButton() {
        String currentURL = driver.getCurrentUrl();
        driver.findElement(By.xpath("//button[text()='Submit']")).click();
        helperMethods.WAITForPopupToDisappear();
        WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }

    /**
     * Dismisses an alert popup if present.
     */
    public void dismissAlert() {
        try {
            Alert alert = SHORT_WAIT.until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                alert.dismiss();
                WAIT.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".swal2-popup")));
            }
        } catch (TimeoutException e) {
            System.out.println("ℹ Alert did not appear, continuing execution.");
        } catch (Exception e) {
            throw new RuntimeException("❌ Unexpected error while dismissing alert: " + e.getMessage());
        }
    }
}
