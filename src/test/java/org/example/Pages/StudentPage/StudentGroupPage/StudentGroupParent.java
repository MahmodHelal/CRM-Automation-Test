package org.example.Pages.StudentPage.StudentGroupPage;

import org.example.Pages.StudentPage.StudentParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.NoSuchElementException;

public class StudentGroupParent extends StudentParent {

    /**
     * Clicks on the button with the given page title and waits for navigation.
     *
     * @param pageTitle The title of the page that the button leads to.
     */
    public void clickOnTicketsButton(String pageTitle) {
        WebDriver driver = Hooks.getDriver(); // ✅ Thread-safe WebDriver
        String currentURL = driver.getCurrentUrl();

        try {
            // Locate the button dynamically based on the page title.
            By buttonLocator = By.xpath("//button[.//span[text()='" + pageTitle + "']]");
            WebElement button = WAIT.until(ExpectedConditions.elementToBeClickable(buttonLocator));

            button.click(); // Click the button
            WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL))); // Wait for URL to change

            System.out.println("✅ Successfully navigated to: " + pageTitle);

        } catch (NoSuchElementException e) {
            System.err.println("❌ Error: Button with title '" + pageTitle + "' not found.");
        } catch (Exception e) {
            System.err.println("❌ Unexpected error while clicking on '" + pageTitle + "': " + e.getMessage());
        }
    }
}
