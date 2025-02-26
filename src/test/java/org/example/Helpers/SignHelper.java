package org.example.Helpers;

import org.example.Pages.LoginPage;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignHelper {
    protected final WebDriver driver = Hooks.getDriver();
    // Page Object representing the login page
    private final LoginPage loginPage = new LoginPage();

    // WebDriverWait instance for handling synchronization
    private final WebDriverWait WAIT = new WebDriverWait(driver, Duration.ofSeconds(15));
    public void signOut() {
        try {
            String currentURL = Hooks.getDriver().getCurrentUrl();
            driver.findElement(By.xpath("//button[.//i[@class='pi pi-user']]")).click();
            WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Logout')]"))).click();
            WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
            System.out.println("✅ Successfully logged out.");
        } catch (Exception e) {
            System.err.println("❌ Error during logout: " + e.getMessage());
        }
    }
    /**
     * Performs login using the provided email and password.
     * This method navigates to the login page, enters credentials,
     * handles any overlays, and ensures login success.
     *
     * @param email    User's email address for login
     * @param password User's password for login
     */
    public void signIn(String email, String password) {
        // Navigate to the login page
        Hooks.getDriver().get("https://crmdev.amit-learning.com/login");

        // Enter login credentials
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);

        // Handle potential overlay popups that might block interaction
        try {
            Hooks.getDriver().switchTo().activeElement().sendKeys(Keys.ESCAPE);
        } catch (Exception e) {
            System.out.println("⚠ No overlay detected, continuing login process.");
        }

        // Click the login button
        loginPage.submitLogin();

        // Wait until the dashboard page is loaded (verifying successful login)
        WAIT.until(ExpectedConditions.urlContains("/dashboard"));

        System.out.println("✅ Login successful: Redirected to the dashboard.");
    }
}
/*
protected void selectDropdownByVisibleText(By elemLocator, String value) {
    try {
        int maxScrollAttempts = 10; // Limit scrolling attempts
        int scrollCount = 0;
        boolean isFound = false;

        while (scrollCount < maxScrollAttempts) {
            try {
                // Attempt to find and click the dropdown
                WebElement dropdown = WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));
                JS.executeScript("arguments[0].click();", dropdown);
//                    System.out.println("Dropdown found and clicked.");
                isFound = true;
                break; // Exit loop once found
            } catch (TimeoutException | NoSuchElementException e) {
                // If not found, scroll down and retry
//                    System.out.println("Dropdown not found. Scrolling attempt: " + (scrollCount + 1));
                JS.executeScript("window.scrollBy(0, 100);"); // Scroll down
                scrollCount++;
                try {
                    Thread.sleep(100); // Short delay to allow UI updates
                } catch (InterruptedException ignored) {}
            }
            if (!isFound) {
                throw new RuntimeException("Failed to locate and click the dropdown after scrolling.");
            }
        }
        // Wait for the list of options to be loaded
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//ul[@role='listbox']")));

        int maxRetries = 10; // Maximum number of retries for scrolling
        int retryCount = 0;
        boolean found = false;
        List<WebElement> options = null;

        while (retryCount < maxRetries && !found) {
            // Check if the dropdown has a search box for filtering
            String locatorCheck = elemLocator.toString();
            if (locatorCheck.contains("Employee name") || locatorCheck.contains("reporting")) {
                // If it's a search box dropdown, type the value to filter options
                WebElement searchBox = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@role='searchbox']")));
                searchBox.clear();
                searchBox.sendKeys(value.trim());
                // Wait briefly for filter results to update
                Thread.sleep(50);
                options = WAIT.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")
                ));
            } else {
                // If no search box, load the options normally
                options = WAIT.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")
                ));
            }

            // Log the options available for debugging
//                System.out.println("Attempt " + (retryCount + 1) + ": Found options - " + options.stream().map(o -> o.getText().trim()).collect(Collectors.joining(", ")));

            // Loop through the options to find the matching one
            for (int i = 0; i < options.size(); i++) {
                WebElement option = options.get(i);
                String optionText = option.getText().trim();
                if (optionText.equalsIgnoreCase(value.trim())) {
//                        System.out.println("Found matching option: " + optionText + " at index " + i);
                    try {
                        // Scroll to the option and attempt to click it
                        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                        // Adding a short pause to allow any animations to finish
                        Thread.sleep(50);
                        WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                        System.out.println("Clicked on option: " + optionText);
                        found = true;
                        break;
                    } catch (StaleElementReferenceException ser) {
                        System.out.println("Stale element encountered for option: " + optionText + ". Re-fetching options...");
                        // Option might have refreshed, break out and retry the entire loop
                        break;
                    } catch (Exception clickException) {
//                            System.out.println("Failed to click option: " + optionText + ". Attempting to click the first row as fallback.");
                        // Attempt to click the first row if available
                        if (i == 0) {
                            try {
                                // Try re-finding the first option and click again
                                WebElement firstOption = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                                        By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")
                                )).get(0);
                                JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", firstOption);
                                Thread.sleep(50);
                                WAIT.until(ExpectedConditions.elementToBeClickable(firstOption)).click();
//                                    System.out.println("Fallback: Clicked on the first option: " + firstOption.getText().trim());
                                found = true;
                                break;
                            } catch (Exception fallbackEx) {
                                System.out.println("Fallback failed for first row option. " + fallbackEx.getMessage());
                            }
                        }
                    }
                }
            }

            // If the desired option was not found, scroll down to load more options and try again.
            if (!found) {
//                    System.out.println("Desired option not found. Scrolling further. Retry count: " + (retryCount + 1));
                JS.executeScript("arguments[0].scrollBy(0, 100);", driver); // Scroll down by 100 pixels
                retryCount++;
                Thread.sleep(100); // Small wait to allow options to load after scrolling
            }
        }

        if (!found) {
            throw new RuntimeException("Failed to find or click the dropdown value: " + value);
        }

        // Close the dropdown by sending ESC
        ACTIONS.sendKeys(Keys.ESCAPE).perform();

        // Now verify if the value is displayed in the selected area
        WebElement element = null;

        try {
            // Check for the text in the selected element using the main locator
            element = SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[text()='" + value.trim() + "']")
            ));
        } catch (Exception e) {
            // If not found, check for it inside the multiselect label div
            element = SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='p-multiselect-label' and contains(normalize-space(), '" + value.trim() + "')]")
            ));
        }

        // Assert that the element is visible after the selection
        Assert.assertTrue(element != null && element.isDisplayed(),
                "Element with text '" + value.trim() + "' is not visible.");
    } catch (Exception e) {
        throw new RuntimeException("Failed to select dropdown value: " + value, e);
    }
}
*/
