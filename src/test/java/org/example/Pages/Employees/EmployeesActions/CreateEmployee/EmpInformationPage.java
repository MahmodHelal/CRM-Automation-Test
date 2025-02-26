package org.example.Pages.Employees.EmployeesActions.CreateEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

/**
 * This class handles the creation of an employee by filling in the necessary details
 * such as name, email, phone, LinkedIn URL, and uploading required documents.
 */
public class EmpInformationPage extends EmployeeParent {
    private final Random random = new Random();
    private final WebDriver driver = Hooks.getDriver(); // ✅ Thread-safe WebDriver access

    // ---------------------------
    // ✅ Locators
    // ---------------------------
    private static final By NAME_FIELD = By.id("name");
    private static final By PHONE_FIELD = By.id("phone");
    private static final By ADDRESS_FIELD = By.id("address");
    private static final By EMAIL_FIELD = By.id("email");
    private static final By WORK_EMAIL_FIELD = By.id("work_email");
    private static final By LINKEDIN_FIELD = By.id("linkedin");
    private static final By PERSONAL_IMAGE_FIELD = By.id("personal_image");
    private static final By CV_FIELD = By.id("cv_file");
    private static final By NATIONAL_ID_FIELD = By.id("nationalId_card");
    private static final By BIRTH_CERTIFICATE_FIELD = By.id("birth_certificate");
    private static final By SECTION_HEADER = By.xpath("//h3[contains(text(),'Employee Information')]");

    // ---------------------------
    // ✅ Static Data
    // ---------------------------
    private static final String[] STREET_NAMES = {"Main", "Maple", "Oak", "Pine", "Elm", "Cedar", "Birch"};
    private static final String[] CITY_NAMES = {"Cairo", "Alexandria", "Giza", "Sharm", "Luxor", "Aswan"};
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    protected final String PROJ_PATH = System.getProperty("user.dir");
    protected final String PDF_PATH = PROJ_PATH + File.separator + "PDF";
    protected final String IMG_PATH = PROJ_PATH + File.separator + "Images";
    // ---------------------------
    // ✅ Methods to fill out the form
    // ---------------------------

    /**
     * Generates and sets a random full name in the employee name field.
     */
    private void setEmpName() {
        String fullName = faker.name().firstName() + " " +
                faker.name().firstName() + " " +
                faker.name().firstName() + " " +
                faker.name().lastName();
        driver.findElement(NAME_FIELD).sendKeys(fullName);
    }

    /**
     * Generates and sets a random mobile number starting with "01".
     */
    private void setMobileNumber() {
        String mobile = "01" + random.ints(9, 0, 10)
                .mapToObj(String::valueOf)
                .reduce("", String::concat);
        driver.findElement(PHONE_FIELD).sendKeys(mobile);
    }

    /**
     * Generates and sets a random address.
     *
     * @return The generated address.
     */
    private String setAddress() {
        String address = (random.nextInt(999) + 1) + " " +
                STREET_NAMES[random.nextInt(STREET_NAMES.length)] + " Street, " +
                "City " + CITY_NAMES[random.nextInt(CITY_NAMES.length)] + " " +
                String.format("%05d", random.nextInt(100000));
        driver.findElement(ADDRESS_FIELD).sendKeys(address);
        return address;
    }

    /**
     * Generates and sets a random personal email based on employee name.
     *
     * @param empName The name of the employee.
     * @return The generated personal email.
     */
    private String setPersonalEmail(String empName) {
        String email = empName.toLowerCase().replaceAll("\\s+", "") + "_" +
                generateRandomString(3) + "@mail.com";
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        return email;
    }

    /**
     * Generates and sets a random work email based on employee name.
     *
     * @param empName The name of the employee.
     * @return The generated work email.
     */
    private String setWorkEmail(String empName) {
        String workEmail = empName.toLowerCase().replaceAll("\\s+", "") + "_" +
                generateRandomString(3) + "@amit-learning.com";
        driver.findElement(WORK_EMAIL_FIELD).sendKeys(workEmail);
        return workEmail;
    }

    /**
     * Uploads a personal picture from the predefined image directory.
     */
    private void uploadPersonalPic() {
        driver.findElement(PERSONAL_IMAGE_FIELD)
                .sendKeys(IMG_PATH + File.separator + "PNG.png");
    }

    /**
     * Generates and sets a random LinkedIn URL.
     *
     * @return The generated LinkedIn URL.
     */
    private String setLinkedinURL() {
        String linkedinUrl = "https://www.linkedin.com/in/" + generateRandomString(10);
        driver.findElement(LINKEDIN_FIELD).sendKeys(linkedinUrl);
        return linkedinUrl;
    }

