/*
package org.example.Pages.Employees.EmployeesActions.EditEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.Map;

public class EditItDevicesPage extends EmployeeParent {

    public void setFingerCode(String code) {
    Hooks.driver.findElement(By.id("finger_print")).sendKeys(code);
}

    public void setItDevices(String device) {
        selectDropdownByVisibleText(By.id("it_device"), device.trim());
    }

       public void fillDeviceDetail(String imgFileName) {
           List<WebElement> headers = Hooks.driver.findElements(By.xpath("//div[@class='card-header']/h4"));

           Map<String, String> deviceSerialNumbers = Map.of(
                   "Mobile", "111111111",
                   "Laptop", "222222222",
                   "Pen tablet", "3333333333",
                   "Headphone", "444444444",
                   "Speaker", "5555555555",
                   "Charger", "6666666666",
                   "SIM card", "7777777777",
                   "Others", "1111222233"
           );
           for (WebElement headerElement : headers) {
               String header = headerElement.getText();
               WebElement section = Hooks.driver.findElement(By.xpath("//div[contains(@class, 'device-card') and .//h4[contains(text(),'" + header + "')]]"));
               if (deviceSerialNumbers.containsKey(header)) {
                   System.out.println("Header -> " + header);
                   checkAndFillDevice(section, deviceSerialNumbers.get(header), imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                   if (header.equals("SIM card")) {
                       WebElement workMobile = Hooks.driver.findElement(By.id("work_mobile_number"));
                       if (isFieldEmpty(workMobile)) {
                           workMobile.sendKeys("0103456789");
                       }
                   } else if (header.equals("Others")) {
                       WebElement deviceType = Hooks.driver.findElement(By.id("device_type"));
                       if (isFieldEmpty(deviceType)) {
                           deviceType.sendKeys("Custom Device Type");
                       }
                   }
               } else {
                   System.out.println("There is no matching device: " + header);
               }
           }
       }
           private void checkAndFillDevice(WebElement card, String serialNumber, String deviceImagePath, String receiptPath) {

               WebElement serialNumberField = card.findElement(By.xpath(".//label[contains(text(), 'Serial Number')]/following-sibling::div/input[@placeholder='Enter Serial Number']"));
               if (isFieldEmpty(serialNumberField)) {
                   serialNumberField.sendKeys(serialNumber);
                   WebElement deviceImageField = card.findElement(By.xpath(".//label[contains(text(), 'Upload Device Image')]/following-sibling::div/input[@type='file']"));
                   deviceImageField.sendKeys(deviceImagePath);

                   WebElement receiptField = card.findElement(By.xpath(".//label[contains(text(), 'Upload Receipt Acknowledgment')]/following-sibling::div/input[@type='file']"));
                   receiptField.sendKeys(receiptPath);
               } else {
                   System.out.println("Field already filled, skipping...");
               }
           }
       }
       */
/* for (WebElement headerElement : headers) {
            String header = headerElement.getText();
            WebElement section = Hooks.driver.findElement(By.xpath("//div[contains(@class, 'device-card') and .//h4[contains(text(),'" + header + "')]]"));
            try {
                switch (header) {
                    case "Mobile":
                        System.out.println("Header of mob ->"+ header);
                        checkAndFillDevice(section, "111111111", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "Laptop":
                        System.out.println("Header of lap ->"+ header);
                        checkAndFillDevice(section, "222222222", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "Pen tablet":
                        System.out.println("Header of tab ->"+ header);
                        checkAndFillDevice(section, "3333333333", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "Headphone":
                        System.out.println("Header of headPhone ->"+ header);

                        checkAndFillDevice(section, "444444444", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "Speaker":
                        System.out.println("Header of speaker ->"+ header);
                        checkAndFillDevice(section, "5555555555", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "Charger":
                        System.out.println("Header of charger ->"+ header);
                        checkAndFillDevice(section, "6666666666", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        break;
                    case "SIM card":
                        System.out.println("Header of SIM ->"+ header);
                        checkAndFillDevice(section, "7777777777", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        WebElement workMobile = Hooks.driver.findElement(By.id("work_mobile_number"));
                        workMobile.sendKeys("0103456789"); // Example mobile number
                        break;
                    case "Others":
                        System.out.println("Header of other ->"+ header);

                        checkAndFillDevice(section, "1111222233", imgPath + File.separator + imgFileName, imgPath + File.separator + imgFileName);
                        WebElement deviceType = Hooks.driver.findElement(By.id("device_type"));
                        deviceType.sendKeys("Custom Device Type");
                        break;
                    default:
                        System.out.println("There is no matching device: " + header);
                }
            } catch (NoSuchElementException e) {
                System.out.println("Element not found for device type: " + header);
            }

        }*//*





*/
