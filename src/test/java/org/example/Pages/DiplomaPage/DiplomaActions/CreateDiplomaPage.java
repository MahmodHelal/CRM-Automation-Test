package org.example.Pages.DiplomaPage.DiplomaActions;

import org.example.Pages.DiplomaPage.DiplomaParentPage;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.List;
import java.util.Random;

public class CreateDiplomaPage extends DiplomaParentPage {

    public void setNameOfDiploma(String nameOfDiploma) {
        WAIT.until(ExpectedConditions.elementToBeClickable(Hooks.getDriver().findElement(By.id("name"))))
                .sendKeys(nameOfDiploma);
    }

    public void setFeesLE(String fees) {
        Hooks.getDriver().findElement(By.xpath("//label[contains(text(),'Fees LE')]/following-sibling::div//input[@id='fees']"))
                .sendKeys(fees);
    }

    public void setFeesUSD(String fees) {
        Hooks.getDriver().findElement(By.xpath("//label[contains(text(),'Fees USD')]/following-sibling::div//input[@id='fees']"))
                .sendKeys(fees);
    }

    public void hasKit(String trueOrFalse) {
        String value = formatBoolean(trueOrFalse);
        selectDropdownByVisibleText(
                By.xpath("//label[text()='Has Kit']/following-sibling::div//div[contains(@class, 'p-dropdown')]"), value);
    }

    private String formatBoolean(String value) {
        return value.equalsIgnoreCase("true") ? "True" : "False";
    }

    public void setTypeOfDiploma(String type) {
        Hooks.getDriver().findElement(By.xpath("//label[text()='" + type + "']/preceding-sibling::div[@id='diplomaid']")).click();
    }

    public void hasWelcomeMail(String trueOrFalse) {
        String value = formatBoolean(trueOrFalse);
        selectDropdownByVisibleText(
                By.xpath("//label[text()='Welcome Mail']/following-sibling::div//div[contains(@class, 'p-dropdown')]"), value);
    }

    public WebElement addDepartmentButton() {
        return WAIT.until(ExpectedConditions.elementToBeClickable(
                Hooks.getDriver().findElement(By.xpath("//label[text()='Department']/following-sibling::div//button[@aria-label='Add']"))));
    }

    public WebElement addCourseButton() {
        return WAIT.until(ExpectedConditions.elementToBeClickable(
                Hooks.getDriver().findElement(By.xpath("//label[text()='Select Courses']/following-sibling::div//button[@aria-label='Add']"))));
    }

