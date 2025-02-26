package Selenium_Locaters;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;
import java.util.List;



public class TestTry {
    static WebDriver driver = new ChromeDriver();
    static Actions actions = new Actions(driver);
    static Actions ACTIONS = new Actions(driver);
    protected static String projPath = System.getProperty("user.dir");
    protected static String imgPath = projPath+ File.separator+"Images";
    protected static WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
    protected static WebDriverWait SHORT_WAIT = new WebDriverWait(driver, Duration.ofSeconds(2));
    protected static WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    protected static WebDriverWait WAIT = new WebDriverWait(driver, Duration.ofSeconds(30));
    static JavascriptExecutor js = (JavascriptExecutor) driver;
    protected static final String PROJ_PATH = System.getProperty("user.dir");
    protected static final String pdfPath = PROJ_PATH + File.separator + "PDF";
    static JavascriptExecutor JS = (JavascriptExecutor) driver;




    public static void main(String[] args) throws InterruptedException {
        /*        driver.navigate().to("https://the-internet.herokuapp.com/");
        driver.manage().window().maximize();
        driver.findElement(By.linkText("Form Authentication")).click();
        driver.findElement(By.linkText("Elemental Selenium")).click();*/
        /*driver.get("http://test.rubywatir.com/radios.php");
        driver.findElement(By.id("radioId")).click();*/
        // Locate the <p> element with the unique text "IT"
        driver.get("https://crmtest.amit-learning.com/");
        driver.manage().window().maximize();
        driver.findElement(By.id("email")).sendKeys("christen.adel@amit-learning.com");
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("VZDbi4TE23D1eU");
        actions.sendKeys(Keys.ESCAPE).perform();
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[contains(text(),'Requests Report')]")).click();
        searchForEmployee("BLLA BLLgdf gdfgd dggd total");

//        driver.findElement(By.xpath("//span[contains(text(),'Tickets')]")).click();
//        searchForSpecificTicket("Full Stack PHP Diploma","Technical Support");
    /*driver.findElement(By.xpath("//button[.//i[@class='pi pi-user']]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Logout')]"))).click();*/
        /*driver.findElement(By.xpath("//a/span[text()='Requests Report']")).click();
        Thread.sleep(2000);
        searchForEmployee("Mob Essam Essam Last Last");*/


     /*   driver.findElement(By.xpath("//a/span[contains(text(),'Employees')]")).click();
        Thread.sleep(1000);
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//span[contains(text(),'Add New')]]"))).click();
        Thread.sleep(1000);
        WebElement textElement = driver.findElement(By.xpath("//p[text()='Documents']"));
        WebElement userIcon = textElement.findElement(By.xpath("ancestor::div[@class='p-timeline-event-content']/preceding-sibling::div[@class='p-timeline-event-separator']/i[@class='pi pi-user']"));
        userIcon.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(), 'Add Optional Document')]")).click();
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[.//span[text()='Choose']]")));
        driver.findElement(By.id("optional_document_name_0")).sendKeys("test");
        WebElement uploadButton  = driver.findElement(By.xpath("//span[text()='Choose']/following-sibling::input[@type='file']"));
        uploadButton.sendKeys(pdfPath + File.separator + "Bank Acc Confirmation.pdf");
*/

/*
        driver.findElement(By.xpath("//a/span[contains(text(),'Group')]")).click();
//        options.setCapability("webSocketUrl", true);
        driver.findElement(By.xpath("//span[contains(text(),'New Group')]")).click();
//        Thread.sleep(2000);
        selectDropdownByVisibleText(By.xpath("//label[text()='Select Branch']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),"Alex");
        selectDropdownByVisibleText(By.xpath("//label[text()='Select Lab']/following-sibling::div//div[contains(@class, 'inputwrapper')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),"Alex Lab 3");
*/

//        WebElement Day = driver.findElement(By.xpath("//label[text()='Monday']/preceding-sibling::div[contains(@class, 'p-checkbox')]"));
//        Day.click();
        /*// Handle multiple Departments
        String[] departments = {"Full time instructors","Training"};
        for (int i = 0; i < departments.length; i++) {
            String department = departments[i].trim();
            addDepartment(department);

            // Check if this is the last department
            if (i == departments.length - 1) {
                clickYesToClosePopup();
            }
        }*/
//        selectDropdownByVisibleText(By.xpath("//label[text()='Welcome Mail']/following-sibling::div//div[contains(@class, 'p-dropdown')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),"True");
        /*driver.findElement(By.xpath("//label[text()='Department']/following-sibling::div//button[@aria-label='Add']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//label[text()='IT']/preceding-sibling::div[@id='diplomaid']")).click();*/
        /*WebElement metaKeyWords = driver.findElement(By.xpath("//div[@id='metaKywords']//input"));
        metaKeyWords.sendKeys("title");
        actions.sendKeys(Keys.ENTER).perform();*/
        /*WebElement editorContainer = driver.findElement(By.className("p-editor-container"));

        // Locate the editable area within the Quill editor
        WebElement editableArea = editorContainer.findElement(By.className("ql-editor"));

        // Click to focus on the editor
        editableArea.click();

        // Input text into the editor
        editableArea.sendKeys("Your desired text goes here.");
        Thread.sleep(1000);*/
//        WebElement outsideElement = driver.findElement(By.tagName("body")); // Example, adjust if necessary
//        outsideElement.click();
//        Thread.sleep(2000);

//        WebElement metaKeyWords = driver.findElement(By.xpath("//div[@id='metaKywords']//input"));
//        metaKeyWords.sendKeys("title");
//        actions.sendKeys(Keys.ENTER).perform();
//        selectDropdownByVisibleText(By.xpath("//label[text()='Welcome Mail']/following-sibling::div//div[contains(@class, 'p-dropdown p-component p-inputwrapper w-12')]"),By.xpath("//ul[contains(@class,'p-dropdown-items')]"),"True");

//        driver.findElement(By.xpath("//div[@class='grid']//label[text()='HR']/preceding-sibling::div[@class='p-checkbox p-component']")).click();
//       driver.findElement(By.xpath("//b[text()='No']/following-sibling::div[contains(@class,'p-radiobutton p-component ml-2')]")).click();
        /*driver.findElement(By.xpath("//a/span[contains(text(),'Users')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//a/span[contains(text(),'Employees')]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//input[@class='p-inputtext p-component w-12']")).sendKeys("01141393650");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'Search')]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@title='Edit']")).click();
        Thread.sleep(1000);
        WebElement inputElement = driver.findElement(By.id("personal_image"));

        // Get the value of the input field
        String inputValue = inputElement.getAttribute("value");

        // Check if the input field is filled or empty
        if (inputValue.trim().isEmpty()) {
            System.out.println("The input field is empty.");
        } else {
            System.out.println("The input field is filled with: " + inputValue);
        }
        fillField(inputElement,imgPath+File.separator+"PNG.png");*/
//        Thread.sleep(3000);
//        driver.quit();
        /*driver.findElement(By.xpath("//button[@class='p-button p-component p-button-success mr-2']")).click();
        Thread.sleep(1000);
        WebElement textElement = driver.findElement(By.xpath("//p[text()='Job Info']"));
        WebElement userIcon = textElement.findElement(By.xpath("ancestor::div[@class='p-timeline-event-content']/preceding-sibling::div[@class='p-timeline-event-separator']/i[@class='pi pi-user']"));
        userIcon.click();
        Thread.sleep(1000);
        selectDropdownByVisibleText(By.xpath("//span[text()='Department']"),By.xpath("//div[@class='p-dropdown-items-wrapper']"),"HR");

        selectDropdownByVisibleText(By.id("job_role"),By.id("job_role_list"),"HR - Senior");
*/

/*
// Navigate upwards to the <i> element with the class "pi pi-user"
        WebElement userIcon = textElement.findElement(By.xpath("ancestor::div[@class='p-timeline-event-content']/preceding-sibling::div[@class='p-timeline-event-separator']/i[@class='pi pi-user']"));
// Click the user icon
        userIcon.click();
        Thread.sleep(2000);
        WebElement radioButtonDiv = driver.findElement(By.xpath("//input[@value='no_fingerprint_timesheet']/ancestor::div[contains(@class, 'p-radiobutton p-component')]"));
        radioButtonDiv.click();
        Thread.sleep(2000);
        driver.quit();
*/
    /*int fact = 6;
        for (int i = 6; i > 0 ; i--) {
            fact = fact*i;
        }*/


    }



