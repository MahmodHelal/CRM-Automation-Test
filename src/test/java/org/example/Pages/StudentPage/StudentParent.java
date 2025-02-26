package org.example.Pages.StudentPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class StudentParent {
    private static final String STUDENT_URL = "https://crmdev.amit-learning.com/student-profile";
    protected final WebDriverWait WAIT = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(20));

    /**
     * Clicks on a specific group section on the student profile page based on the provided diploma and group names.
     *
     * @param diplomaName The name of the diploma to match.
     * @param groupName   The name of the group to match.
     */
    public void clickOnSpecificGroup(String diplomaName, String groupName) {
        try {
            List<WebElement> groupSections = Hooks.getDriver().findElements(By.xpath("//div[contains(@class, 'groupCard')]"));

            for (WebElement section : groupSections) {
                // Extract diploma & group names within the section
                String foundDiploma = getElementText(section, ".//h5[contains(text(), 'Diploma')]");
                String foundGroup = getElementText(section, ".//h5[contains(text(), 'Group')]");

                if (foundDiploma.equalsIgnoreCase(diplomaName) && foundGroup.equalsIgnoreCase(groupName)) {
                    String currentURL = Hooks.getDriver().getCurrentUrl();
                    section.click();
                    WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
                    System.out.println("✅ Clicked on group: " + groupName + " for diploma: " + diplomaName);
                    return;
                }
            }
            System.out.println("⚠️ No matching group found for diploma: " + diplomaName + " and group: " + groupName);
        } catch (NoSuchElementException e) {
            System.err.println("❌ Error: Unable to find the specified group section. " + e.getMessage());
        }
    }

    /**
     * Extracts all diploma names from the student profile.
     *
     * @return A list of diploma names.
     */
    public List<String> getAllDiplomaNames() {
        return extractElementsText("//h5[contains(text(), 'Diploma')]", "Diploma : ");
    }

    /**
     * Extracts all group names from the student profile.
     *
     * @return A list of group names.
     */
    public List<String> getAllGroupNames() {
        return extractElementsText("//h5[contains(text(), 'Group')]", "Group : ");
    }

    /**
     * Extracts all group days associated with each group.
     *
     * @return A list of group days.
     */
    public List<String> getAllGroupDays() {
        List<WebElement> groupSections = Hooks.getDriver().findElements(By.xpath("//h5[contains(text(), 'Group')]/parent::div"));
        List<String> allGroupDays = new ArrayList<>();

        for (WebElement section : groupSections) {
            List<WebElement> dayElements = section.findElements(By.xpath(".//h5[contains(text(), 'Days')]/span"));
            allGroupDays.add(getConcatenatedText(dayElements));
        }
        return allGroupDays;
    }

    /**
     * Logs out the current user.
     */
    public void logout() {
        try {
            String currentURL = Hooks.getDriver().getCurrentUrl();
            Hooks.getDriver().findElement(By.xpath("//button[.//i[@class='pi pi-user']]")).click();
            WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Logout')]"))).click();
            WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
            System.out.println("✅ Successfully logged out.");
        } catch (Exception e) {
            System.err.println("❌ Error during logout: " + e.getMessage());
        }
    }

    // ---------------------------
    // 🔹 Helper Methods
    // ---------------------------

    /**
     * Extracts text from multiple elements and returns a list.
     *
     * @param xpath         XPath of the elements.
     * @param textToRemove  Text prefix to remove from extracted text.
     * @return A list of cleaned text values.
     */
    private List<String> extractElementsText(String xpath, String textToRemove) {
        List<WebElement> elements = Hooks.getDriver().findElements(By.xpath(xpath));
        List<String> extractedText = new ArrayList<>();
        for (WebElement element : elements) {
            extractedText.add(element.getText().replace(textToRemove, "").trim());
        }
        return extractedText;
    }

    /**
     * Extracts text from an element within a parent element.
     *
     * @param parentElement The parent WebElement.
     * @param xpath         XPath relative to the parent element.
     * @return Extracted text or an empty string if not found.
     */
    private String getElementText(WebElement parentElement, String xpath) {
        try {
            return parentElement.findElement(By.xpath(xpath)).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    /**
     * Concatenates text from a list of elements, separated by commas.
     *
     * @param elements List of WebElements.
     * @return Concatenated string.
     */
    private String getConcatenatedText(List<WebElement> elements) {
        StringBuilder text = new StringBuilder();
        for (WebElement element : elements) {
            text.append(element.getText()).append(", ");
        }
        return text.length() > 2 ? text.substring(0, text.length() - 2) : "";
    }
}
