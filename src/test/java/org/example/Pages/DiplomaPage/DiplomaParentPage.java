package org.example.Pages.DiplomaPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

/**
 * Base class for Diploma-related pages.
 * Provides common utilities for handling diplomas, including dropdowns, file paths, and button interactions.
 */
public class DiplomaParentPage {
    protected final WebDriver driver = Hooks.getDriver();
    protected final Actions ACTIONS = new Actions(driver);
    protected final JavascriptExecutor JS = (JavascriptExecutor) driver;
    protected final WebDriverWait SHORT_WAIT = new WebDriverWait(driver, Duration.ofSeconds(2));
    protected final WebDriverWait WAIT = new WebDriverWait(driver, Duration.ofSeconds(10));

    // ✅ Project paths for storing diploma-related files (Images & PDFs)
    protected final String projPath = System.getProperty("user.dir");
    protected final String imgPath = projPath + File.separator + "Images";
    protected final String pdfPath = projPath + File.separator + "PDF";

    /**
     * Clicks the "New Diploma" button.
     * Uses JavaScript execution to ensure reliable clicking if the button is not directly interactable.
     */
    public void addNewDiplomaButton() {
        WebElement element = WAIT.until(ExpectedConditions.elementToBeClickable(
                driver.findElement(By.xpath("//span[contains(text(),'New Diploma')]"))
        ));
        JS.executeScript("arguments[0].click();", element);
    }

    /**
     * Selects an option from a dropdown menu by visible text.
     * Handles dropdown loading, scrolling, and exception cases for better stability.
     *
     * @param elemLocator The locator of the dropdown button.
     * @param value       The visible text of the option to select.
     */
    protected void selectDropdownByVisibleText(By elemLocator, String value) {
        try {
            // ✅ Open dropdown
            WAIT.until(ExpectedConditions.elementToBeClickable(elemLocator)).click();

            // ✅ Wait for dropdown options to load
            WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@role='listbox']")));

            // ✅ Get all available options
            List<WebElement> options = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));

            // ✅ Find and select the matching option
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(value.trim())) {
                    try {
                        // Scroll into view and click normally
                        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                        SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                    } catch (ElementClickInterceptedException e) {
                        // Fallback: Click using JavaScript if normal click fails
                        JS.executeScript("arguments[0].scrollIntoView(true);", option);
                        JS.executeScript("arguments[0].click();", option);
                    }
                    break;
                }
            }

            // ✅ Close dropdown using ESC key
            ACTIONS.sendKeys(Keys.ESCAPE).perform();

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to select dropdown value: " + value, e);
        }
    }
}
