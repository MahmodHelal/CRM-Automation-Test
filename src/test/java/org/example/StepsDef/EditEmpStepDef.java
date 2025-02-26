/*
package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.DocsPage;
import org.example.Pages.Employees.EmployeesActions.EditEmployee.EditPersonalInfoPage;
import org.example.Pages.Employees.EmployeesActions.EditEmployee.EditItDevicesPage;
import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;




public class EditEmpStepDef {
    EditPersonalInfoPage editInfoPage = new EditPersonalInfoPage();
    EditItDevicesPage editItDevices = new EditItDevicesPage();
    DocsPage editDocsPage = new DocsPage();
    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(15));
    WebDriverWait shortWait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));


    @And("searches for {string} using the search bar")
    public void searchesForPhoneUsingTheSearchBar(String phone) throws InterruptedException {
        EmployeeParent.searchFor(phone);
        Thread.sleep(2000);
    }

    @And("clicks the Edit button")
    public void clicksTheEditButton() {
        EmployeeParent.editEmployeeButton().click();
    }

    @Then("the Edit Employee Page will open")
    public void theEditEmployeePageWillOpen() {
        
    }

    @And("edits personal information if empty")
    public void editsPersonalInformationIfEmpty(DataTable personalInfoTable) {

        wait.until(ExpectedConditions.textToBe(editInfoPage.HEADER_OF_SECTION, "Employee Information"));
        // Convert the DataTable to a Map
        Map<String, String> personalInfo = personalInfoTable.asMap(String.class, String.class);

        // Fill the employee information dynamically
        for (Map.Entry<String, String> entry : personalInfo.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            switch (key.toLowerCase()) {
                case "name":
                    editInfoPage.setEmpName(value);
                    break;
                case "phone":
                    editInfoPage.setMobileNumber(value);
                    break;
                case "address":
                    editInfoPage.setAddress(value);
                    break;
                case "personal email":
                    editInfoPage.setPersonalEmail(value);
                    break;
                case "work email":
                    editInfoPage.setWorkEmail(value);
                    break;
                case "profile picture":
                    editInfoPage.uploadPersonalPic(value);
                    break;
                case "linkedin url":
                    editInfoPage.setLinkedinURL(value);
                    break;
                case "cv file name":
                    editInfoPage.uploadCvPdf(value);
                    break;
                case "id file name":
                    editInfoPage.uploadID(value);
                    break;
                case "birth file name":
                    editInfoPage.uploadBirth(value);
                    break;
                default:
                    System.out.println("Unknown key: " + key);
            }
        }
        editInfoPage.nextButton();
    }

    @And("edits job information if empty")
    public void editsJobInformationIfEmpty() {




    }

    @And("edits IT data if empty {string}")
    public void editsITDataIfEmpty(String imgFileName,DataTable itDevicesTable) {

        wait.until(ExpectedConditions.textToBe(editItDevices.HEADER_OF_SECTION, "IT Details"));
        // Convert the DataTable to a map for easier key-value handling
        Map<String, String> itDevices = itDevicesTable.asMap(String.class, String.class);

        for (Map.Entry<String, String> entry : itDevices.entrySet()) {
            // Extract the key and value from the DataTable entry
            String key = entry.getKey().toLowerCase(); // Convert key to lowercase for case-insensitive matching
            String value = entry.getValue();

            switch (key) {
                case "finger print":
                    // Call the method to set the fingerprint code
                    editItDevices.setFingerCode(value);
                    break;

                case "it devices":
                    // Split the comma-separated devices and handle each device individually
                    String[] devices = value.split(",");
                    for (String device : devices) {
                        editItDevices.setItDevices(device.trim()); // Trim whitespace for clean input
                    }
                    editItDevices.fillDeviceDetail(imgFileName);
                    break;

                default:
                    // Log a message for unsupported or unrecognized keys
                    System.out.println("Unrecognized IT Data key: " + key);
                    break;
            }
        }
        editItDevices.nextButton();
    }


    @And("edits documents if empty")
    public void editsDocumentsIfEmpty(DataTable DocumentsTable) {
        wait.until(ExpectedConditions.textToBe(editDocsPage.HEADER_OF_SECTION, "Documents"));

        Map<String, String> Docs = DocumentsTable.asMap(String.class, String.class);
        String gender = Docs.getOrDefault("Gender", "").trim();

        for (Map.Entry<String, String> entry : Docs.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue();

            try {
                switch (key) {
                    case "gender":
                        editDocsPage.setGender(value);
                        break;
                    case "military service status file":
                        if ("Male".equalsIgnoreCase(gender)) {
                            editDocsPage.uploadMilitaryServiceStatusPdf();                        }
                        break;
                    case "criminal record file":
                        editDocsPage.uploadCriminalRecordPdf();
                        break;
                    // Add other cases...
                    case "optional documents":
                        processOptionalDocuments(value);
                        break;
                    default:
                        System.out.println("Unrecognized key: {}" + key);
                }
            } catch (Exception e) {
                System.out.println("Error processing key "+ key +": "+ e.getMessage() );
            }
        }

        editDocsPage.submitButton();
    }

    private void processOptionalDocuments(String value) {
        if (value.trim().isEmpty()) {
            System.out.println("Optional documents value is empty.");
            return;
        }
        String[] docPartsArray = value.split(";");
        if (docPartsArray.length == 2) {
            String[] docNames = docPartsArray[0].split(",");
            String[] docFileNames = docPartsArray[1].split(",");
            if (docNames.length == docFileNames.length) {
                for (int i = 0; i < docNames.length; i++) {
                    editDocsPage.addOptionalDocument(docNames[i].trim(), docFileNames[i].trim(), i);
                }
            } else {
                System.out.println("Mismatch between document names and file names. Names: " + docNames.length + ", Files: " + docFileNames.length);
            }
        } else {
            System.out.println("Invalid optional document format.");
        }
    }


    @And("submits the updated profile")
    public void submitsTheUpdatedProfile() {
        
    }


    @Then("verifies the changes on the Requests Page")
    public void verifiesTheChangesOnTheRequestsPage() {
    }


}
*/
