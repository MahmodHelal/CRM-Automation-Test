package org.example.Pages.GroupsPage.GroupsActions;

import org.example.Pages.GroupsPage.GroupsParentPage;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

public class CreateGroupPage extends GroupsParentPage {
    protected JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();


    public void setGroupType(String type){
        selectDropdownByVisibleText(By.xpath("//label[text()='Group Type']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),type);
    }
    public void setDiploma(String diploma){
        selectDropdownByVisibleText(By.xpath("//label[text()='Select Diploma']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),diploma);
    }

    public void setStartingDate(String date){
//        setInputField(By.id("date"), date);
        Hooks.getDriver().findElement(By.id("date")).sendKeys(date);
    }
    public void setTrainingDays(String day) throws InterruptedException {
//        Thread.sleep(1000);
        Hooks.getDriver().findElement(By.xpath("//label[text()='"+day+"']/preceding-sibling::div[contains(@class, 'p-checkbox')]")).click();
    }
    public void setTrainingHours(String startHour,String amOrPm,String period){
        // Locate and click the time picker input field to open the dropdown
        WebElement timePickerInput = shortWait.until(ExpectedConditions.elementToBeClickable(Hooks.getDriver().findElement(By.cssSelector(".vue__time-picker-input"))));
        timePickerInput.click();

        // Select the hour (e.g., "04")
        WebElement hourOption = wait.until(ExpectedConditions.elementToBeClickable(Hooks.getDriver().findElement(By.xpath("//ul[@class='hours']/li[@data-key='"+startHour+"']"))));
        hourOption.click();

        // Select AM/PM (e.g., "PM")
        WebElement amPmOption = wait.until(ExpectedConditions.elementToBeClickable(Hooks.getDriver().findElement(By.xpath("//ul[@class='apms']/li[@data-key='"+amOrPm+"']"))));
        amPmOption.click();

        WebElement body = Hooks.getDriver().findElement(By.tagName("body"));
        body.click();


   /*     WebElement sessionPeriod = wait.until(ExpectedConditions.elementToBeClickable(Hooks.driver.findElement(By.id("name"))));
        sessionPeriod.sendKeys(period);*/
        setInputField(By.id("name"),period);


    }

    public void isHybrid(String yesOrNo){
        selectDropdownByVisibleText(By.xpath("//label[text()='Hybrid']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),yesOrNo);
    }

    public void isOnline(String yesOrNo){
        selectDropdownByVisibleText(By.xpath("//label[text()='Online']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),yesOrNo);
    }

    public void isViral(String yesOrNo){
        selectDropdownByVisibleText(By.xpath("//label[text()='Group viral']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),yesOrNo);
    }

    public void showOnWebsite(String yesOrNo){
        selectDropdownByVisibleText(By.xpath("//label[text()=' Show on website']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),yesOrNo);
    }

    public void selectBranch(String branch){
        selectDropdownByVisibleText(By.xpath("//label[text()='Select Branch']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),branch);
    }

    public void selectLab(String lab){
        selectDropdownByVisibleText(By.xpath("//label[text()='Select Lab']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),lab);
    }

    public void setFees(String fees){
        setInputField(By.id("phone"),fees);
    }

    public void saveButton(){
        wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
        String currentUrl =Hooks.getDriver().getCurrentUrl();
        Hooks.getDriver().findElement(By.xpath("//span[contains(text(),'Save')]")).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentUrl))); // Wait until the URL changes

    }
}
