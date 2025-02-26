package org.example.Pages.Requests;

import org.example.Helpers.HelperMethods;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestsPage {

    // Constants for timeouts and polling intervals
    private static final Duration TIMEOUT = Duration.ofSeconds(40);
    private static final Duration SHORT_TIMEOUT = Duration.ofSeconds(5);
    private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);
    private static final Duration CELL_WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Instance variables
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final WebDriverWait shortWait;
    private final Actions actions;
    private final JavascriptExecutor js;
    HelperMethods helperMethods = new HelperMethods();

    // Locators
    private static final By TABLE_HEADERS = By.xpath("//table//thead//th");
    private static final By TABLE_ROWS = By.xpath("//table//tbody//tr");
    private static final By SEARCH_BUTTON = By.xpath("//button[.//span[contains(text(),'Search')]]");
    private static final By TABLE_CONTAINER = By.cssSelector("table.p-datatable-table tbody");
    private static final By APPROVE_BUTTON = By.xpath(".//button[contains(@class, 'approve-btn')]");
    private static final By REJECT_BUTTON = By.xpath(".//button[contains(@class, 'reject-btn')]");
    private static final By CONFIRM_BUTTON = By.xpath("//button[contains(@class, 'swal2-confirm') and text()='Yes']");
    private static final By COMMENT_TEXTAREA = By.xpath("//textarea[@placeholder='Enter your comment here...']");
    private static final By SUBMIT_BUTTON = By.xpath("//button[contains(@class, 'swal2-confirm') and text()='Submit']");
    private static final By POPUP = By.className("swal2-popup");

    public RequestsPage() {
        this.driver = Hooks.getDriver();
        this.wait = new WebDriverWait(driver, TIMEOUT);
        this.shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    public void searchForEmployee(String empName) {
        helperMethods.selectDropdownByVisibleText(By.xpath("//span[text()='Employee name']"), empName.trim());
        clickSearchButton();
    }

    public void clickSearchButton() {
        // ✅ Capture initial table state
        int initialRowCount = driver.findElements(TABLE_ROWS).size();
        String initialTableHTML = getTableHTML();

        // ✅ Click the search button
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON)).click();

        // ✅ Ensure any popups disappear before waiting for table update
//        helperMethods.WAITForPopupToDisappear();

        // ✅ Wait for both row count change OR HTML content change
