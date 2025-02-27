package org.example.Pages.Employees.EmployeesActions.CreateEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class ITDetailsPage extends EmployeeParent {

    private static final String WORK_MOBILE_NUMBER = "0103456789";
    private static final String CUSTOM_DEVICE_TYPE = "Custom Device Type";



    public WebElement getSectionHeader() {
        return Hooks.getDriver().findElement(By.xpath("//h3[contains(text(),'IT Details')]"));
    }
    // ---------------------------
    // ✅ IT Details Methods
    // ---------------------------

    /**
     * Sets a random 6-digit fingerprint code.
     */
    public void setFingerCode() throws InterruptedException {
        helperMethods.WAITForPopupToDisappear();
        int randomCode = random.nextInt(900000) + 100000; // Ensuring 6-digit random number
        WebElement fingerPrintField = WAIT.until(ExpectedConditions.presenceOfElementLocated(By.id("finger_print")));
        fingerPrintField.sendKeys(String.valueOf(randomCode));

        // ✅ Assertion to verify fingerprint code is set
        Assert.assertEquals(fingerPrintField.getAttribute("value"), String.valueOf(randomCode),
                "Fingerprint code was not set correctly.");
    }

    /**
     * Selects an IT device from the dropdown.
     * @param device The device name.
     */
    public void setItDevices(String device) {
        helperMethods.selectDropdownByVisibleText(By.id("it_device"), device.trim());
    }

    /**
     * Fills out details for all IT devices, including Serial Number, Device Image, and Receipt Upload.
     */
    private void fillDeviceDetails() {
        List<WebElement> headers = SHORT_WAIT.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'card')]/h4")));

        for (WebElement headerElement : headers) {
            String header = headerElement.getText().trim();
            WebElement section = driver.findElement(By.xpath(
                    "//div[h4[contains(text(), '"+ header + "')]]"));



            try {
                String serialNumber = generateSerialNumber(header);
                String formattedFileName = formatFileName(header);
                String filePath = PDF_PATH + File.separator + formattedFileName + ".pdf";
                String imagePath = IMG_PATH + File.separator + formattedFileName + ".png";

                fillDataForDevice(section, serialNumber, imagePath, filePath);

                // Device-specific adjustments
                applyDeviceSpecificSettings(header);

            } catch (NoSuchElementException e) {
                System.out.println("Element not found for device type: " + header);
            }
        }
    }
    public void processItDevices(String devicesCsv) {
        String[] devices = devicesCsv.split(",");
        for (String device : devices) {
            setItDevices(device.trim());
        }
        fillDeviceDetails();
    }

    /**
     * Applies settings specific to certain device types (e.g., SIM card or Custom Device).
     * @param header The header text for the device.
     */
    private void applyDeviceSpecificSettings(String header) {
        if (header.equalsIgnoreCase("SIM card")) {
            setWorkMobileNumber(WORK_MOBILE_NUMBER);
        } else if (header.equalsIgnoreCase("Others")) {
            setDeviceType(CUSTOM_DEVICE_TYPE);
        }
    }

    /**
     * Fills out IT device details including Serial Number, Image Upload, and Receipt Upload.
     *
     * @param card The device section container element.
     * @param serialNumber The generated serial number.
     * @param deviceImagePath Path to the device image.
     * @param receiptPath Path to the receipt document.
     */
    private void fillDataForDevice(WebElement card, String serialNumber, String deviceImagePath, String receiptPath) {
        WebElement serialNumberField = card.findElement(By.cssSelector("input[placeholder='Enter Serial Number']"));
        serialNumberField.sendKeys(serialNumber);
        Assert.assertEquals(serialNumberField.getAttribute("value"), serialNumber,
                "Serial number was not entered correctly.");

        WebElement deviceImageField = card.findElement(By.xpath(".//label[contains(text(), 'Upload Device Image')]/following::input"));
        deviceImageField.sendKeys(deviceImagePath);

        WebElement receiptField = card.findElement(By.xpath(".//label[contains(text(), 'Upload Receipt Acknowledgment')]/following::input[1]"));
        receiptField.sendKeys(receiptPath);
    }

    /**
     * Sets the work mobile number for the "SIM card" device.
     *
     * @param mobileNumber The mobile number to set.
     */
    private void setWorkMobileNumber(String mobileNumber) {
        WebElement workMobile = Hooks.getDriver().findElement(By.id("work_mobile_number"));
        workMobile.sendKeys(mobileNumber);
        Assert.assertEquals(workMobile.getAttribute("value"), mobileNumber,
                "Work mobile number was not set correctly.");
    }

    /**
     * Sets the custom device type for "Others" category.
     *
     * @param deviceTypeName The custom device type name.
     */
    private void setDeviceType(String deviceTypeName) {
        WebElement deviceType = Hooks.getDriver().findElement(By.id("device_type"));
        deviceType.sendKeys(deviceTypeName);
        Assert.assertEquals(deviceType.getAttribute("value"), deviceTypeName,
                "Custom device type was not set correctly.");
    }

    // ---------------------------
    // ✅ Verification Methods
    // ---------------------------

    /**
     * Verifies that all device details were saved correctly.
     */
    public void verifyDeviceDetails() {
        List<WebElement> headers = Hooks.getDriver().findElements(By.xpath("//div[@class='card-header']/h4"));

        for (WebElement headerElement : headers) {
            String header = headerElement.getText().trim();
            WebElement section = Hooks.getDriver().findElement(By.xpath(
                    "//div[contains(@class, 'device-card') and .//h4[contains(text(),'" + header + "')]]"));

            try {
                String expectedSerial = generateSerialNumber(header);
                WebElement serialNumberField = section.findElement(By.xpath(".//input[@placeholder='Enter Serial Number']"));

                Assert.assertEquals(serialNumberField.getAttribute("value"), expectedSerial,
                        "Serial number mismatch for device: " + header);

                verifyDeviceSpecificDetails(header);

            } catch (NoSuchElementException e) {
                System.out.println("Verification failed for device type: " + header);
            }
        }
    }

    /**
     * Verifies the device-specific details (e.g., mobile number or custom device type).
     * @param header The header text for the device.
     */
    private void verifyDeviceSpecificDetails(String header) {
        if (header.equalsIgnoreCase("SIM card")) {
            WebElement workMobile = Hooks.getDriver().findElement(By.id("work_mobile_number"));
            Assert.assertEquals(workMobile.getAttribute("value"), WORK_MOBILE_NUMBER,
                    "Work mobile number was not saved correctly.");
        } else if (header.equalsIgnoreCase("Others")) {
            WebElement deviceType = Hooks.getDriver().findElement(By.id("device_type"));
            Assert.assertEquals(deviceType.getAttribute("value"), CUSTOM_DEVICE_TYPE,
                    "Custom device type was not saved correctly.");
        }
    }

    // ---------------------------
    // ✅ Helper Methods
    // ---------------------------

    /**
     * Generates a serial number based on the device type.
     *
     * @param deviceType The type of device.
     * @return The generated serial number.
     */
    private String generateSerialNumber(String deviceType) {
        switch (deviceType.toLowerCase()) {
            case "mobile": return "111111111";
            case "laptop": return "222222222";
            case "pen tablet": return "333333333";
            case "headphone": return "444444444";
            case "speaker": return "555555555";
            case "charger": return "666666666";
            case "sim card": return "777777777";
            case "others": return "888888888";
            default: return "999999999";
        }
    }

    /**
     * Formats a filename based on the device type.
     *
     * @param deviceType The type of device.
     * @return The formatted filename.
     */
    private String formatFileName(String deviceType) {
        return deviceType.replaceAll("\\s+", "").toLowerCase();
    }
}
