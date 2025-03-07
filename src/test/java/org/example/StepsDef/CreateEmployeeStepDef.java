package org.example.StepsDef;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Helpers.SignHelper;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.*;
import org.example.Pages.HomePage;
import org.example.Pages.LoginPage;
import org.example.Pages.Requests.RequestsPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public class CreateEmployeeStepDef {
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

    // Test Data (To maintain test state)
    private String EMPLOYEE_NAME = null;
    private String EMPLOYEE_EMAIL = null;


    /**
     * Step: User logs in using the given username and password.
     */
    @Given("user enters username {string} and password {string}")
    public void userEntersUserNameAndPassword(String userName, String password) {
        loginPage.enterUsername(userName);
        loginPage.enterPassword(password);
        loginPage.submitLogin();
    }


    /**
     * Step: User navigates to the Employees Page.
     */
    @When("user opens the Employees Page")
    public void userOpensTheEmployeesPage() {
        homePage.getEmployeesPage();
    }


    /**
     * Step: User Opens the "Create Employee" Page.
     */
    @And("opens Create Employee page")
    public void opensCreateEmployeePage() {
        empInformationPage.clickCreateEmployeeButton();
        Assert.assertTrue(Objects.requireNonNull(Hooks.getDriver().getCurrentUrl()).contains("create-employee"), "Failed to open Create Employee page");
    }


    /**
     * Step: Fills in the personal information for the new employee.
     */
    @Then("fills the personal information")
    public void fillsThePersonalInformation() {
        empInformationPage.fillEmployeeInfo(EMPLOYEE_NAME);

        // Retrieve and store employee details
        Map<String, String> employeeInfo = empInformationPage.getEmployeeInfo();

        EMPLOYEE_NAME = employeeInfo.get("name");
        EMPLOYEE_EMAIL = employeeInfo.get("work_email");

        // Validation: Ensure employee name is captured
        if (EMPLOYEE_NAME == null || EMPLOYEE_NAME.isBlank()) {
            throw new RuntimeException("❌ EMPLOYEE_NAME is missing. Ensure correct data entry.");
        }

        System.out.println("✔ EMPLOYEE_NAME captured: " + EMPLOYEE_NAME);

        empInformationPage.nextButton();
    }

    /**
     * Step: Fills in the job information.
     */
    @And("fills the job information")
    public void fillsTheJobInformation(DataTable jobInfoTable) {
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

        jobInfoPage.nextButton();
    }


    /**
     * Step: Fills IT-related information for the new employee.
     */
    @And("Fill IT Data")
    public void fillITData(DataTable itDevicesTable) {
        Map<String, String> itDevices = extractDataFromTable(itDevicesTable);
        itDetailsPage.setFingerCode();

        itDevices.forEach((key, value) -> {
            if (key.equalsIgnoreCase("it devices")) {
                itDetailsPage.processItDevices(value);
            } else {
                System.err.println("Warning: Unrecognized IT Data key: " + key);
            }
        });

        itDetailsPage.nextButton();
    }


    /**
     * Step: Uploads necessary documents and submits the employee creation form.
     */
    @And("Fill Documents Data And Submit")
    public void fillDocumentsDataAndSubmit(DataTable DocumentsTable) {
        Map<String, String> docs = extractDataFromTable(DocumentsTable);

        docs.forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "gender" -> {
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
                }
                case "optional documents" -> uploadDocumentsPage.handleOptionalDocuments(value);
                default -> System.out.println("Unrecognized document key: " + key);
            }
        });

        uploadDocumentsPage.submitButton();
    }



    /**
     * Step: Verifies the created employee request in the Requests page.
     */
    @Then("Open Requests Page To Check Data")
    public void openRequestsPageToCheckData(DataTable empTable) {
        homePage.getRequestsReportPage();
        requestsPage.waitForUpdatedRows();  // ✅ Ensure table is updated before searching

        Map<String, String> empInfo = extractDataFromTable(empTable);

        WebElement row = requestsPage.getRowBySearch("Employee Name", EMPLOYEE_NAME);
        if (row == null) {
            System.out.println("❌ No row found for Employee: " + EMPLOYEE_NAME);
            throw new RuntimeException("❌ No row found for Employee: " + EMPLOYEE_NAME);
        }
        System.out.println("✅ Employee row found: " + EMPLOYEE_NAME);

        Map<String, String> actualData = requestsPage.getEmployeeData(row);

        empInfo.forEach((key, expectedValue) -> {
            if (!actualData.containsKey(key.toLowerCase())) {
                throw new RuntimeException("❌ Key not found in actualData: " + key);
            }

            String actualValue = actualData.get(key.toLowerCase());

            // ✅ Handle date formatting separately
            if (key.equalsIgnoreCase("effective date")) {
                actualValue = requestsPage.formatDate(actualValue);
            }

            System.out.println("🔍 Checking: " + key + " | Expected: " + expectedValue + " | Found: " + actualValue);
            Assert.assertEquals(actualValue, expectedValue, "❌ Mismatch found for " + key);
        });
    }




    /**
     * Step: HR first level approves the hiring request.
     */
    @And("approve hiring request for that employee {string}")
    public void approveHiringRequestForThatEmployee(String expectedStatus) {
        boolean approvalSuccessful = requestsPage.performActionAndWaitForStatus(EMPLOYEE_NAME, expectedStatus);
        if (!approvalSuccessful) {
            throw new AssertionError("❌ Request status update failed. Expected status: " + expectedStatus);
        }
    }


    /**
     * Step: HR second level signs in and approves the request.
     */
    @Given("Hr second level enters username {string} and password {string}")
    public void hrSecondLevelEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }
    @Given("It second level enters username {string} and password {string}")
    public void itSecondLevelEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }


    @Given("KPI second level enters username {string} and password {string}")
    public void kpiSecondLevelEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }

    @Given("CFO enters username {string} and password {string}")
    public void cfoEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }

    @Given("Owner enters username {string} and password {string}")
    public void ownerEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }

    @Given("COO enters username {string} and password {string}")
    public void cooEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }

    @Given("CEO enters username {string} and password {string}")
    public void ceoEntersUsernameAndPassword(String username, String password) {
        signHelper.signIn(username, password);
    }
    @Then("login with new mail{string}")
    public void loginWithNewMail(String passsord) {
        signHelper.signIn(EMPLOYEE_EMAIL, passsord);
    }















    /**
     * Step: User signs out.
     */
    @Then("User Sign out")
    public void userSignOut() {
        signHelper.signOut();
    }

    /**
     * Utility method: Extracts data from a DataTable into a Map.
     */
    private Map<String, String> extractDataFromTable(DataTable dataTable) {
        return dataTable.asMap(String.class, String.class);
    }



    public void adelHanaka() {
    }
}
