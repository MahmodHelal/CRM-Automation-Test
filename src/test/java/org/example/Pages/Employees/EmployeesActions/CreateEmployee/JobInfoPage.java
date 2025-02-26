package org.example.Pages.Employees.EmployeesActions.CreateEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Page class for handling job information fields during employee creation.
 */
public class JobInfoPage extends EmployeeParent {
    private final Random random = new Random();

    // ✅ Salary & Insurance Constants
    private static final int MIN_SALARY = 5000;
    private static final int MAX_SALARY = 20000;
    private static final double GROSS_MULTIPLIER = 1.25;
    private static final double BASIC_SALARY_PERCENT = 0.6;
    private static final double SOCIAL_SALARY_PERCENT = 0.8;
    private static final double SOCIAL_INSURANCE_RATE = 0.14;
    private static final double COMPANY_SOCIAL_SHARE_RATE = 0.6;
    private static final double EMPLOYEE_SOCIAL_SHARE_RATE = 0.4;
    private static final double EMPLOYEE_SOCIAL_SHARE_DEDUCTED_RATE = 0.2;
    

    public WebElement getSectionHeader() {
        return driver.findElement(By.xpath("//h3[contains(text(),'Job Information')]"));
    }
    public void setR1(String reporter1) {
        helperMethods.selectDropdownByVisibleText(By.id("reporting_level_one"), reporter1);
        System.out.println("Reporter 1 set to: " + reporter1);
    }

    public void setR2(String reporter2) {
        helperMethods.selectDropdownByVisibleText(By.id("reporting_level_two"), reporter2);
        System.out.println("Reporter 2 set to: " + reporter2);
    }

    // ✅ Random Salary Calculation
    private final double randomSalary = random.nextInt(MAX_SALARY - MIN_SALARY + 1) + MIN_SALARY;

    // ---------------------------
    // ✅ Date Handling Methods
    // ---------------------------

    public void setHiringDate(String date) {
        setDateField(By.xpath("//label[text()='Hiring Date*']/following::input[1]"), date);
    }

    public void setContractEndDate(String date) {
        setDateField(By.xpath("//label[text()='Contract End date*']/following::input[1]"), date);
    }

