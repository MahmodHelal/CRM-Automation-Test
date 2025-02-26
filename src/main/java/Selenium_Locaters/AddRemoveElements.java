package Selenium_Locaters;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AddRemoveElements {
    static WebDriver driver = new FirefoxDriver();
    static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    public static void main(String[] args) {
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.linkText("Add/Remove Elements")).click();

        int added = 3;    // Number of elements to add
        int deleted = 5;  // Number of elements to delete

        // Ensure deletions don't exceed the number of added elements
        int maxDeletions = Math.min(added, deleted);  // Cap the deletions to the number of added elements
        int expectedRemaining = Math.max(0, added - maxDeletions); // Remaining elements should never be negative

        // Add elements
        addElement(added);
        // Validate that the correct number of elements were added
        validateNumberOfAdded(added);

        // Delete elements
        deleteElements(maxDeletions);
        // Validate that the correct number of elements remain after deletion
        validateNumberOfRemaining(expectedRemaining);

        driver.quit(); // Close the browser after the test
    }

    // Method to add elements
    public static void addElement(int counter) {
        WebElement addButton = driver.findElement(By.cssSelector("button[onclick='addElement()']"));

        // Click the "Add Element" button the given number of times
        for (int i = 0; i < counter; i++) {
            addButton.click();
            System.out.println("Clicked 'Add Element' " + (i + 1) + " time(s).");

            // Wait for the new "Delete" button to appear after each click
            waitForElementToAppear(By.cssSelector("#elements .added-manually"));
        }
    }

    // Method to delete elements
    public static void deleteElements(int counter) {
        List<WebElement> deleteButtons = driver.findElements(By.cssSelector("#elements .added-manually"));
        int availableDeleteButtons = deleteButtons.size();

        // Ensure the counter does not exceed the available buttons
        int maxDeletions = Math.min(counter, availableDeleteButtons);

        // Click on the delete buttons
        for (int i = 0; i < maxDeletions; i++) {
            deleteButtons.get(i).click();
            System.out.println("Clicked 'Delete Element' " + (i + 1) + " time(s).");
        }
    }


    // Wait for an element to appear
    private static void waitForElementToAppear(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait for all elements to be present
    private static void waitForAllElementsToAppear(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // Validation to ensure the correct number of elements were added
    public static void validateNumberOfAdded(int expectedCount) {
        waitForAllElementsToAppear(By.cssSelector("#elements .added-manually"));

        List<WebElement> addedElements = driver.findElements(By.cssSelector("#elements .added-manually"));
        int actualCount = addedElements.size();

        if (actualCount == expectedCount) {
            System.out.println("Validation Passed: " + expectedCount + " elements added.");
        } else {
            System.out.println("Validation Failed: Expected " + expectedCount + " elements, but found " + actualCount + ".");
        }
    }

    // Validation to ensure the correct number of elements remain after deletion
    public static void validateNumberOfRemaining(int expectedCount) {
        // Capture the current number of elements after deletion
        List<WebElement> remainingElements = driver.findElements(By.cssSelector("#elements .added-manually"));
        int remainingCount = remainingElements.size();

        if (remainingCount == expectedCount) {
            System.out.println("Validation Passed: " + expectedCount + " remaining elements.");
        } else {
            System.out.println("Validation Failed: Expected " + expectedCount + " remaining elements, but found " + remainingCount + ".");
        }
    }
}
