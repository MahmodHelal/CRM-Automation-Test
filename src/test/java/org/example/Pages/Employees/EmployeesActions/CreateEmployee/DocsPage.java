package org.example.Pages.Employees.EmployeesActions.CreateEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.stream.IntStream;

/**
 * Page class for handling document uploads and gender selection.
 */
public class DocsPage extends EmployeeParent {

    // ✅ Using Enum for Gender Selection
    public enum Gender {
        MALE("male"),
        FEMALE("female");

        private final String value;

        Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String getId() {
            return "gender" + value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
        }
    }

    // ✅ Section Header
    public WebElement getSectionHeader() {
        return WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Documents')]")));
    }

    // ---------------------------
    // ✅ Gender Selection Methods
    // ---------------------------

    public void setGender(Gender gender) {
        WebElement genderElement = WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.id(gender.getId())));
        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", genderElement);
        clickElementWithRetry(genderElement);
    }

    private void clickElementWithRetry(WebElement element) {
        try {
            WAIT.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            JS.executeScript("arguments[0].click();", element);
        }
    }



    // ---------------------------
    // ✅ Document Upload Methods
    // ---------------------------

    private void uploadDocument(String elementId, String fileName) {
        WebElement fileInput = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        fileInput.sendKeys(PDF_PATH + File.separator + fileName);
    }

    // Standard Document Uploads
    public void uploadMilitaryServiceStatusPdf() {
        uploadDocument("military_service_certificate", "file-sample_150kB.pdf");
    }

    public void uploadCriminalRecordPdf() {
        uploadDocument("criminal_record_certificate", "file-sample_150kB.pdf");
    }

    public void uploadBankStatementPdf() {
        uploadDocument("bank_account_confirmation", "file-sample_150kB.pdf");
    }

    public void uploadFormSixInsurancePdf() {
        uploadDocument("social_insurance_form_6", "file-sample_150kB.pdf");
    }

    public void uploadForm111InsurancePdf() {
        uploadDocument("social_insurance_form_111", "file-sample_150kB.pdf");
    }

    public void uploadInsurancePrintPdf() {
        uploadDocument("social_insurance_history_printout", "file-sample_150kB.pdf");
    }

    public void uploadEmploymentOfficeCertificatePdf() {
        uploadDocument("employment_office_certificate", "file-sample_150kB.pdf");
    }

    // ---------------------------
    // ✅ Optional Document Handling
    // ---------------------------

    public void handleOptionalDocuments(String value) {
        // Expected format: "docName1,docName2;fileName1,fileName2"
        String[] docParts = value.split(";");
        if (docParts.length != 2) {
            System.err.println("❌ Invalid optional document format: " + value);
            return;
        }

        String[] docNames = docParts[0].split(",");
        String[] docFiles = docParts[1].split(",");

        if (docNames.length != docFiles.length) {
            System.err.println("❌ Mismatch between document names and file names.");
            return;
        }

        IntStream.range(0, docNames.length)
                .forEach(i -> addOptionalDocument(docNames[i].trim(), docFiles[i].trim(), i));
    }

    private void addOptionalDocument(String docName, String docFileName, int index) {
        WebElement addDocButton = WAIT.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[contains(text(), 'Add Optional Document')]]")));
        addDocButton.click();

        WebElement docNameInput = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.id("optional_document_name_" + index)));
        docNameInput.sendKeys(docName);

        WebElement fileInput = WAIT.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@id='optional_document_file_" + index + "']//input[@type='file']")));
        JS.executeScript("arguments[0].style.display='block';", fileInput);

        fileInput.sendKeys(PDF_PATH + File.separator + docFileName);
    }

    // ---------------------------
    // ✅ Assertion Methods
    // ---------------------------

    public boolean isDocumentUploaded(String elementId) {
        try {
            WebElement fileInput = driver.findElement(By.id(elementId));
            return !fileInput.getAttribute("value").isEmpty();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isGenderSelected(Gender gender) {
        try {
            WebElement selectedGender = driver.findElement(By.xpath("//label[contains(text(),'" + gender.getValue() + "')]/preceding-sibling::input[@checked]"));
            return selectedGender.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isOptionalDocumentUploaded(int index) {
        try {
            WebElement fileInput = driver.findElement(By.id("optional_document_file_" + index));
            return !fileInput.getAttribute("value").isEmpty();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