    private void setDateField(By locator, String date) {
        WebElement inputField = WAIT.until(ExpectedConditions.presenceOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(convertToMMDDYYYY(date));
    }

    private String convertToMMDDYYYY(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    .format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (Exception e) {
            System.err.println("❌ Invalid date format: " + date);
            return date;
        }
    }

    // ---------------------------
    // ✅ Dropdown Selection Methods
    // ---------------------------

    public void setWeekendDaysRandomly() {
        int numOfDays = random.nextInt(2) + 1;
//        helperMethods.WAITForPopupToDisappear();
        helperMethods.selectDropdownByVisibleText(By.id("no_of_weekend_days"), String.valueOf(numOfDays));
        List<WebElement> days = driver.findElements(By.xpath("//label[text()=' Weekend Days* ']/following-sibling::div//div[@class='p-checkbox p-component']"));
        Collections.shuffle(days);
        days.stream().limit(numOfDays).forEach(WebElement::click);
    }

    public void setJobLocation(String location) {
        helperMethods.selectDropdownByVisibleText(By.id("job_location"), location.trim());
    }

    public void setApprovalLevels(String levels) {
        if (levels == null || levels.trim().isEmpty() || "none".equalsIgnoreCase(levels)) return;

        Arrays.stream(levels.split(",")).map(String::trim).forEach(level -> {
            helperMethods.selectDropdownByVisibleText(By.id("approval_level"), level);

            ACTIONS.sendKeys(Keys.ESCAPE).perform();
            // ✅ Verify selection is saved in UI
            String selectedValue = helperMethods.getDropdownSelectedValue(By.id("approval_level"));
            if (!selectedValue.contains(level)) {
                throw new RuntimeException("❌ Failed to select approval level: " + level);
            }
        });

    }


    public void setDepartment(String department) {
        helperMethods.selectDropdownByVisibleText(By.xpath("//span[text()='Department']"), department);
    }

    public void setJobRole(String jobRole) {
        helperMethods.selectDropdownByVisibleText(By.id("job_role"), jobRole);
        System.out.println("Job Role: " + jobRole);
    }

    public void setJobTitle(String jobTitle) {
        setInputField(By.id("job_title"), jobTitle.replaceAll("[-&]", "").toLowerCase());
    }
    // ---------------------------
    // ✅ Salary Handling Methods
    // ---------------------------
    // ---------------------------
    // ✅ Salary & Tax Handling Methods
    // ---------------------------

    public void setCalcSalary(String calcSalary) {
        Map<String, String> salaryOptions = Map.of(
                "Fingerprint", "//input[@value='fingerprint']/ancestor::div[contains(@class, 'p-radiobutton p-component')]",
                "Timesheet", "//input[@value='timesheet']/ancestor::div[contains(@class, 'p-radiobutton p-component')]",
                "Without fingerprint & timesheet", "//input[@value='no_fingerprint_timesheet']/ancestor::div[contains(@class, 'p-radiobutton p-component')]"
        );

        if (!salaryOptions.containsKey(calcSalary)) {
            throw new IllegalArgumentException("❌ Invalid salary calculation option: " + calcSalary);
        }

        WebElement radioButton = driver.findElement(By.xpath(salaryOptions.get(calcSalary)));
        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", radioButton);
        WAIT.until(ExpectedConditions.elementToBeClickable(radioButton)).click();
        WAIT.until(ExpectedConditions.attributeContains(radioButton, "class", "p-radiobutton-checked"));
    }


    public void setNetSalary() {
        setInputField(By.id("net_salary"), String.valueOf(randomSalary));
        setDeductedInTax();
        setNotDeductedInTax();
    }

    private void setDeductedInTax() {
        setInputField(By.id("monthly_income_tax"), String.format("%.2f", randomSalary * 0.10));
    }

    private void setNotDeductedInTax() {
        setInputField(By.id("non_monthly_income_tax"), String.format("%.2f", randomSalary * 0.05));
    }

    // ---------------------------
    // ✅ Social Insurance Handling Methods
    // ---------------------------

    public void setSocialInsuranceStatus(String status) {
        helperMethods.selectDropdownByVisibleText(By.id("social_insurance_status"), status);
        if (status.equalsIgnoreCase("added")) {
            setSocialInsuranceNumber();
            setSocialInsuranceGrossSalary(randomSalary);
        }
    }

    private void setSocialInsuranceNumber() {
        setInputField(By.id("social_insurance_no"), String.valueOf(random.nextInt(9000000) + 100000));
    }

    private void setSocialInsuranceGrossSalary(double netSalary) {
        double grossSalary = netSalary * GROSS_MULTIPLIER;
        setInputField(By.id("gross_salary"), String.format("%.2f", grossSalary));
        setSocialInsuranceBasicSalary(grossSalary * BASIC_SALARY_PERCENT);
        setSocialInsuranceSalary(grossSalary * SOCIAL_SALARY_PERCENT);
    }

    private void setSocialInsuranceBasicSalary(double basicSalary) {
        setInputField(By.id("basic_salary"), String.format("%.2f", basicSalary));
    }

    private void setSocialInsuranceSalary(double insuranceSalary) {
        setInputField(By.id("social_insurance_salary"), String.format("%.2f", insuranceSalary));
        setSocialInsuranceInstallment(insuranceSalary * SOCIAL_INSURANCE_RATE);
    }

    private void setSocialInsuranceInstallment(double installment) {
        setInputField(By.id("total_social_installment"), String.format("%.2f", installment));
        setCompanyShareSI(installment * COMPANY_SOCIAL_SHARE_RATE);
        setEmployeeShareSocialInsuranceNotDeducted(installment * EMPLOYEE_SOCIAL_SHARE_RATE);
        setEmployeeShareSocialInsuranceDeducted(installment * EMPLOYEE_SOCIAL_SHARE_DEDUCTED_RATE);
    }
    private void setCompanyShareSI(double companyShareSI) {
        setInputField(By.id("company_social_share"), String.format("%.2f", companyShareSI));
    }
    private void setEmployeeShareSocialInsuranceNotDeducted(double employeeShareSI) {
        setInputField(By.id("employee_social_insurance_share_not_deducted"), String.format("%.2f", employeeShareSI));
    }
    private void setEmployeeShareSocialInsuranceDeducted(double employeeShareSI) {
        setInputField(By.id("employee_social_share"), String.format("%.2f", employeeShareSI));
    }

    // ---------------------------
    // ✅ Medical Card Handling Methods
    // --- Medical Insurance Related ---
    // ---------------------------


    public void setMedicalCardStatus(String status)  {
        helperMethods.selectDropdownByVisibleText(By.id("medical_card_status"), status);
        if (status.equalsIgnoreCase("added")) {
            setMedicalCardImage(PDF_PATH + File.separator + "file-sample_150kB.pdf");
            setTotalCostPerYearMI(random.nextDouble() * 1000);
        }
    }

    private void setMedicalCardImage(String filePath) {
        SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(By.id("medical_card_image")))
                .sendKeys(filePath);
    }