    public static void searchForSpecificTicket(String diploma, String ticketType) {
        try {
            By diplomaDropdown = By.xpath("//span[text()='Diploma']/parent::div");
            selectDropdownByVisibleText(diplomaDropdown, diploma);

            By ticketTypeDropdown = By.xpath("//span[text()='Type']/parent::div");
            selectDropdownByVisibleText(ticketTypeDropdown, ticketType);

            System.out.println("Successfully selected diploma: " + diploma + " and ticket type: " + ticketType);
        } catch (Exception e) {
            throw new RuntimeException("Error in searching for specific ticket", e);
        }
        clickSearchButton();
    }

    public static boolean isFieldEmpty(WebElement element) {
        return element.getAttribute("value").isEmpty();
    }

    public static void fillField(WebElement element, String value) {
        if (isFieldEmpty(element)) {
            element.clear();
            element.sendKeys(value);
        }
    }

    protected static void selectDropdownByVisibleText(By elemLocator, String value) {
        try {
            // Wait until the dropdown is visible and click to open the dropdown
            WebElement dropdown = WAIT.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));
            dropdown.click();

            // Wait for the list of options to be loaded
            WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@role='listbox']")));

            int maxRetries = 10; // Maximum number of retries for scrolling
            int retryCount = 0;
            boolean found = false;
            List<WebElement> options = null;

            while (retryCount < maxRetries && !found) {
                // Check if the dropdown has a search box for filtering
                if (elemLocator.toString().contains("Employee name")) {
                    // If it's a search box dropdown, type the value to filter options
                    WebElement searchBox = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@role='searchbox']")));
                    searchBox.sendKeys(value);
                    options = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));
                } else {
                    // If no search box, load the options normally
                    options = WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@role='listbox']/li[not(contains(@class, 'p-dropdown-empty-message'))]")));
                }

                // Loop through the options to find the matching one
                for (WebElement option : options) {
                    String optionText = option.getText().trim();
                    if (optionText.equalsIgnoreCase(value.trim())) {
                        value = option.getText().trim();
                        // Scroll to the option and click it
                        JS.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", option);
                        WAIT.until(ExpectedConditions.elementToBeClickable(option)).click();
                        System.out.println("Scrolled to and selected: " + optionText);
                        found = true;
                        break;
                    }
                }

                // Scroll down to load more options if not found
                if (!found) {
                    JS.executeScript("arguments[0].scrollBy(0, 100);", driver); // Scroll down by 100 pixels
                    retryCount++;
                }
            }

            // Close the dropdown by sending ESC
            ACTIONS.sendKeys(Keys.ESCAPE).perform();

            // Now verify if the value is displayed in the selected area
            WebElement element = null;
            try {
                // Check for the text in the selected element using the main locator
                element = WAIT.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + value.trim() + "']")));
            } catch (Exception e) {
                // If not found, check for it inside the multiselect label div
                WebElement parentElement = (WebElement) elemLocator;
                element = SHORT_WAIT.until(ExpectedConditions.visibilityOf(parentElement.findElement(By.xpath("//div[@class='p-multiselect-label' and contains(normalize-space(), '" + value + "')]"))));
            }

            // Assert that the element is visible after the selection
            Assert.assertTrue(element != null && element.isDisplayed(), "Element with text '" + value + "' is not visible.");

        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout while waiting for dropdown elements: " + value, e);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Element not found: " + value, e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select dropdown value: " + value, e);
        }
    }

    public static void addDepartment(String department){
        driver.findElement(By.xpath("//label[text()='Department']/following-sibling::div//button[@aria-label='Add']")).click();
        shortWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//label[text()='" + department + "']/preceding-sibling::div[@id='diplomaid']")))).click();

    }
    public static void clickYesToClosePopup() {
        // Find the "Yes" button in the popup and click it to close
        WebElement yesButton = shortWait.until(ExpectedConditions.elementToBeClickable(
                driver.findElement(By.xpath("//span[text()='Yes']"))
        ));
        yesButton.click();
    }
    public static void searchForEmployee(String empName) {
            selectDropdownByVisibleText(By.xpath("//span[text()='Employee name']"), empName.trim());
        /*WebElement dropdown = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Employee name']")));
        dropdown.click();*/

       /* WebElement employeeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//li[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + empName.toLowerCase() + "']")));
        // Use translate() to handle case insensitivity
        *//*WebElement employeeOption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//li[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '"
                        + empName.toLowerCase().trim() + "']")));*/
//        employeeOption.click();
        clickSearchButton();
    }
    public static void clickSearchButton() {
        shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[contains(text(),'Search')]]"))).click();
    }

}
/*For extract all branches
* List<WebElement> locationOptions = driver.findElements(By.xpath("//ul[@id='job_location_list']/li"));
List<String> branchNames = new ArrayList<>();

for (WebElement option : locationOptions) {
    String branchName = option.getText().trim();
    if (!branchName.equalsIgnoreCase("All")) { // Exclude "All" if needed
        branchNames.add(branchName);
    }
}

System.out.println("Branches: " + branchNames);
// Output: [Maadi, Alex, Nasr1, Mohandseen, Maaditempo-s1, Alex  tempo-s4] */


