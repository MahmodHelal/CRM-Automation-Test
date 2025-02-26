package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Pages.CousresPage.CourseParentPage;
import org.example.Pages.CousresPage.CoursesActionsPage.CreateCoursePage;
import org.example.Pages.HomePage;
import org.example.Pages.LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Map;

public class CreateCourseStepDef {
    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CourseParentPage parentCoursePage = new CourseParentPage();
    private final CreateCoursePage createCoursePage = new CreateCoursePage();
    private final WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(15));

    @Given("user enters credentials:")
    public void userEntersCredentials(DataTable credentials) {
        Map<String, String> credential = credentials.asMap(String.class, String.class);
        loginPage.enterUsername(credential.get("Login Mail"));
        loginPage.enterPassword(credential.get("Password"));
        loginPage.submitLogin();
        waitForPageLoad();
    }

    @When("user navigates to the Courses page and opens the Create Course page")
    public void userNavigatesToTheCoursesPageAndOpensTheCreateCoursePage() {
        homePage.getCoursesPage();
        parentCoursePage.clickAddNewCourseButton();
        waitForPageLoad();
    }

    @Then("adds a course with the following details:")
    public void addsACourseWithTheFollowingDetails(DataTable courseDetails) {
        Map<String, String> data = courseDetails.asMap(String.class, String.class);

        createCoursePage.setNameOfCourse(data.get("Course Name"));
        createCoursePage.setDescriptionOfCourse(data.get("Course Description"));

        for (String dept : data.get("Departments").split(",")) {
            createCoursePage.setDepartmentOfCourse(dept.trim());
        }

        createCoursePage.setNameOfTerm(data.get("Term Name"));
        createCoursePage.setTermHours(data.get("Term Hours"));

        if (data.containsKey("SubTerms")) {
            addSubTerms(data.get("SubTerms"));
        }

        createCoursePage.clickSave();
        wait.until(ExpectedConditions.urlMatches(".*/listcourses"));
    }

    private void waitForPageLoad() {
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(Hooks.getDriver().getCurrentUrl())));
    }

    private void addSubTerms(String subTerms) {
        String[] subTermData = subTerms.split(";");
        String[] names = subTermData[0].split(",");
        String[] descriptions = subTermData[1].split(",");
        String[] feedbacks = subTermData[2].split(",");

        for (int i = 0; i < names.length; i++) {
            createCoursePage.setSubTermName(i, names[i].trim());
            createCoursePage.setSubTermDesc(i, descriptions[i].trim());
            createCoursePage.setSubTermFeedback(i, feedbacks[i].trim());

            if (i < names.length - 1) {
                createCoursePage.addAnotherSubTerm();
            }
        }
    }
}