    private void setTotalCostPerYearMI(double totalCost)  {
        setInputField(By.id("total_medical_cost"), String.valueOf(totalCost));
        setCompanySharePerYearMI(totalCost * 90 / 100);
        setEmployeeSharePerYearMI(totalCost * 10 / 100);
    }
    private void setCompanySharePerYearMI(double companyShare) {
        setInputField(By.id("company_share_in_medical_insurance"), String.valueOf(companyShare));
    }
    private void setEmployeeSharePerYearMI(double employeeShare)  {
        setInputField(By.id("employee_share_in_medical_insurance_per_year"), String.valueOf(employeeShare));
        setEmployeeSharePerMonthMI(employeeShare / 12);
    }
    private void setEmployeeSharePerMonthMI(double employeeSharePerMonth)  {
        setInputField(By.id("employee_share_in_medical_insurance_per_month"), String.valueOf(employeeSharePerMonth));
    }
    // ---------------------------
    // ✅ Payroll Handling Methods
    // ---------------------------

    public void setPayrollCardStatus(String status) {
        helperMethods.selectDropdownByVisibleText(By.id("payroll_card_status"), status);
        if (status.equalsIgnoreCase("added")) {
            setBankAccNumber();
        }
    }

    public void setBankAccNumber() {
        setInputField(By.id("bank_account_no"), String.valueOf(random.nextInt(900000000) + 1000000000));
    }

    // ---------------------------
    // ✅ Certificate Uploads
    // ---------------------------

    public void uploadGradCertificate() {
        setInputField(By.id("graduation_certificate"), PDF_PATH + File.separator + "file-sample_150kB.pdf");
    }

    public void uploadExpCertificate() {
        setInputField(By.id("experience_certificates"), PDF_PATH + File.separator + "file-sample_150kB.pdf");
    }
    public String getHiringDate() {
        return getInputFieldValue(By.id("hiring_date"));
    }

    public String getContractEndDate() {
        return getInputFieldValue(By.id("contract_end_date"));
    }
    public String getR1() {
        return helperMethods.getDropdownSelectedValue(By.id("reporting_level_one"));
    }

    public String getR2() {
        return helperMethods.getDropdownSelectedValue(By.id("reporting_level_two"));
    }
    public String getDepartment() {
        return helperMethods.getDropdownSelectedValue(By.xpath("//span[text()='Department']"));
    }

    public String getJobRole() {
        return helperMethods.getDropdownSelectedValue(By.id("job_role"));
    }

    public String getJobTitle() {
        return getInputFieldValue(By.id("job_title"));
    }

    public String getJobLocation() {
        return helperMethods.getDropdownSelectedValue(By.id("job_location"));
    }
    public String getWeekendDays() {
        return helperMethods.getDropdownSelectedValue(By.id("no_of_weekend_days"));
    }
    public String getNetSalary() {
        return getInputFieldValue(By.id("net_salary"));
    }