/*For extract all approvals
* List<WebElement> approvalOptions = driver.findElements(By.xpath("//ul[@id='approval_level_list']/li//span[@data-pc-section='option']"));
List<String> approvalLevels = new ArrayList<>();

for (WebElement option : approvalOptions) {
    approvalLevels.add(option.getText().trim());
}

System.out.println("Approval Levels: " + approvalLevels);
// Output: [HR 1st level, HR 2nd level, CHR, ..., CEO]*/


/*For extratc Department
* List<WebElement> departmentOptions = driver.findElements(By.xpath("//ul[@id='pv_id_164_list']/li"));
List<String> departments = new ArrayList<>();

// Skip the first element ("All") if needed
for (int i = 1; i < departmentOptions.size(); i++) {
    departments.add(departmentOptions.get(i).getText().trim());
}

System.out.println("Departments: " + departments);
// Output: [C-level, Marketing, Sales, ..., Students affairs]*/

/* For C-level
* List<WebElement> jobRoleOptions = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> cLevelJobRoles = new ArrayList<>();

for (WebElement role : jobRoleOptions) {
    cLevelJobRoles.add(role.getText().trim());
}

System.out.println("C-Level Job Roles: " + cLevelJobRoles);
// Output: [Chief Executive Officer, Chief Operations Officer, ..., Chief Information Officer]*/


