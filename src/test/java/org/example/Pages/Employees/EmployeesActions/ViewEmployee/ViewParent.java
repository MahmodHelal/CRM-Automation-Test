/*
package org.example.Pages.Employees.EmployeesActions.ViewEmployee;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ViewParent {

    // Getter methods
    public String getName() {
        return Hooks.getDriver().findElement(By.xpath("//div[contains(@class, 'ml-6')]/h2")).getText();
    }

    public String getPhone() {
        return getFieldText("Phone");
    }

    public String getWorkEmail() {
        return getFieldText("Work Email");
    }

    public String getBranch() {
        return getFieldText("Branch");
    }

    public String getDepartment() {
        return getFieldText("Department");
    }

    public String getJobTitle() {
        return getFieldText("Job Title");
    }

    public String getJobRole() {
        return getFieldText("Job Role");
    }

    // Verification methods
    public void verifyEmployeeDetails(String expectedName, String expectedPhone,
                                      String expectedEmail, String expectedBranch,
                                      String expectedDept, String expectedJobTitle,
                                      String expectedJobRole) {
        Assert.assertEquals(getName(), expectedName, "Name mismatch");
        Assert.assertEquals(getPhone(), expectedPhone, "Phone mismatch");
        Assert.assertEquals(getWorkEmail(), expectedEmail, "Email mismatch");
        Assert.assertEquals(getBranch(), expectedBranch, "Branch mismatch");
        Assert.assertEquals(getDepartment(), expectedDept, "Department mismatch");
        Assert.assertEquals(getJobTitle(), expectedJobTitle, "Job Title mismatch");
        Assert.assertEquals(getJobRole(), expectedJobRole, "Job Role mismatch");
    }

    private String getFieldText(String fieldName) {
        WebElement element = Hooks.driver.findElement(
                By.xpath(String.format("//div[contains(@class, 'ml-6')]/p[strong[text()='%s :']]", fieldName))
        );
        return element.getText().replace(fieldName + " :", "").trim();
    }
}*/
