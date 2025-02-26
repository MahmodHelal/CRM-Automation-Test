/*
package org.example.StepsDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Pages.CousresPage.CourseParentPage;
import org.example.Pages.CousresPage.CoursesActionsPage.CreateCoursePage;
import org.example.Pages.HomePage;
import org.testng.Assert;

import java.io.IOException;

public class SwitchingCourse {
HomePage homePage = new HomePage();
CourseParentPage courseParentPage = new CourseParentPage();
    String oldCourseName;

@When("user navigates to the Courses page")
    public void userNavigatesToTheCoursesPage() {
    homePage.getCoursesPage().click();
    }

    @Given("I have an active course {string} with a new course to replace it from the Excel file")
    public void iHaveAnActiveCourseWithANewCourseToReplaceIt(String oldCourseName) throws IOException {
        String[][] courseData = ExcelReader.getCourseData();

        // Loop through the Excel data to find the old course and its replacement
        for (String[] course : courseData) {
            if (course[0].equals(oldCourseName)) {
                String newCourseName = course[1];  // Assuming the new course name is in the second column
                System.out.println("Found old course: " + oldCourseName + ", replacing with: " + newCourseName);
            }
        }
    }
    @When("I click the Switch Button to toggle the course status to Inactive")
    public void iClickTheSwitchButtonToToggleTheCourseStatusToInactive() {
        // Use oldCourseName dynamically from Excel
        courseParentPage.clickSwitchButton(oldCourseName);
    }

    @When("I choose to {string}")
    public void iChooseTo(String action) {
        courseParentPage.selectAction(action);
    }

    @Then("the old course {string} should be replaced with the new course {string} in all pending groups")
    public void theOldCourseShouldBeReplacedWithTheNewCourseInAllPendingGroups(String oldCourse, String newCourse) {
        // Verification step: Check if the course was replaced in the pending groups
        Assert.assertTrue(Hooks.driver.getPageSource().contains(newCourse), "New course was not added!");
    }

    @Then("the instructors assigned to {string} should be reassigned to {string}")
    public void theInstructorsAssignedToShouldBeReassignedTo(String oldCourse, String newCourse) {
        // Verification step: Check if the instructor is reassigned to the new course
        Assert.assertTrue(Hooks.driver.getPageSource().contains(newCourse), "Instructors were not reassigned!");

    }

    @Then("the course materials should be updated in all pending groups")
    public void theCourseMaterialsShouldBeUpdatedInAllPendingGroups() {
        // Verification step: Check if the course materials are updated
        Assert.assertTrue(Hooks.driver.getPageSource().contains("Materials for " + "Math 102"));
    }


}
*/
