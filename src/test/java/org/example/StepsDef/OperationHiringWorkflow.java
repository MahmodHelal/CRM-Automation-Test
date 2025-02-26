/*
package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.DocsPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.EmpInformationPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.ITDetailsPage;
import org.example.Pages.Employees.EmployeesActions.CreateEmployee.JobInfoPage;
import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.Pages.HomePage;
import org.example.Pages.Requests.RequestsPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class OperationHiringWorkflow {

    // Page objects are created as instance variables.
    HomePage homePage = new HomePage();
    EmpInformationPage empInformationPage = new EmpInformationPage();
    JobInfoPage jobInfoPage = new JobInfoPage();
    ITDetailsPage itDetailsPage = new ITDetailsPage();
    DocsPage docsPage = new DocsPage();
    RequestsPage requestsPage = new RequestsPage();
    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(20));

    @When("Create an employee with role operation")
    public void createAnEmployeeWithRoleOperation(DataTable dataTable) throws InterruptedException {
        // Extract all values from the provided DataTable.
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        // --- PERSONAL INFORMATION SECTION ---
        homePage.getEmployeesPage();
        EmployeeParent.clickCreateEmployeeButton();
        // Personal Info (e.g., name, contact, and file uploads)
        empInformationPage.setEmpName();
        empInformationPage.setMobileNumber();
        empInformationPage.setAddress();
        empInformationPage.setPersonalEmail(data.get("name"));
        empInformationPage.setWorkEmail(data.get("name"));
        empInformationPage.uploadPersonalPic();
        empInformationPage.setLinkedinURL();
        empInformationPage.uploadCvPdf();
        empInformationPage.uploadID();
        empInformationPage.uploadBirth();
        empInformationPage.nextButton();

        // --- JOB INFORMATION SECTION ---
        wait.until(ExpectedConditions.textToBe(EmployeeParent.HEADER_OF_SECTION, "Job Information"));
        jobInfoPage.setHiringDate(data.get("hiring date"));
        jobInfoPage.setContractEndDate(data.get("contract end date"));
        jobInfoPage.setWeekendDaysRandomly();
        jobInfoPage.setJobLocation(data.get("job location"));
        jobInfoPage.setApprovalLevel(data.get("approval levels"));
        jobInfoPage.setDepartment(data.get("department"));
        try {
            jobInfoPage.setJobRole(data.get("job role"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while setting job role", e);
        }
        jobInfoPage.setJobTitle(data.get("job role"));
        jobInfoPage.setR1(data.get("1st level manager"));
        jobInfoPage.setR2(data.get("2nd level manager"));
        try {
            jobInfoPage.setNetSalary();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while setting net salary", e);
        }
        jobInfoPage.setCalcSalary(data.get("salary calculation"));
        jobInfoPage.setMedicalCardStatus(data.get("medical card status"));
        try {
            jobInfoPage.setSocialInsuranceStatus(data.get("social insurance status"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while setting social insurance status", e);
        }
        jobInfoPage.setPayrollCardStatus(data.get("payroll card status"));
        jobInfoPage.uploadGradCertificate();
        jobInfoPage.uploadExpCertificate();
        jobInfoPage.nextButton();

        // --- IT DETAILS SECTION ---
        wait.until(ExpectedConditions.textToBe(EmployeeParent.HEADER_OF_SECTION, "IT Details"));
        itDetailsPage.setFingerCode();
        String itDevices = data.get("it devices");
        if (itDevices != null && !itDevices.isEmpty()) {
            // Split comma-separated devices and set each device.
            String[] devices = itDevices.split(",");
            for (String device : devices) {
                itDetailsPage.setItDevices(device.trim());
            }
        }
        itDetailsPage.fillDeviceDetails();
        itDetailsPage.nextButton();

        // --- DOCUMENTS SECTION ---
        wait.until(ExpectedConditions.textToBe(EmployeeParent.HEADER_OF_SECTION, "Documents"));
        docsPage.setGender(data.get("gender"));
        // Upload military service certificate only if gender is male.
        if ("Male".equalsIgnoreCase(data.get("gender"))) {
            docsPage.uploadMilitaryServiceStatusPdf();
        }
        docsPage.uploadCriminalRecordPdf();
        docsPage.uploadBankStatementPdf();
        docsPage.uploadFormSixInsurancePdf();
        docsPage.uploadForm111InsurancePdf();
        docsPage.uploadInsurancePrintPdf();
        docsPage.uploadEmploymentOfficeCertificatePdf();

        docsPage.submitButton();
    }

    @Then("I should see the employee in Employees Report with status {string} and in Request Report with status {string}")
    public void iShouldSeeTheEmployeeInEmployeesReportWithStatusAndInRequestReportWithStatus(String empStatus, String reqStatus) {
        homePage.getRequestsReportPage().click();
     */
/*   requestsPage.searchForEmployee();
        requestsPage.verifyRequestStatus(reqStatus);

        homePage.getEmployeesPage().click();
        EmployeeParent.searchForEmployee();
        EmployeeParent.verifyEmployeeStatus(empStatus);
        *//*

    }
}
*/
