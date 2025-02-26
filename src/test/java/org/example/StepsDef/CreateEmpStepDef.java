package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Helpers.HelperMethods;
import org.example.Helpers.SignHelper;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.EmpInformationPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.ITDetailsPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.JobInfoPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.DocsPage;
import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.Pages.HomePage;
import org.example.Pages.LoginPage;
import org.example.Pages.Requests.RequestsPage;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.testng.AssertJUnit.assertEquals;

public class CreateEmpStepDef {

    // Page Object Instances
    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();
    private final EmpInformationPage empInformationPage = new EmpInformationPage();
    private final JobInfoPage jobInfoPage = new JobInfoPage();
    private final ITDetailsPage itDetailsPage = new ITDetailsPage();
    private final DocsPage uploadDocumentsPage = new DocsPage();
    private final RequestsPage requestsPage = new RequestsPage();
    private final SignHelper signHelper = new SignHelper();
    // WebDriverWait using Hooks.getDriver()
    private final WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(50));

    // Test Data (For Future Use)
    private  String EMPLOYEE_NAME = null;

    @Given("user enters username {string} and password {string}")
    public void userEntersUserNameAndPassword(String userName, String password) {
        loginPage.enterUsername(userName);
        loginPage.enterPassword(password);
        loginPage.submitLogin();
    }

    @When("user opens the Employees Page")
    public void userOpensTheEmployeesPage() {
        homePage.getEmployeesPage();
    }

    @And("clicks on the Create Employee button")
    public void clickOnCreateEmployeeButton() {
        empInformationPage.clickCreateEmployeeButton();
    }

    @Then("the Create Employee Page will open")
    public void theCreateEmployeePageWillOpen() {
        // Future Optimization: Use Hooks or Helper methods to verify page navigation
        wait.until(ExpectedConditions.urlContains("create-employee"));
    }

    @And("fills the personal information")
    public void fillsThePersonalInformation() {
        // Wait for Employee Information Section to load
//        wait.until(ExpectedConditions.textToBePresentInElement(empInformationPage.getSectionHeader(), "Employee Information"));

        empInformationPage.fillEmployeeInfo(EMPLOYEE_NAME);

        // Retrieve employee info
        Map<String, String> employeeInfo = empInformationPage.getEmployeeInfo();

        // Assign the employee name to EMPLOYEE_NAME
        EMPLOYEE_NAME = employeeInfo.get("name");

        if (EMPLOYEE_NAME == null || EMPLOYEE_NAME.isBlank()) {
            throw new RuntimeException("❌ EMPLOYEE_NAME is null or empty. Ensure the employee name is correctly captured.");
        }

        System.out.println("✔ EMPLOYEE_NAME captured: " + EMPLOYEE_NAME);
        // Click Next Button
        empInformationPage.nextButton();
        System.out.println("Completed filling personal information.");
    }


    @And("fills the job information")
    public void fillsTheJobInformation(DataTable jobInfoTable) {
        // Wait until 'Employee Information' section is present and contains the text
//        wait.until(ExpectedConditions.textToBePresentInElement(jobInfoPage.getSectionHeader(), "Job Information"));

        Map<String, String> jobInfo = extractDataFromTable(jobInfoTable);

        jobInfo.forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "hiring date" -> jobInfoPage.setHiringDate(value);
                case "contract end date" -> {
                    jobInfoPage.setContractEndDate(value);
                    jobInfoPage.setWeekendDaysRandomly();
                }
                case "job location" -> jobInfoPage.setJobLocation(value);
                case "approval levels" -> jobInfoPage.setApprovalLevels(value);
                case "department" -> jobInfoPage.setDepartment(value);
                case "job role" -> {
                    jobInfoPage.setJobRole(value);
                    jobInfoPage.setJobTitle(value);
                }
                case "1st level manager" -> jobInfoPage.setR1(value);
                case "2nd level manager" -> {
                    jobInfoPage.setR2(value);
                    jobInfoPage.setNetSalary();
                }
                case "salary calculation" -> jobInfoPage.setCalcSalary(value);
                case "medical card status" -> jobInfoPage.setMedicalCardStatus(value);
                case "social insurance status" -> jobInfoPage.setSocialInsuranceStatus(value);
                case "payroll card status" -> {
                    jobInfoPage.setPayrollCardStatus(value);
                    jobInfoPage.uploadGradCertificate();
                    jobInfoPage.uploadExpCertificate();
                }
                default -> System.out.println("⚠ Warning: Unrecognized job information key: " + key);
            }
        });
        // Retrieve employee info
        jobInfoPage.getJobInfoData();

        jobInfoPage.nextButton();
    }

    @And("Fill IT Data")
    public void fillITData(DataTable itDevicesTable) {

        // Wait until the IT Details section is visible
//        wait.until(ExpectedConditions.textToBePresentInElement(itDetailsPage.getSectionHeader(), "IT Details"));

        // Extract DataTable into a key-value map for easy handling
        Map<String, String> itDevices = extractDataFromTable(itDevicesTable);

        itDetailsPage.setFingerCode();

        // Iterate over the DataTable entries and handle the corresponding action using a switch-case
        itDevices.forEach((key, value) -> {
            try {
                switch (key.toLowerCase()) {
                    case "it devices":
                        itDetailsPage.processItDevices(value);
                        break;
                    default:
                        System.err.println("Warning: Unrecognized IT Data key: " + key);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error processing IT data for key: " + key);
                e.printStackTrace();
            }
        });

        // Proceed to the next step
        itDetailsPage.nextButton();
        System.out.println("Completed filling IT information.");
    }

    @And("Fill Documents Data And Submit")
    public void fillDocumentsDataAndSubmit(DataTable DocumentsTable) {
//        wait.until(ExpectedConditions.textToBePresentInElement(uploadDocumentsPage.getSectionHeader(), "Documents"));

        Map<String, String> docs = extractDataFromTable(DocumentsTable);

        // Execute actions based on the provided keys using switch-case
        for (Map.Entry<String, String> entry : docs.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue();

            switch (key) {
                case "gender":
                    uploadDocumentsPage.setGender(DocsPage.Gender.valueOf(value.toUpperCase()));
                    String gender = docs.get("Gender");
                    if ("Male".equalsIgnoreCase(gender)) {
                        uploadDocumentsPage.uploadMilitaryServiceStatusPdf();
                    }
                    uploadDocumentsPage.uploadCriminalRecordPdf();
                    uploadDocumentsPage.uploadBankStatementPdf();
                    uploadDocumentsPage.uploadFormSixInsurancePdf();
                    uploadDocumentsPage.uploadForm111InsurancePdf();
                    uploadDocumentsPage.uploadInsurancePrintPdf();
                    uploadDocumentsPage.uploadEmploymentOfficeCertificatePdf();
                    break;
                case "optional documents":
                    uploadDocumentsPage.handleOptionalDocuments(value);
                    break;
                default:
                    System.out.println("Unrecognized document key: " + key);
                    break;
            }
        }

        uploadDocumentsPage.submitButton();
    }

    @Then("Open Requests Page To Check Data")
    public void openRequestsPageToCheckData(DataTable empTable) {
        // Navigate to the Requests Report page
        homePage.getRequestsReportPage();

        // Search for the employee using the stored name
        requestsPage.searchForEmployee(EMPLOYEE_NAME);

        // Convert DataTable to a Map for easy access
        Map<String, String> empInfo = extractDataFromTable(empTable);

        // ✅ Fetch the row ONCE instead of multiple times
        WebElement row = requestsPage.getRowBySearch("Employee Name", EMPLOYEE_NAME);

        // ✅ Extract all required values in one go to reduce re-fetching
        Map<String, String> actualData = new HashMap<>();
        actualData.put("department", requestsPage.getCellDataFromRow(row, "Department"));
        actualData.put("job role", requestsPage.getCellDataFromRow(row, "Job Role"));
        actualData.put("type", requestsPage.getCellDataFromRow(row, "Type"));
        actualData.put("effective date", requestsPage.getCellDataFromRow(row, "Effective Date"));

        // ✅ Iterate once and assert all values
        for (Map.Entry<String, String> entry : empInfo.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String expectedValue = entry.getValue();
            String actualValue = actualData.get(key); // ✅ Retrieve from pre-fetched data

            if (actualValue == null) {
                System.out.println("❌ Unrecognized key: " + key);
                continue;
            }

            // ✅ Handle date formatting separately
            if (key.equals("effective date")) {
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate date = LocalDate.parse(actualValue, inputFormatter);
                    actualValue = date.format(outputFormatter);
                } catch (DateTimeParseException e) {
                    throw new RuntimeException("❌ Invalid date format received: " + actualValue, e);
                }
            }

            // ✅ Print and Assert
            System.out.println("✔ Checking " + key + " | Expected: " + expectedValue + " | Actual: " + actualValue);
            Assert.assertEquals(actualValue, expectedValue, "❌ Mismatch found for " + key);
        }
    }

    @And("take a specific action on the request with status {string}")
    public void takeASpecificActionOnTheRequestWithStatus(String expectedStatus) {
        // ✅ Fetch row once before performing the action
        WebElement row = requestsPage.getRowBySearch("Employee Name", EMPLOYEE_NAME);

        // ✅ Determine action to perform
        String action = expectedStatus.equalsIgnoreCase("Approved") ? "approve" : "reject";

        // ✅ Perform the action
        requestsPage.clickActionButton(row, RequestsPage.ActionType.valueOf(action.toUpperCase()));

        // ✅ Short wait to check if status updates without refresh
        if (!requestsPage.waitForQuickStatusUpdate(row, expectedStatus)) {
            // ✅ Refresh the table only if needed
            requestsPage.clickSearchButton();
            row = requestsPage.getRowBySearch("Employee Name", EMPLOYEE_NAME);  // ✅ Fetch row again after refresh
        }

        // ✅ Wait for the new status after refresh
        requestsPage.waitForStatusUpdate(EMPLOYEE_NAME, expectedStatus);

        // ✅ Fetch final status using the row reference
        String actualStatus = requestsPage.getCellDataFromRow(row, "Final Status");
        System.out.println("✅ Actual Final Status after action: " + actualStatus);

        // ✅ Assertion
        Assert.assertEquals(actualStatus.trim().toLowerCase(), expectedStatus.toLowerCase(),
                "❌ The request status did not update as expected.");
    }


    private Map<String, String> extractDataFromTable(DataTable dataTable) {
        return dataTable.asMap(String.class, String.class);
    }


    @Then("User Sign out")
    public void userSignOut() {
        signHelper.signOut();

    }


    @When("That Employee tries to enter username and password")
    public void thatEmployeeTriesToEnterUsernameAndPassword() {
//        signHelper.signIn(, );
        //WillFail
    }

    @Given("Hr first level enters username {string} and password {string}")
    public void hrFirstLevelEntersUsernameAndPassword(String arg0, String arg1) {
        signHelper.signIn(arg0, arg1);
    }

    @And("approve hiring request for that employee")
    public void approveHiringRequestForThatEmployee() {
        // ✅ Fetch the row once before clicking the action button
        WebElement row = requestsPage.getRowBySearch("Employee Name", EMPLOYEE_NAME);

        // ✅ Perform the approval action
        requestsPage.clickActionButton(row, RequestsPage.ActionType.APPROVE);

        // ✅ Wait for status update instead of waiting for the entire table
        requestsPage.waitForStatusUpdate(EMPLOYEE_NAME, "Approved");

        // ✅ Fetch the final status using the same row
        String actualStatus = requestsPage.getCellDataFromRow(row, "Final Status");
        System.out.println("✅ Actual Final Status after action: " + actualStatus);

        // ✅ Assertion
        Assert.assertEquals(actualStatus.trim().toLowerCase(), "approved",
                "❌ The request status did not update as expected.");
    }

    @Given("Hr second level enters username {string} and password {string}")
    public void hrSecondLevelEntersUsernameAndPassword(String arg0, String arg1) {

    }
/*

    @Given("It second level enters username {string} and password {string}")
    public void itSecondLevelEntersUsernameAndPassword(String arg0, String arg1) {

    }

    @Given("KPI second level enters username {string} and password {string}")
    public void kpiSecondLevelEntersUsernameAndPassword(String arg0, String arg1) {

    }

    @Given("CFO enters username {string} and password {string}")
    public void cfoEntersUsernameAndPassword(String arg0, String arg1) {

    }

    @Given("his first Reporter enters username and password {string}")
    public void hisFirstReporterEntersUsernameAndPassword(String arg0) {

    }

    @Given("his second Reporter enters username and password {string}")
    public void hisSecondReporterEntersUsernameAndPassword(String arg0) {

    }

    @Given("COO enters username {string} and password {string}")
    public void cooEntersUsernameAndPassword(String arg0, String arg1) {

    }

    @Given("CEO enters username {string} and password {string}")
    public void ceoEntersUsernameAndPassword(String arg0, String arg1) {

    }

    @Then("Successfully Logged In")
    public void successfullyLoggedIn() {
    }*/
}
