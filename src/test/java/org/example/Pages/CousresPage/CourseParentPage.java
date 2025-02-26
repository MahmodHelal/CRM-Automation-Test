package org.example.Pages.CousresPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * The base class for all Course-related pages.
 * It provides common methods for interacting with UI elements such as buttons, dropdowns, and page loading.
 */
public class CourseParentPage {
    // ✅ Thread-safe WebDriver access
    protected final WebDriver driver = Hooks.getDriver();
    protected final Actions actions = new Actions(driver);
    protected final JavascriptExecutor js = (JavascriptExecutor) driver;
    protected final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    protected final WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));

    // ✅ URL of the course management page
    protected static final String COURSE_PAGE_URL = "https://crmdev.amit-learning.com/listcourses";

    // ✅ Locators
    private final By addNewCourseButton = By.xpath("//span[contains(text(),'New Course')]");
    private final By switchButtonLocator = By.xpath("//button[@data-course='%s']");
    private final By actionButtonLocator = By.xpath("//button[@data-action='%s']");

    /**
     * Clicks the "Add New Course" button.
     * Waits until the button is clickable before performing the action.
     */
    public void clickAddNewCourseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewCourseButton)).click();
    }

    /**
     * Clicks the switch button for a specified course.
     *
     * @param courseName The name of the course to switch to.
     */
    public void clickSwitchButton(String courseName) {
        By locator = By.xpath(String.format(switchButtonLocator.toString(), courseName));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Selects an action (Edit, Delete, etc.) for a course.
     *
     * @param action The action to perform (e.g., "Edit", "Delete").
     */
    public void selectAction(String action) {
        By locator = By.xpath(String.format(actionButtonLocator.toString(), action));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Selects a value from a dropdown menu by visible text.
     * Handles dropdown opening, option selection, and exception cases.
     *
     * @param dropdownTrigger The locator of the dropdown button.
     * @param optionsLocator  The locator of the dropdown options container.
     * @param value           The visible text of the option to select.
     */
    protected void selectDropdownByVisibleText(By dropdownTrigger, By optionsLocator, String value) {
        try {
            // ✅ Open dropdown
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownTrigger));
            dropdown.click();

            // ✅ Wait for options to load
            WebElement optionsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(optionsLocator));
            List<WebElement> options = optionsContainer.findElements(By.tagName("li"));

            // ✅ Find and select matching option
            options.stream()
                    .filter(option -> option.getText().trim().equalsIgnoreCase(value))
                    .findFirst()
                    .ifPresentOrElse(
                            this::safeClickOption,
                            () -> { throw new NoSuchElementException("⚠ Option not found: " + value); }
                    );

        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to select dropdown value '" + value + "' in dropdown: " + dropdownTrigger, e);
        }
    }

    /**
     * Attempts to click an option safely.
     * Handles cases where elements might be obstructed or not immediately clickable.
     *
     * @param option The WebElement representing the dropdown option.
     */
    private void safeClickOption(WebElement option) {
        try {
            option.click();
        } catch (ElementClickInterceptedException e) {
            // ✅ Scroll into view and retry clicking using JavaScript
            js.executeScript("arguments[0].scrollIntoView({behavior: 'auto', block: 'center'});", option);
            js.executeScript("arguments[0].click();", option);
        }
    }

    /**
     * Waits for the page to fully load by checking the document ready state.
     */
    protected void waitForPageLoad() {
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
    }
}
