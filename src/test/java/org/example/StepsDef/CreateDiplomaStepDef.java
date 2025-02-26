package org.example.StepsDef;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Helpers.DiplomaHelper;
import org.example.Helpers.SignHelper;
import org.example.Pages.DiplomaPage.DiplomaActions.CreateDiplomaPage;
import org.example.Pages.HomePage;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CreateDiplomaStepDef {
    HomePage homePage = new HomePage();
    CreateDiplomaPage createDiplomaPage = new CreateDiplomaPage();
    SignHelper loginHelper = new SignHelper();
    DiplomaHelper diplomaHelper = new DiplomaHelper(createDiplomaPage);

    String projPath = System.getProperty("user.dir");
    String diplomaFilePath = projPath + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "diploma.xlsx";

    @When("user navigates to the Diplomas page and opens the Create Diploma page")
    public void userNavigatesToTheDiplomasPageAndOpensTheCreateDiplomaPage() {
        homePage.getDiplomaPage();
        createDiplomaPage.addNewDiplomaButton();
    }

    @Then("adds a diploma with its details")
    public void addsADiplomaWithItsDetails(DataTable diplomaDetails) {
        // Convert the DataTable to a List of Maps
        List<Map<String, String>> diplomaInfo = diplomaDetails.asMaps(String.class, String.class);

        // Process each row
        for (Map<String, String> entryMap : diplomaInfo) {
            // Delegate the form filling to the helper
            diplomaHelper.fillDiplomaForm(entryMap);
            createDiplomaPage.saveButton();
        }
    }
}
