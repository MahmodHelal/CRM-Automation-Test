package org.example.Pages.StudentPage.StudentGroupPage.StudentTicketsPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class StudentTicketsParent {
    protected final WebDriverWait WAIT = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10));
    protected final WebDriverWait SHORT_WAIT = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(5));
    protected final JavascriptExecutor JS = (JavascriptExecutor) Hooks.getDriver();
    protected final Actions ACTION = new Actions(Hooks.getDriver());

    /**
     * Clicks on the "New Ticket" button.
     */
    public void clickOnNewTicketButton() {
        try {
            String currentURL = Hooks.getDriver().getCurrentUrl();
            WebElement newTicketButton = WAIT.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[text()='New Ticket']]")));
            newTicketButton.click();
            WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
            System.out.println("✅ Clicked on 'New Ticket' button.");
        } catch (Exception e) {
            System.err.println("❌ Failed to click 'New Ticket' button: " + e.getMessage());
        }
    }

    /**
     * Clicks on a specific ticket based on title, creation date, and status.
     *
     * @param ticketTitle The title of the ticket.
     * @param createdAt   The creation date of the ticket.
     * @param status      The status of the ticket.
     */
    public void clickOnSpecificTicket(String ticketTitle, String createdAt, String status) {
        try {
            List<WebElement> tickets = Hooks.getDriver().findElements(By.xpath("//div[contains(@class, 'card groupCard')]"));

            for (WebElement ticket : tickets) {
                String title = ticket.findElement(By.tagName("h1")).getText().trim();
                String ticketCreatedAt = ticket.findElement(By.tagName("small")).getText().replace("Created At : ", "").trim();
                List<WebElement> statusElements = ticket.findElements(By.xpath(".//span[contains(@class, 'p-tag-value')]"));

                List<String> statuses = new ArrayList<>();
                for (WebElement statusElement : statusElements) {
                    statuses.add(statusElement.getText().trim());
                }

                if (title.equals(ticketTitle) && ticketCreatedAt.equals(createdAt) && statuses.contains(status)) {
                    ticket.click();
                    System.out.println("✅ Clicked on ticket: " + ticketTitle);
                    return;
                }
            }

            System.out.println("❌ No matching ticket found for Title: " + ticketTitle + ", Created At: " + createdAt + ", Status: " + status);
        } catch (Exception e) {
            System.err.println("❌ Error clicking on ticket: " + e.getMessage());
        }
    }

    /**
     * Extracts details of all available tickets.
     *
     * @return A list of ticket details, each represented as a map.
     */
    public List<Map<String, Object>> extractAllTicketsDetails() {
        List<Map<String, Object>> ticketList = new ArrayList<>();

        try {
            List<WebElement> tickets = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'card groupCard')]")));

            for (WebElement ticket : tickets) {
                Map<String, Object> ticketDetails = new HashMap<>();

                try {
                    WebElement titleElement = WAIT.until(ExpectedConditions.visibilityOf(ticket.findElement(By.tagName("h1"))));
                    String ticketTitle = titleElement.getText().trim();
                    ticketDetails.put("title", ticketTitle);

                    List<WebElement> statusElements = ticket.findElements(By.xpath(".//span[contains(@class, 'p-tag-value')]"));
                    List<String> statuses = new ArrayList<>();

                    for (WebElement statusElement : statusElements) {
                        statuses.add(statusElement.getText().trim());
                    }
                    ticketDetails.put("statuses", statuses);

                    if (!ticketTitle.isEmpty() && !statuses.isEmpty()) {
                        ticketList.add(ticketDetails);
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Error extracting ticket details: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Failed to extract tickets: " + e.getMessage());
        }

        return ticketList;
    }

    /**
     * Prints the current page title.
     */
    public void pageTitle() {
        System.out.println("📌 Current Page Title: " + Hooks.getDriver().getTitle());
    }
}