    public String getMonthlyIncomeTax() {
        return getInputFieldValue(By.id("monthly_income_tax"));
    }

    public String getNonMonthlyIncomeTax() {
        return getInputFieldValue(By.id("non_monthly_income_tax"));
    }
    public String getSocialInsuranceStatus() {
        return helperMethods.getDropdownSelectedValue(By.id("social_insurance_status"));
    }

    public String getSocialInsuranceNumber() {
        return getInputFieldValue(By.id("social_insurance_no"));
    }

    public String getGrossSalary() {
        return getInputFieldValue(By.id("gross_salary"));
    }

    public String getBasicSalary() {
        return getInputFieldValue(By.id("basic_salary"));
    }

    public String getSocialInsuranceSalary() {
        return getInputFieldValue(By.id("social_insurance_salary"));
    }
    public String getMedicalCardStatus() {
        return helperMethods.getDropdownSelectedValue(By.id("medical_card_status"));
    }

    public String getTotalMedicalCost() {
        return getInputFieldValue(By.id("total_medical_cost"));
    }

    public String getCompanyMedicalShare() {
        return getInputFieldValue(By.id("company_share_in_medical_insurance"));
    }

    public String getEmployeeMedicalSharePerMonth() {
        return getInputFieldValue(By.id("employee_share_in_medical_insurance_per_month"));
    }
    public String getPayrollCardStatus() {
        return helperMethods.getDropdownSelectedValue(By.id("payroll_card_status"));
    }

    public String getBankAccountNumber() {
        return getInputFieldValue(By.id("bank_account_no"));
    }
    public String getApprovalLevels() {
        return helperMethods.getDropdownSelectedValue(By.id("approval_level"));
    }
    private String getInputFieldValue(By locator) {
        return WAIT.until(ExpectedConditions.presenceOfElementLocated(locator)).getAttribute("value").trim();
    }


    /**
     * Returns all employee job information as a Map for easy assertion.
     *
     * @return
     */
    public Map<String, String> getJobInfoData() {
        Map<String, String> jobInfo = new HashMap<>();

//        jobInfo.put("hiring_date", getHiringDate());
//        jobInfo.put("contract_end_date", getContractEndDate());
        jobInfo.put("reporting_level_one", getR1());
        jobInfo.put("reporting_level_two", getR2());
//        jobInfo.put("department", getDepartment());
        jobInfo.put("job_role", getJobRole());
//        jobInfo.put("job_title", getJobTitle());
//        jobInfo.put("job_location", getJobLocation());
//        jobInfo.put("weekend_days", getWeekendDays());
//        jobInfo.put("net_salary", getNetSalary());
//        jobInfo.put("monthly_income_tax", getMonthlyIncomeTax());
//        jobInfo.put("non_monthly_income_tax", getNonMonthlyIncomeTax());
//        jobInfo.put("social_insurance_status", getSocialInsuranceStatus());
//        jobInfo.put("social_insurance_no", getSocialInsuranceNumber());
//        jobInfo.put("gross_salary", getGrossSalary());
//        jobInfo.put("basic_salary", getBasicSalary());
//        jobInfo.put("social_insurance_salary", getSocialInsuranceSalary());
//        jobInfo.put("medical_card_status", getMedicalCardStatus());
//        jobInfo.put("total_medical_cost", getTotalMedicalCost());
//        jobInfo.put("company_medical_share", getCompanyMedicalShare());
//        jobInfo.put("employee_medical_share_per_month", getEmployeeMedicalSharePerMonth());
//        jobInfo.put("payroll_card_status", getPayrollCardStatus());
//        jobInfo.put("bank_account_no", getBankAccountNumber());
        jobInfo.put("approval_levels", getApprovalLevels());

        System.out.println("✅ Extracted Job Information:");
        jobInfo.forEach((key, value) -> System.out.println(key + ": " + value));

        return jobInfo;
    }

}

