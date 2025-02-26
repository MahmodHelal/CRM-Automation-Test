package org.example.Pages;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    // Define a centralized WebDriverWait to avoid redundant object creation
    private final WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(15));

    /**
     * Generic method to find an element by its XPath
     */
    private   WebElement findElementByXPath(String xpath) {
        return Hooks.getDriver().findElement(By.xpath(xpath));
    }

    /**
     * Generic method to click an element and wait for a URL change
     */
    private void clickAndWaitForUrlChange(String xpath) {
        String currentURL = Hooks.getDriver().getCurrentUrl();
        findElementByXPath(xpath).click();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
    }

    /**
     * Navigation methods to different sections of the application
     */
    public   void getRolesPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Roles')]"); }
    public   void getSalesPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Sales & Marketing Dashboard')]"); }
    public   void getLogsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'System Logs')]"); }
    public   void getTicketsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Tickets')]"); }
    public   void getGroupPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Group')]"); }
    public   void getBranchesPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Branches')]"); }
    public   void getDiplomaPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Diploma')]"); }
    public   void getCoursesPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Courses')]"); }
    public   void getStudentsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Students')]"); }
    public   void getLeadStudentsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Lead Students')]"); }
    public   void getLeadAndStudentsReportsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Lead And Students Reports')]"); }
    public   void getDiscountCodePage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Discount Code')]"); }
    public   void getDepartmentsPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Departments')]"); }
    public   void getTasksReportPage() { clickAndWaitForUrlChange("//a/span[contains(text(),'Tasks Report')]"); }
    public   void getRequestsReportPage() { clickAndWaitForUrlChange("//a/span[text()='Requests Report']"); }

    /**
     * Handles navigation where clicking on a parent menu is required first
     */
    public   void getCertificatesTypesPage() {
        findElementByXPath("//a/span[contains(text(),'Certificates')]").click();
         clickAndWaitForUrlChange("//a/span[contains(text(),'Certificates types')]");
    }

    public   void getCertificatesStatusPage() {
        findElementByXPath("//a/span[contains(text(),'Certificates')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'Certificates status')]");
    }

    public   void getB2BAccountsPage() {
        findElementByXPath("//a/span[contains(text(),'B2B')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'B2B Accounts')]");
    }

    public   void getB2BDealsPage() {
        findElementByXPath("//a/span[contains(text(),'B2B')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'B2B Deals')]");
    }

    public   void getInvoicesPage() {
        findElementByXPath("//a/span[contains(text(),'Finance')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'Invoices')]");
    }

    public   void getTransactionsPage() {
        findElementByXPath("//a/span[contains(text(),'Finance')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'Transactions')]");
    }

    /**
     * Employees navigation handling URL change properly
     */
    public void getEmployeesPage() {
        findElementByXPath("//a/span[contains(text(),'Users')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'Employees')]");
    }

    public   void getInstructorsPage() {
        findElementByXPath("//a/span[contains(text(),'Users')]").click();
        clickAndWaitForUrlChange("//a/span[contains(text(),'Instructors')]");
    }
}
