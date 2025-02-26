package org.example.Helpers;

import org.example.Pages.DiplomaPage.DiplomaActions.CreateDiplomaPage;

import java.util.Map;
import java.util.Optional;

public class DiplomaHelper {
    private final CreateDiplomaPage createDiplomaPage;

    public DiplomaHelper(CreateDiplomaPage createDiplomaPage) {
        this.createDiplomaPage = createDiplomaPage;
    }

    /**
     * Fills out the diploma form dynamically based on the provided data.
     */
    public void fillDiplomaForm(Map<String, String> diplomaData) {
        diplomaData.forEach((key, value) -> {
            String trimmedKey = key.toLowerCase().trim();
            String trimmedValue = Optional.ofNullable(value).orElse("").trim();

            switch (trimmedKey) {
                case "diploma name" -> {
                    createDiplomaPage.setNameOfDiploma(trimmedValue);
                    createDiplomaPage.setSlug();
                    createDiplomaPage.setShortcutName();
                }
                case "feesle" -> createDiplomaPage.setFeesLE(trimmedValue);
                case "feesusd" -> createDiplomaPage.setFeesUSD(trimmedValue);
                case "has kit" -> createDiplomaPage.hasKit(trimmedValue);
                case "diploma type" -> createDiplomaPage.setTypeOfDiploma(trimmedValue);
                case "welcomemail" -> createDiplomaPage.hasWelcomeMail(trimmedValue);
                case "department" -> processMultipleSelections(trimmedValue,
                        () -> createDiplomaPage.addDepartmentButton().click(),
                        createDiplomaPage::setDepartments,
                        () -> createDiplomaPage.clickYesToClosePopup());
                case "courses" -> processMultipleSelections(trimmedValue,
                        () -> createDiplomaPage.addCourseButton().click(),
                        createDiplomaPage::setCourses,
                        () -> createDiplomaPage.clickYesToClosePopup());
                case "requirements" -> createDiplomaPage.addRequirement(trimmedValue.split(","));
                case "learns" -> createDiplomaPage.addLearns(trimmedValue.split(","));
                case "video url" -> createDiplomaPage.setVideoUrl(trimmedValue);
                case "diploma image" -> createDiplomaPage.uploadDiplomaImg(trimmedValue);
                case "diploma inner image" -> createDiplomaPage.uploadDiplomaInnerImg(trimmedValue);
                case "master icon" -> createDiplomaPage.uploadMasterIconImg(trimmedValue);
                case "slave icon" -> createDiplomaPage.uploadSlaveIconImg(trimmedValue);
                case "head title" -> createDiplomaPage.setHeadTitle(trimmedValue);
                case "meta title" -> createDiplomaPage.setMetaTitle(trimmedValue);
                case "meta description" -> createDiplomaPage.setMetaDescriptionTitle(trimmedValue);
                case "meta keywords" -> createDiplomaPage.setMetaKeywordsTitle(trimmedValue);
                case "short description" -> createDiplomaPage.setShortDescription(trimmedValue);
                case "diploma description" -> createDiplomaPage.setDescription(trimmedValue);
                default -> System.out.println("[WARNING] Unrecognized key: " + trimmedKey);
            }
        });
    }

    /**
     * Processes multiple selections (departments, courses) efficiently.
     */
    private void processMultipleSelections(String value, Runnable openAction,
                                           java.util.function.Consumer<String> selectAction,
                                           Runnable closeAction) {
        if (!value.isEmpty()) {
            String[] items = value.split(",");
            openAction.run();
            for (String item : items) {
                selectAction.accept(item.trim());
            }
            closeAction.run();
        }
    }
}
