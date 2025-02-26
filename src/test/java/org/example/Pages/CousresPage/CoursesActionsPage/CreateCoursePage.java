package org.example.Pages.CousresPage.CoursesActionsPage;

import org.example.Pages.CousresPage.CourseParentPage;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * This class provides actions to create a course in the application.
 * It extends CourseParentPage and interacts with various course-related elements.
 */
public class CreateCoursePage extends CourseParentPage {
    private final WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(15));
    private final JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();

    // 🔍 Locators for UI elements
    private final By courseNameField = By.id("job");
    private final By descriptionField = By.xpath("//textarea[contains(@placeholder, 'Write the Course Description…')]");
    private final String departmentCheckbox = "//div[@class='grid']//label[text()='%s']/preceding-sibling::div[@class='p-checkbox p-component']";
    private final By termNameField = By.xpath("//input[@placeholder='Ex: Algorithms, Embedded C, ….']");
    private final By termHoursField = By.id("ho");
    private final By addSubTermButton = By.xpath("//span[contains(text(),'Add More')]");
    private final By subTermNameFields = By.xpath("//input[@placeholder='Ex: Loops, Do While, ….']");
    private final By subTermDescFields = By.id("assigniment");
    private final String feedbackRadioButton = "//b[text()='%s']/following-sibling::div[contains(@class,'p-radiobutton')][1]";
    private final By removeSubTermButton = By.xpath("//span[contains(text(),'x')]");
    private final By saveButton = By.xpath("//span[contains(text(),'Save')]");
    private final By cancelButton = By.xpath("//button[text()='Cancel']");

    /**
     * Enters the name of the course.
     *
     * @param courseName The name of the course.
     */
    public void setNameOfCourse(String courseName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(courseNameField))
                .sendKeys(courseName);
    }

    /**
     * Enters the course description.
     *
     * @param description The description of the course.
     */
    public void setDescriptionOfCourse(String description) {
        Hooks.getDriver().findElement(descriptionField).sendKeys(description);
    }

    /**
     * Selects the department for the course.
     *
     * @param department The department name.
     */
    public void setDepartmentOfCourse(String department) {
        String formattedLocator = String.format(departmentCheckbox, department);
        WebElement checkbox = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(formattedLocator)));
        checkbox.click();
    }


    /**
     * Enters the name of the term.
     *
     * @param term The name of the term.
     */
    public void setNameOfTerm(String term) {
        Hooks.getDriver().findElement(termNameField).sendKeys(term);
    }

    /**
     * Sets the number of hours for the term.
     *
     * @param termHours The number of hours for the term.
     */
    public void setTermHours(String termHours) {
        Hooks.getDriver().findElement(termHoursField).sendKeys(termHours);
    }

    /**
     * Clicks the button to add another sub-term.
     */
    public void addAnotherSubTerm() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addSubTermButton));
        js.executeScript("arguments[0].click();", button);
    }

    /**
     * Sets the name of a specific sub-term.
     *
     * @param index       The index of the sub-term in the list.
     * @param subTermName The name of the sub-term.
     */
    public void setSubTermName(int index, String subTermName) {
        List<WebElement> fields = Hooks.getDriver().findElements(subTermNameFields);
        if (index < fields.size()) {
            fields.get(index).sendKeys(subTermName);
        }
    }

    /**
     * Sets the description of a specific sub-term.
     *
     * @param index       The index of the sub-term in the list.
     * @param subTermDesc The description of the sub-term.
     */
    public void setSubTermDesc(int index, String subTermDesc) {
        List<WebElement> fields = Hooks.getDriver().findElements(subTermDescFields);
        if (index < fields.size()) {
            fields.get(index).sendKeys(subTermDesc);
        }
    }

    /**
     * Selects a feedback option (Yes/No) for a sub-term.
     *
     * @param index    The index of the sub-term.
     * @param yesOrNo The feedback choice ("Yes" or "No").
     */
    public void setSubTermFeedback(int index, String yesOrNo) {
        String formattedLocator = String.format(feedbackRadioButton, yesOrNo);
        List<WebElement> options = Hooks.getDriver().findElements(By.xpath(formattedLocator));
        if (index < options.size()) {
            options.get(index).click();
        }
    }

    /**
     * Removes the last added sub-term.
     */
    public void clickRemoveSubTerm() {
        Hooks.getDriver().findElement(removeSubTermButton).click();
    }

    /**
     * Clicks the "Save" button to save the course.
     */
    public void clickSave() {
        // Ensure the page is fully loaded before attempting to save
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        Hooks.getDriver().findElement(saveButton).click();
        wait.until(ExpectedConditions.urlToBe(COURSE_PAGE_URL));
    }

    /**
     * Clicks the "Cancel" button to discard changes.
     */
    public void clickCancel() {
        Hooks.getDriver().findElement(cancelButton).click();
    }
}
