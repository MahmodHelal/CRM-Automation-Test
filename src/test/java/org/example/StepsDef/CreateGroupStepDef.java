package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.checkerframework.checker.units.qual.C;
import org.example.Pages.CousresPage.CoursesActionsPage.CreateCoursePage;
import org.example.Pages.CousresPage.CourseParentPage;
import org.example.Pages.DiplomaPage.DiplomaActions.CreateDiplomaPage;
import org.example.Pages.GroupsPage.GroupsActions.CreateGroupPage;
import org.example.Pages.HomePage;
import org.example.Pages.LoginPage;

import java.util.List;
import java.util.Map;

public class CreateGroupStepDef {
LoginPage loginPage = new LoginPage();
HomePage homePage = new HomePage();
CreateGroupPage createGroupPage = new CreateGroupPage();
  /*  @Given("user enters credentials:")
    public void userEntersCredentials(DataTable credentials) {
        List<Map<String, String>> data = credentials.asMaps(String.class, String.class);
        String loginMail = data.get(0).get("Login Mail");
        String password = data.get(0).get("Password");
        loginPage.userName().sendKeys(loginMail);
        loginPage.password().sendKeys(password);
        loginPage.clickOnLoginButton();

    }*/









    @Then("user navigates to the Groups page and opens the Create Group page")
    public void userNavigatesToTheGroupsPageAndOpensTheCreateGroupPage() {
        homePage.getGroupPage();
        createGroupPage.addGroupButton().click();


    }

    @And("adds a group with the following details")
    public void addsAGroupWithTheFollowingDetails(DataTable dataTable) throws InterruptedException {
        // Convert DataTable to a Map for easy access to column values
        List<Map<String, String>> groupDetails = dataTable.asMaps(String.class, String.class);

        // Loop through each row in the DataTable
        for (Map<String, String> group : groupDetails) {
            // Set Group Type
            createGroupPage.setGroupType(group.get("Group Type"));

            // Set Diploma
            createGroupPage.setDiploma(group.get("Diploma"));

            // Set Starting Date
            createGroupPage.setStartingDate(group.get("Starting Date"));

            // Set Training Days (split by commas for multiple days)
         /*   String[] trainingDays = group.get("Training Days").split(",");
            for (String day : trainingDays) {
                createGroupPage.setTrainingDays(day.trim());
            }
*/
            // Set Training Hours
            String[] trainingHours = group.get("Training Hours").split(",");
            if (trainingHours.length == 3) {
                createGroupPage.setTrainingHours(trainingHours[0], trainingHours[1], trainingHours[2]);
            }

            // Set Hybrid
            createGroupPage.isHybrid(group.get("Hybrid"));

            // Set Online
            createGroupPage.isOnline(group.get("Online"));

            // Set Viral
            createGroupPage.isViral(group.get("Viral"));

            // Show on Website
            createGroupPage.showOnWebsite(group.get("Show on Website"));

            // Select Branch
            createGroupPage.selectBranch(group.get("Branch"));

            // Select Lab
            createGroupPage.selectLab(group.get("Lab"));

            /*// Set Fees
            createGroupPage.setFees(group.get("Fees"));*/
        }
        createGroupPage.saveButton();
    }

}

