package org.example.StepsDef.Student;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.example.Pages.*;
import org.example.Pages.StudentPage.StudentGroupPage.StudentGroupParent;
import org.example.Pages.StudentPage.StudentGroupPage.StudentTicketsPage.StudentTicketsActions.CreateTicketStudent;
import org.example.Pages.StudentPage.StudentGroupPage.StudentTicketsPage.StudentTicketsParent;
import org.example.Pages.StudentPage.StudentParent;
import org.example.Pages.TicketsPage.TicketsParentPage;
import org.example.StepsDef.Hooks;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class CreateTicketAndManageItStepDef {
    private final LoginPage loginPage = new LoginPage();
    private final StudentParent studentParent = new StudentParent();
    private final StudentGroupParent studentGroupParent = new StudentGroupParent();
    private final StudentTicketsParent studentTicketsParent = new StudentTicketsParent();
    private final CreateTicketStudent createTicketStudent = new CreateTicketStudent();
    private final HomePage homePage = new HomePage();
    private final TicketsParentPage ticketsParentPage = new TicketsParentPage();

    /**
     * Logs in as a student with the given email and password.
     */
    @Given("the user logs in as student with email {string} and password {string}")
    public void loginAsStudent(String userEmail, String userPass) {
        loginPage.enterUsername(userEmail);
        loginPage.enterPassword(userPass);
        loginPage.submitLogin();
        Assert.assertEquals(Hooks.getDriver().getTitle(), "Student Profile", "Login Failed!");
    }


    /**
     * Extracts all data related to the user's group and stores it for later use.
     */
    @When("the user extracts all group data")
    public void extractGroupData() {
        List<String> diplomaNames = studentParent.getAllDiplomaNames();
        List<String> groupNames = studentParent.getAllGroupNames();
        List<String> groupDays = studentParent.getAllGroupDays();

        if (!diplomaNames.isEmpty() && !groupNames.isEmpty()) {
            Hooks.scenarioContext.setContext("SELECTED_DIPLOMA", diplomaNames.get(0));
            Hooks.scenarioContext.setContext("SELECTED_GROUP", groupNames.get(0));

            System.out.println("✅ Extracted Diploma: " + diplomaNames.get(0));
            System.out.println("✅ Extracted Group: " + groupNames.get(0));
        } else {
            throw new RuntimeException("❌ No diploma or group found.");
        }
    }

    /**
     * Navigates to the user's group page.
     */
    @And("the user navigates to My Group page")
    public void navigateToMyGroup() {
        String diplomaName = (String) Hooks.scenarioContext.getContext("SELECTED_DIPLOMA");
        String groupName = (String) Hooks.scenarioContext.getContext("SELECTED_GROUP");

        Assert.assertNotNull(diplomaName, "❌ Diploma name not extracted.");
        Assert.assertNotNull(groupName, "❌ Group name not extracted.");

        studentParent.clickOnSpecificGroup(diplomaName, groupName);
        Assert.assertEquals(Hooks.getDriver().getTitle(), "Student Diploma", "❌ Failed to navigate to Student Diploma page.");
    }

    /**
     * Navigates to a specific page within the group section.
     */
    @And("the user navigates to the {string} page")
    public void navigateToPage(String pageTitle) {
        studentGroupParent.clickOnTicketsButton(pageTitle);
        Assert.assertEquals(Hooks.getDriver().getTitle(), "List Of Student Tickets", "❌ Failed to navigate to Tickets page.");
    }

    /**
     * Clicks on the "Create New Ticket" button.
     */
    @And("the user clicks on Create New Ticket button")
    public void clickCreateNewTicket() {
        studentTicketsParent.clickOnNewTicketButton();
        Assert.assertEquals(Hooks.getDriver().getTitle(), "Create Student Tickets", "❌ Failed to navigate to Create Ticket page.");
    }

    /**
     * Fills out the ticket form with provided data.
     */
    @And("the user fills the ticket form with:")
    public void fillTicketForm(DataTable ticketData) {
        List<Map<String, String>> data = ticketData.asMaps(String.class, String.class);

        for (Map<String, String> row : data) {
            String ticketType = row.get("Ticket Type");
            String title = row.get("Title");
            String course = row.get("Course");
            String description = row.get("Description");

            createTicketStudent.fillTicketForm(ticketType, title, course, description);

            Hooks.scenarioContext.setContext("CREATED_TICKET_TITLE", title);
            Hooks.scenarioContext.setContext("CREATED_TICKET_STATUS", "Open");
        }
    }

    /**
     * Clicks on a button with the given title.
     */
    @And("the user clicks on {string} button")
    public void clickButton(String buttonText) {
        createTicketStudent.clickOnCreateTicketButton(buttonText);
    }

    /**
     * Verifies the user is redirected to the correct page.
     */
    @Then("the user should be redirected to {string} page")
    public void verifyRedirect(String pageTitle) {
        Assert.assertEquals(Hooks.getDriver().getTitle(), "List Of Student Tickets", "❌ Redirection failed.");
    }

    /**
     * Ensures the ticket is created successfully.
     */
    @And("the ticket should be created with title {string} and status {string}")
    public void verifyTicketCreation(String title, String status) {
        List<Map<String, Object>> tickets = studentTicketsParent.extractAllTicketsDetails();
        boolean ticketFound = tickets.stream()
                .anyMatch(ticket -> title.equals(ticket.get("title")) && ticket.get("statuses").toString().contains(status));

        Assert.assertTrue(ticketFound, "❌ Created ticket not found with Title: " + title + " and Status: " + status);
    }

    /**
     * Logs out the user.
     */
    @When("the user logs out")
    public void logout() {
        studentParent.logout();
        Assert.assertEquals(Hooks.getDriver().getTitle(), "Login", "❌ Logout failed.");
    }

    /**
     * Logs in as an operation user.
     */
    @Given("the user logs in as operation with email {string} and password {string}")
    public void loginAsOperation(String email, String password) {
        loginPage.enterUsername(email);
        loginPage.enterPassword(password);
        loginPage.submitLogin();
        Assert.assertEquals(Hooks.getDriver().getTitle(), "AMIT Dashboard", "❌ Login failed.");
    }

    /**
     * Navigates to the Tickets page.
     */
    @When("the user navigates to Tickets page")
    public void navigateToTicketsPage() {
        homePage.getTicketsPage();
        Assert.assertEquals(Hooks.getDriver().getTitle(), "List Tickets", "❌ Failed to navigate to Tickets page.");
    }

    /**
     * Searches for the created ticket.
     */
    @And("the user searches for the ticket with ticket type {string} and the extracted diploma name")
    public void searchForCreatedTicket(String ticketType) {
        String diplomaName = (String) Hooks.scenarioContext.getContext("SELECTED_DIPLOMA");

        Assert.assertNotNull(diplomaName, "❌ Diploma name not found in context.");

        ticketsParentPage.searchForSpecificTicket(diplomaName, ticketType);
        ticketsParentPage.clickSearchButton();
    }

}
