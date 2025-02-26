package org.example.Pages.GroupsPage;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class GroupsParentPage {
    protected String projPath = System.getProperty("user.dir");
    protected  String imgPath = projPath+ File.separator+"Images";
    protected  String pdfPath = projPath+ File.separator+"PDF";
    protected WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10));
    protected WebDriverWait shortWait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(2));
    protected Actions actions = new Actions(Hooks.getDriver());



    public WebElement addGroupButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(Hooks.getDriver().findElement(By.xpath("//span[text()='New Group']"))));
    }
    protected void selectDropdownByVisibleText(By elemLocator, By dropLocator, String value) {
        try {
            // Wait and click the dropdown to open the list
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(elemLocator));
            actions.moveToElement(dropdown).click().perform();

            // Wait until the dropdown list is visible
            WebElement dropdownList = wait.until(ExpectedConditions.visibilityOfElementLocated(dropLocator));

            // Find all options in the dropdown
            List<WebElement> options = dropdownList.findElements(By.tagName("li"));
            for (WebElement option : options) {
                // Check if the option text matches the value
                if (option.getText().contains(value)) {
                    actions.moveToElement(option).click().perform();
                    break;

                }
            }


        } catch (Exception e) {
            throw new RuntimeException("Failed to select dropdown value: " + value, e);
        }
    }
    protected void setInputField(By locator, String value) {
        WebElement inputField = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
        inputField.sendKeys(value);
    }
}