    /**
     * Uploads a CV PDF file from the predefined PDF directory.
     */
    private void uploadCvPdf() {
        driver.findElement(CV_FIELD)
                .sendKeys(PDF_PATH + File.separator + "CV.pdf");
    }

    /**
     * Uploads a national ID image.
     */
    private void uploadID() {
        driver.findElement(NATIONAL_ID_FIELD)
                .sendKeys(IMG_PATH + File.separator + "small_icon.png");
    }

    /**
     * Uploads a birth certificate image.
     */
    private void uploadBirth() {
        driver.findElement(BIRTH_CERTIFICATE_FIELD)
                .sendKeys(IMG_PATH + File.separator + "Inner_Image.png");
    }

    // ---------------------------
    // ✅ Getter methods for assertions (used in test/step def)
    // ---------------------------

    private String getEmpName() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(NAME_FIELD)).getAttribute("value");
    }

    private String getMobileNumber() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(PHONE_FIELD)).getAttribute("value");
    }

    private String getAddress() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(ADDRESS_FIELD)).getAttribute("value");
    }

    private String getPersonalEmail() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_FIELD)).getAttribute("value");
    }

    private String getWorkEmail() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(WORK_EMAIL_FIELD)).getAttribute("value");
    }

    private String getLinkedinURL() {
        return SHORT_WAIT.until(ExpectedConditions.visibilityOfElementLocated(LINKEDIN_FIELD)).getAttribute("value");
    }
    private Map<String, Consumer<String>> employeeInfo(String EMPLOYEE_NAME) {
        Map<String, Consumer<String>> personalInfoActions = new HashMap<>();
        personalInfoActions.put("mobileNumber", value -> setMobileNumber());
        personalInfoActions.put("address", value -> setAddress());
        personalInfoActions.put("personalEmail", value -> setPersonalEmail(EMPLOYEE_NAME));
        personalInfoActions.put("workEmail", value -> setWorkEmail(EMPLOYEE_NAME));
        personalInfoActions.put("linkedinURL", value -> setLinkedinURL());

        // File Uploads
        personalInfoActions.put("personalPic", value -> uploadPersonalPic());
        personalInfoActions.put("cvPdf", value -> uploadCvPdf());
        personalInfoActions.put("id", value -> uploadID());
        personalInfoActions.put("birthCert", value -> uploadBirth());
        return personalInfoActions;

    }
    public void fillEmployeeInfo(String EMPLOYEE_NAME) {
        // Set employee name only if it hasn't been set before
        if (EMPLOYEE_NAME == null || EMPLOYEE_NAME.isEmpty()) {
            setEmpName();
            EMPLOYEE_NAME = getEmpName();

            if (EMPLOYEE_NAME == null || EMPLOYEE_NAME.isEmpty()) {
                System.out.println("Warning: Failed to retrieve Employee Name! Check if the name field is correctly set.");
            } else {
//                System.out.println("Employee Name: " + EMPLOYEE_NAME);
            }
        } else {
            System.out.println("Using existing Employee Name: " + EMPLOYEE_NAME);
        }
        Map<String, Consumer<String>> personalInfoActions = employeeInfo(EMPLOYEE_NAME);
        personalInfoActions.forEach((key, action) -> action.accept(null));
    }

    /**
     * Returns all employee information as a Map for easy assertion.
     *
     * @return
     */
    public Map<String, String> getEmployeeInfo() {
        Map<String, String> info = new HashMap<>();

        info.put("name", getEmpName());
        info.put("work_email", getWorkEmail());
        info.put("phone", getMobileNumber());
//        info.put("address", getAddress());
//        info.put("email", getPersonalEmail());
//        info.put("linkedin", getLinkedinURL());

//        // Validate the data
//        Assert.assertFalse(info.get("name").isEmpty(), "Name should not be empty");
//        Assert.assertTrue(info.get("phone").matches("^01[0-9]{9}$"), "Phone number format is incorrect");
//        Assert.assertFalse(info.get("address").isEmpty(), "Address should not be empty");
//        Assert.assertTrue(info.get("email").contains("@"), "Personal email should contain @");
//        Assert.assertTrue(info.get("work_email").contains("@"), "Work email should contain @");
//        Assert.assertTrue(info.get("linkedin").startsWith("https://www.linkedin.com/"), "LinkedIn URL should start with https://www.linkedin.com/");

        // Print the information
        info.forEach((key, value) -> System.out.println(key + ": " + value));

        return info;
    }

    public WebElement getSectionHeader() {
        return SHORT_WAIT.until(ExpectedConditions.presenceOfElementLocated(SECTION_HEADER));

    }

    // ---------------------------
    // ✅ Helper method
    // ---------------------------

    /**
     * Generates a random alphanumeric string of the specified length.
     *
     * @param length The desired length of the random string.
     * @return A randomly generated string.
     */
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