/*For Marketing
* List<WebElement> marketingJobRoles = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> roles = new ArrayList<>();

for (WebElement role : marketingJobRoles) {
    roles.add(role.getText().trim());
}

System.out.println("Marketing Job Roles: " + roles);
// Output: [Marketing - Internship, Marketing - Junior, ..., Marketing - Senior Director]*/

/*For Sales
* List<WebElement> salesJobRoles = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> roles = new ArrayList<>();

for (WebElement role : salesJobRoles) {
    roles.add(role.getText().trim());
}

System.out.println("Sales Job Roles: " + roles);
// Output: [Sales - Internship, Sales - Mid Senior, ..., Sales - Senior Director]*/

/*For front Office
* List<WebElement> frontOfficeRoles = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> roles = new ArrayList<>();

for (WebElement role : frontOfficeRoles) {
    roles.add(role.getText().trim());
}

System.out.println("Front-Office Job Roles: " + roles);
// Output: [Front office - Internship, Front office - Junior, ..., Front office - Senior Director]*/


/* For Hr Job Roles
* List<WebElement> hrJobRoles = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> roles = new ArrayList<>();

for (WebElement role : hrJobRoles) {
    roles.add(role.getText().trim());
}

System.out.println("HR Job Roles: " + roles);
// Output: [HR - Internship, HR - Junior, ..., HR - Senior Director] */

/*For Accountant
* List<WebElement> accountantRoles = driver.findElements(By.xpath("//ul[@id='job_role_list']/li"));
List<String> roles = new ArrayList<>();

for (WebElement role : accountantRoles) {
    roles.add(role.getText().trim());
}

System.out.println("Accountant Job Roles: " + roles);
// Output: [Accountant - Internship, Accountant - Junior, ..., Accountant - Senior Director]*/

//    public String getColumnData(String header) {
//        List<WebElement> headers = Hooks.driver.findElements(By.xpath("//table//thead//th"));
//        int columnIndex = -1;
//
//        for (int i = 0; i < headers.size(); i++) {
//            if (headers.get(i).getText().equalsIgnoreCase(header)) {
//                columnIndex = i + 1;
//                break;
//            }
//        }
//        if (columnIndex == -1) throw new IllegalArgumentException("Header not found: " + header);
//
//        List<WebElement> rows = Hooks.driver.findElements(By.xpath("//table//tbody/tr"));
//        if (!rows.isEmpty()) {
//            WebElement firstRow = rows.get(0);
//            return firstRow.findElement(By.xpath("./td[" + columnIndex + "]")).getText();
//        }
//        return "No rows found";
//    }