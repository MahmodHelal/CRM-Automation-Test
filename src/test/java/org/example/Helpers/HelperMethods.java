package org.example.Helpers;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class HelperMethods {
    protected final WebDriver driver = Hooks.getDriver();
    protected  WebDriverWait SHORT_WAIT = new WebDriverWait(driver, Duration.ofSeconds(5));
    protected  WebDriverWait WAIT = new WebDriverWait(driver, Duration.ofSeconds(30));
    protected final JavascriptExecutor JS;
    protected final Actions ACTIONS;

    public HelperMethods() {
        this.JS = (JavascriptExecutor) driver;
        this.ACTIONS = new Actions(driver);
    }

    /**
     * WAITs for any blocking popup (e.g., SweetAlert) to disappear.
     */
    public void WAITForPopupToDisappear() {
        try {
            // **Check if popup exists instantly**
            if (!driver.findElements(By.className("swal2-popup")).isEmpty()) {
                System.out.println("ℹ Popup detected. Waiting for it to disappear...");
                Wait<WebDriver> shortWait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofMillis(2000)) // 2000ms timeout
                        .pollingEvery(Duration.ofMillis(50)) // Check every 50ms
                        .ignoring(NoSuchElementException.class);
                shortWait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("swal2-popup")));
            } else {
                System.out.println("✅ No popup detected. Skipping wait.");
            }
        } catch (TimeoutException e) {
            System.out.println("⚠ Warning: Popup did not disappear within timeout.");
        }
    }
    /**
     * Selects an option from a dropdown menu by visible text.
     */
    public void selectDropdownByVisibleText(By elemLocator, String value) {
        try {
            WebElement dropdown = WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));
//            JS.executeScript("arguments[0].click();", dropdown);
            dropdown.click();
            WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//ul[@role='listbox']")));

            int maxRetries = 10;
            boolean found = false;
            int retryCount = 0;
            List<WebElement> options = null;

            while (retryCount < maxRetries && !found) {
                try {
                    String locatorCheck = elemLocator.toString();
                    if (locatorCheck.contains("Employee name") || locatorCheck.contains("reporting")) {
                        // ✅ If dropdown has a search box, type the value to filter options
                        WebElement searchBox = SHORT_WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@role='searchbox']")));
                        searchBox.clear();
                        searchBox.sendKeys(value.trim());

                        // ✅ Fetch options after filtering
                        options = WAIT.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")
                        ));
                    } else {
                        // ✅ If no search box, load options normally
                        options = WAIT.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")
                        ));
                    }

                    for (WebElement option : options) {
                        if (option.getText().trim().equalsIgnoreCase(value.trim())) {
                            JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                            WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        JS.executeScript("window.scrollBy(0, 100);");
                        WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li")));
                    }

                } catch (StaleElementReferenceException e) {
                    System.out.println("⚠ StaleElementReferenceException caught. Retrying... For :- "+value);
                    retryCount++;
                    Thread.sleep(200); // Short pause before retrying
                }
            }

            if (!found) {
                throw new RuntimeException("❌ Failed to find or click the dropdown value: " + value);
            }

            ACTIONS.sendKeys(Keys.ESCAPE).perform();
            Thread.sleep(100);


            WebElement selectedElement = WAIT.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='" + value.trim() + "'] | //div[@class='p-multiselect-label' and contains(normalize-space(), '" + value.trim() + "')]")
            ));

            Assert.assertTrue(selectedElement.isDisplayed(), "❌ Selected value '" + value.trim() + "' is not visible.");
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to select dropdown value: " + value, e);
        }
    }

    public String getDropdownSelectedValue(By elemLocator) {
        try {
            WebElement dropdown = WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));

            // ✅ Find selected values correctly (multi-select & single-select)
            List<WebElement> selectedElements = dropdown.findElements(By.xpath(
                    ".//div[contains(@class, 'p-multiselect-label-container')] | " +
                            ".//span[contains(@class, 'p-dropdown-label')]"
            ));

            if (selectedElements.isEmpty()) {
                System.out.println("⚠ Warning: No value selected for dropdown: " + elemLocator);
                return "Not Set";  // ✅ Return default value instead of failing
            }

            // ✅ If only one value is selected, return it
            if (selectedElements.size() == 1) {
                return selectedElements.get(0).getText().trim();
            }

            // ✅ Multi-selection: return all selected values joined by ", "
            return selectedElements.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .filter(text -> !text.isEmpty())
                    .reduce((a, b) -> a + ", " + b) // ✅ Joins multiple selections with ", "
                    .orElse("Not Set");

        } catch (Exception e) {
            System.out.println("❌ Error retrieving dropdown value for: " + elemLocator + " - " + e.getMessage());
            return "Error";  // ✅ Prevents test failure if dropdown is missing
        }
    }


}
