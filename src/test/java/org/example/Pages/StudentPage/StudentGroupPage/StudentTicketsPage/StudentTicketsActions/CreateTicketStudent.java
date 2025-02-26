package org.example.Pages.StudentPage.StudentGroupPage.StudentTicketsPage.StudentTicketsActions;

import org.example.Pages.StudentPage.StudentGroupPage.StudentTicketsPage.StudentTicketsParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CreateTicketStudent extends StudentTicketsParent {
    private final WebDriverWait WAIT = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10));
    private final WebDriverWait SHORT_WAIT = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(5));
    private final JavascriptExecutor JS = (JavascriptExecutor) Hooks.getDriver();
    private final Actions ACTIONS = new Actions(Hooks.getDriver());

    /**
     * Fills in the ticket creation form.
     *
     * @param ticketType       The type of the ticket.
     * @param ticketTitle      The title of the ticket.
     * @param ticketCourse     The related course for the ticket.
     * @param ticketDescription The description of the ticket.
     */
    public void fillTicketForm(String ticketType, String ticketTitle, String ticketCourse, String ticketDescription) {
        try {
            selectDropdownByVisibleText(By.xpath("//label[text()='Ticket Type']/following-sibling::div"), ticketType);

            WebElement titleInput = Hooks.getDriver().findElement(By.id("name"));
            titleInput.sendKeys(ticketTitle);

            selectDropdownByVisibleText(By.xpath("//label[text()='Course']/following-sibling::div"), ticketCourse);

            WebElement descriptionInput = Hooks.getDriver().findElement(By.xpath("//label[@for='description']/following::textarea"));
            descriptionInput.sendKeys(ticketDescription);

            System.out.println("✅ Ticket form filled successfully.");
        } catch (Exception e) {
            System.err.println("❌ Error filling ticket form: " + e.getMessage());
        }
    }

    /**
     * Clicks on the "Create Ticket" button.
     *
     * @param buttonText The text on the button to click.
     */
    public void clickOnCreateTicketButton(String buttonText) {
        try {
            String currentURL = Hooks.getDriver().getCurrentUrl();
            WebElement createTicketButton = Hooks.getDriver().findElement(By.xpath("//button[.//span[text()='" + buttonText + "']]"));
            createTicketButton.click();
            WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
            System.out.println("✅ Clicked on '" + buttonText + "' button.");
        } catch (Exception e) {
            System.err.println("❌ Failed to click '" + buttonText + "' button: " + e.getMessage());
        }
    }

    /**
     * Selects an option from a dropdown by visible text.
     *
     * @param elemLocator The dropdown element locator.
     * @param value       The value to select.
     */
    protected void selectDropdownByVisibleText(By elemLocator, String value) {
        try {
            WebElement dropdown = WAIT.until(ExpectedConditions.elementToBeClickable(elemLocator));
            dropdown.click();

            // Handle searchable dropdowns
            if (elemLocator.toString().contains("Employee name") || elemLocator.toString().contains("reporting")) {
                WebElement searchBox = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@role='searchbox']")));
                searchBox.clear();
                searchBox.sendKeys(value.trim());
                Thread.sleep(300);
            }

            List<WebElement> options = WAIT.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                    By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));

            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(value.trim())) {
                    JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                    Thread.sleep(150);
                    WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                    System.out.println("✅ Selected option: " + value);
                    return;
                }
            }

            throw new NoSuchElementException("❌ Option not found: " + value);
        } catch (Exception e) {
            System.err.println("❌ Failed to select dropdown value: " + value + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