    public void setDepartments(String department) {
        SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(
                        Hooks.getDriver().findElement(By.xpath("//label[text()='" + department + "']/preceding-sibling::div[@id='diplomaid']"))))
                .click();
    }

    public void setCourses(String course) {
        SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(
                        Hooks.getDriver().findElement(By.xpath("//label[contains(text(),'" + course + "')]/preceding-sibling::div[@id='diplomaid']"))))
                .click();
    }

    public void clickYesToClosePopup() {
        WebElement yesButton = SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(
                Hooks.getDriver().findElement(By.xpath("//span[text()='Yes']"))));
        yesButton.click();
    }

    private String generateRandom(int length) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphabet.length());
            builder.append(alphabet.charAt(randomIndex));
        }
        return builder.toString();
    }

    public void setSlug() {
        String text = generateRandom(4);
        Hooks.getDriver().findElement(By.id("Slug")).sendKeys(text);
    }

    public void setShortcutName() {
        String text = generateRandom(4);
        Hooks.getDriver().findElement(By.id("ShourName")).sendKeys(text);
    }

    public void publishOnWebsite() {
        Hooks.getDriver().findElement(By.xpath("//label[text()='Publish on the website']/following-sibling::div//div[@id='diplomaid']"))
                .click();
    }

    public void publishOnInnerWebsite() {
        Hooks.getDriver().findElement(By.xpath("//label[text()='Publish on the website inner page']/following-sibling::div//div[@id='diplomaid']"))
                .click();
    }

    public void addRequirement(String[] requirements) {
        WebElement requirementText = Hooks.getDriver().findElement(By.id("requirments"));
        WebElement addNewReqButton = Hooks.getDriver().findElement(By.xpath("//input[@id='requirments']/following-sibling::i[@title='Add Requirment']"));

        requirementText.sendKeys(requirements[0]);
        for (int i = 1; i < requirements.length; i++) {
            addNewReqButton.click();
            List<WebElement> allRequirementInputs = Hooks.getDriver().findElements(By.id("requirments"));
            allRequirementInputs.get(allRequirementInputs.size() - 1).sendKeys(requirements[i]);
        }
    }

    public void addLearns(String[] learns) {
        WebElement learnText = Hooks.getDriver().findElement(By.id("WhatWillYouLearn"));
        WebElement addNewLearnButton = Hooks.getDriver().findElement(By.xpath("//input[@id='WhatWillYouLearn']/following-sibling::i[@title='Add Subject']"));

        learnText.sendKeys(learns[0]);
        for (int i = 1; i < learns.length; i++) {
            addNewLearnButton.click();
            List<WebElement> allLearnInputs = Hooks.getDriver().findElements(By.id("WhatWillYouLearn"));
            allLearnInputs.get(allLearnInputs.size() - 1).sendKeys(learns[i]);
        }
    }

    public void setVideoUrl(String url) {
        Hooks.getDriver().findElement(By.id("VideoURL")).sendKeys(url);
    }

    public void uploadDiplomaImg(String imgName) {
        Hooks.getDriver().findElement(By.xpath("//label[contains(text(),'Diploma Image')]/following::input[@type='file' and @name='diplomaImgs']"))
                .sendKeys(imgPath + File.separator + imgName);
    }

    private void waitUntilAllImagesUpload() {
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Diploma Image']/following::span[text()='Upload complete']")));
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Diploma Inner Image']/following::span[text()='Upload complete']")));
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Master Icon']/following::span[text()='Upload complete']")));
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Slave Icon']/following::span[text()='Upload complete']")));
    }

    public void uploadDiplomaInnerImg(String imgnName) {
        Hooks.getDriver().findElement(By.xpath("//label[text()='Diploma Inner Image']/following::input[@type='file' and @name='diplomaImgs']"))
                .sendKeys(imgPath + File.separator + imgnName);
    }

    public void uploadMasterIconImg(String imgnName) {
        Hooks.getDriver().findElement(By.xpath("//label[text()='Master Icon']/following::input[@type='file' and @name='diplomaImgs']"))
                .sendKeys(imgPath + File.separator + imgnName);
    }

    public void uploadSlaveIconImg(String imgnName) {
        Hooks.getDriver().findElement(By.xpath("//label[text()='Slave Icon']/following::input[@type='file' and @name='diplomaImgs']"))
                .sendKeys(imgPath + File.separator + imgnName);
    }

    public void setHeadTitle(String title) {
        Hooks.getDriver().findElement(By.id("HeadTitle")).sendKeys(title);
    }

    public void setMetaTitle(String title) {
        Hooks.getDriver().findElement(By.id("metaTitle")).sendKeys(title);
    }

    public void setMetaDescriptionTitle(String title) {
        Hooks.getDriver().findElement(By.id("metaDes")).sendKeys(title);
    }

    public void setMetaKeywordsTitle(String title) {
        Hooks.getDriver().findElement(By.xpath("//div[@id='metaKywords']//input")).sendKeys(title);
        ACTIONS.sendKeys(Keys.ENTER).perform();
    }

    public void setShortDescription(String shortDescription) {
        Hooks.getDriver().findElement(By.id("ShourDes")).sendKeys(shortDescription);
    }

    public void setDescription(String content) {
        WebElement editorContainer = Hooks.getDriver().findElement(By.className("p-editor-container"));
        WebElement editableArea = editorContainer.findElement(By.className("ql-editor"));
        editableArea.click();
        editableArea.sendKeys(content);
    }

    public void saveButton() {
        waitUntilAllImagesUpload();
        WebElement saveButton = WAIT.until(ExpectedConditions.elementToBeClickable(
                Hooks.getDriver().findElement(By.xpath("//span[contains(text(),'Save')]"))));
        saveButton.click();
        String currentURL = Hooks.getDriver().getCurrentUrl();
        WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }
}
