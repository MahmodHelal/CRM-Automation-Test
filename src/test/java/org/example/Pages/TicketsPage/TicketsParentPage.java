package org.example.Pages.TicketsPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class TicketsParentPage {
    WebDriverWait WAIT = new WebDriverWait(Hooks.getDriver(), java.time.Duration.ofSeconds(10));
    WebDriverWait SHORT_WAIT = new  WebDriverWait(Hooks.getDriver(), java.time.Duration.ofSeconds(2));
    Actions action = new Actions(Hooks.getDriver());
    JavascriptExecutor JS = (JavascriptExecutor) Hooks.getDriver();


public void searchForSpecificTicket(String diploma,String ticketType) {
    // Implementation for searching for a specific ticket
    By diplomaDropdown = By.xpath("//span[text()='Diploma']/parent::div");
    selectDropdownByVisibleText(diplomaDropdown, diploma);
    By ticketTypeDropdown = By.xpath("//span[text()='Type']/parent::div");
    selectDropdownByVisibleText(ticketTypeDropdown, ticketType);

}
public void clickSearchButton() {
    // Implementation for clicking the search button
    WebElement searchButton = Hooks.getDriver().findElement(By.xpath("//button[.//span[text()='Search']]"));
    searchButton.click();
    // XPath for the SVG that appears during search
    /*By loadingSvg = By.xpath("//button[@aria-label='Search']//svg");

    // Wait for the SVG to appear (indicating search has started)
    wait.until(ExpectedConditions.presenceOfElementLocated(loadingSvg));

    // Wait for the SVG to disappear (indicating search is complete)
    wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSvg));*/
}


    protected  void selectDropdownByVisibleText(By elemLocator, String value) {
        try {
            // Wait until the dropdown is visible and click to open the dropdown
            WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator)).click();

            // Wait for the list of options to be loaded
            WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@role='listbox']")));

            int maxRetries = 10; // Maximum number of retries for scrolling
            int retryCount = 0;
            boolean found = false;
            List<WebElement> options = null;

            while (retryCount < maxRetries && !found) {
                // Check if the dropdown has a search box for filtering
                String locatorCheck = elemLocator.toString();
                if (locatorCheck.contains("Employee name")) {
                    // If it's a search box dropdown, type the value to filter options
                    Hooks.getDriver().findElement(By.xpath("//input[@role='searchbox']")).sendKeys(value);
                    options = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));
                } else {
                    // If no search box, load the options normally
                    options = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));
                }

                // Loop through the options to find the matching one
                for (WebElement option : options) {
                    String optionText = option.getText().trim();
                    if (optionText.equalsIgnoreCase(value.trim())) {
                        // Scroll to the option and click it
                        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                        WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                        System.out.println("Scrolled to and selected: " + optionText);
                        found = true;
                        break;
                    }
                }

                // Scroll down to load more options if not found
                if (!found) {
                    JS.executeScript("arguments[0].scrollBy(0, 100);", Hooks.getDriver()); // Scroll down by 100 pixels
                    retryCount++;
                }
            }

            // Close the dropdown by sending ESC
            action.sendKeys(Keys.ESCAPE).perform();

            // Now verify if the value is displayed in the selected area
            WebElement element = null;

            try {
                // Check for the text in the selected element using the main locator
                element = SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + value.trim() + "']")));
            } catch (Exception e) {
                // If not found, check for it inside the multiselect label div
                WebElement parentElement = SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));
                element = SHORT_WAIT.until(ExpectedConditions.visibilityOf(parentElement.findElement(By.xpath("//div[@class='p-multiselect-label' and contains(normalize-space(), '" + value + "')]"))));
            }

            // Assert that the element is visible after the selection
            Assert.assertTrue(element != null && element.isDisplayed(), "Element with text '" + value + "' is not visible.");

        } catch (Exception e) {
            throw new RuntimeException("Failed to select dropdown value: " + value, e);
        }
    }

}