//        waitForTableUpdate(initialRowCount, initialTableHTML);
    }


    public WebElement getRowBySearch(String header, String searchValue) {
        int columnIndex = getColumnIndex(header);
        List<WebElement> rows = waitForUpdatedRows();  // ✅ Always fetch the latest table rows

        for (int attempt = 0; attempt < 3; attempt++) {  // ⏳ Reduce retry attempts to 3
            for (WebElement row : rows) {
                try {
                    String cellText = row.findElement(By.xpath("./td[" + columnIndex + "]")).getText().trim();
                    if (cellText.equalsIgnoreCase(searchValue.trim())) {
                        return row;
                    }
                } catch (StaleElementReferenceException e) {
                    System.out.println("⚠ Stale row detected. Retrying...");
                    break;  // Retry fetching fresh rows
                }
            }

            // ✅ Wait before retrying if no match was found
            if (attempt < 2) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                rows = waitForUpdatedRows(); // ✅ Re-fetch updated rows
            }
        }

        throw new NoSuchElementException("❌ No row found with " + header + " containing: " + searchValue);
    }
    public boolean waitForQuickStatusUpdate(WebElement row, String expectedStatus) {
        try {
            return new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(2))  // ✅ Short wait (2s)
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(StaleElementReferenceException.class)
                    .until(d -> {
                        String actualStatus = getCellDataFromRow(row, "Final Status");  // ✅ Uses the row reference
                        return actualStatus.equalsIgnoreCase(expectedStatus);
                    });
        } catch (TimeoutException e) {
            return false;  // ✅ If status doesn't update in 2s, refresh is needed
        }
    }

    public String getCellDataFromRow(WebElement row, String header) {
        int columnIndex = getColumnIndex(header);

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    try {
                        return row.findElement(By.xpath("./td[" + columnIndex + "]")).getText().trim();
                    } catch (StaleElementReferenceException e) {
                        System.out.println("⚠ Stale row detected. Retrying...");
                        return ""; // Return empty string and retry polling
                    }
                });
    }



    public enum ActionType {
        APPROVE, REJECT
    }

    public void clickActionButton(WebElement row, ActionType action) {
        By buttonLocator = (action == ActionType.APPROVE) ? APPROVE_BUTTON : REJECT_BUTTON;

        row.findElement(buttonLocator).click();
        confirmAction();

        if (action == ActionType.REJECT) {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(COMMENT_TEXTAREA))
                    .sendKeys("Automated rejection comment");
            shortWait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
        }

        helperMethods.WAITForPopupToDisappear();
    }


    private void waitForTableUpdate(int initialRowCount, String initialTableHTML) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))  // ⏳ Adjust timeout
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    List<WebElement> rows = driver.findElements(TABLE_ROWS);
                    int newRowCount = rows.size();

                    // ✅ Condition 1: Row count changed
                    if (newRowCount != initialRowCount) {
                        System.out.println("✔ Table updated: New row count = " + newRowCount);
                        return true;
                    }

                    // ✅ Condition 2: HTML content changed
                    String currentHTML = getTableHTML();
                    assert currentHTML != null;
                    if (!currentHTML.equals(initialTableHTML)) {
                        System.out.println("✔ Table updated: HTML content changed.");
                        return true;
                    }

                    return false;
                });
    }


    private void confirmAction() {
        shortWait.until(ExpectedConditions.elementToBeClickable(CONFIRM_BUTTON)).click();
        helperMethods.WAITForPopupToDisappear();
    }




    private String getTableHTML() {
        return (String) js.executeScript("return document.querySelector('table').innerHTML;");
    }

    public List<WebElement> waitForUpdatedRows() {
        int initialRowCount = driver.findElements(TABLE_ROWS).size();
        long startTime = System.currentTimeMillis();
        long timeoutMillis = 10000; // 10 seconds timeout
        List<WebElement> rows = null;

        while (System.currentTimeMillis() - startTime < timeoutMillis) {
            rows = driver.findElements(TABLE_ROWS);
            int currentRowCount = rows.size();

            // ✅ 1. Detect if table was cleared (full refresh case)
            if (currentRowCount == 0) {
                System.out.println("⚠ Table was cleared! Waiting for new data...");
            }

            // ✅ 2. If at least one row is present, return updated rows
            if (currentRowCount > 0 && currentRowCount != initialRowCount) {
                System.out.println("✅ Table updated! New row count: " + currentRowCount);
                rows.forEach(row -> waitForNonEmptyCell(row, By.xpath("./td[1]")));
                return rows;
            }

            // ✅ 3. Retry after a short pause (to allow UI update)
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // ❌ If timeout occurs, return whatever rows exist
        System.out.println("⚠ Warning: Table update timeout. Returning latest detected rows.");
        return driver.findElements(TABLE_ROWS);
    }

    private void waitForNonEmptyCell(WebElement row, By cellLocator) {
        new FluentWait<>(driver)
                .withTimeout(CELL_WAIT_TIMEOUT)
                .pollingEvery(POLLING_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    try {
                        return !row.findElement(cellLocator).getText().trim().isEmpty();
                    } catch (NoSuchElementException | StaleElementReferenceException e) {
                        return false;
                    }
                });
    }

    private Map<String, Integer> headerIndexCache = new HashMap<>();
    private int getColumnIndex(String header) {
        if (headerIndexCache.isEmpty()) {  // Cache headers only once
            List<WebElement> headers = driver.findElements(TABLE_HEADERS);
            for (int i = 0; i < headers.size(); i++) {
                headerIndexCache.put(headers.get(i).getText().trim().toLowerCase(), i + 1);
            }
        }

        Integer index = headerIndexCache.get(header.trim().toLowerCase());
        if (index != null) return index;

        throw new IllegalArgumentException("Header not found: " + header);
    }
    public void waitForStatusUpdate(String employeeName, String expectedStatus) {
        wait.withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(300))
                .until(driver -> {
                    WebElement row = getRowBySearch("Employee Name", employeeName);  // ✅ Always fetch latest row
                    String actualStatus = row.findElement(By.xpath("./td[" + getColumnIndex("Final Status") + "]")).getText().trim();

                    // ✅ Ensure the row is fully updated
                    boolean allDataUpdated = actualStatus.equalsIgnoreCase(expectedStatus)
                            && !getCellDataFromRow(row, "Department").isEmpty()
                            && !getCellDataFromRow(row, "Job Role").isEmpty();

                    return allDataUpdated;
                });
    }





}
