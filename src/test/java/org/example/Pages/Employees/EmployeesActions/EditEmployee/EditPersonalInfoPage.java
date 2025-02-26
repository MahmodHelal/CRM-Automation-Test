/*
package org.example.Pages.Employees.EmployeesActions.EditEmployee;

import org.example.Pages.Employees.EmployementParent.EmployeeParent;
import org.example.StepsDef.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;

public class EditPersonalInfoPage extends EmployeeParent {
    public void setEmpName(String Name) {
        WebElement nameField = Hooks.driver.findElement(By.id("name"));
        if (isFieldEmpty(nameField)) {
            nameField.sendKeys(Name);
        } else {
            String firstName = nameField.getAttribute("value");
            nameField.sendKeys(Name+" "+Name);
        }
    }

    public void setMobileNumber(String phone) {
        WebElement phoneField = Hooks.driver.findElement(By.id("phone"));
        if (isFieldEmpty(phoneField)) {
            phoneField.sendKeys(phone);
        } else {
            System.out.println("Mobile number field is already filled. Skipping...");
        }
    }

    public void setAddress(String address) {
        WebElement addressField = Hooks.driver.findElement(By.id("address"));
        if (isFieldEmpty(addressField)) {
            addressField.sendKeys(address);
        } else {
            System.out.println("Address field is already filled. Skipping...");
        }
    }

    public void setPersonalEmail(String pMail) {
        WebElement emailField = Hooks.driver.findElement(By.id("email"));
        if (isFieldEmpty(emailField)) {
            emailField.sendKeys(pMail);
        } else {
            System.out.println("Personal email field is already filled. Skipping...");
        }
    }

    public void setWorkEmail(String wMail) {
        WebElement workEmailField = Hooks.driver.findElement(By.id("work_email"));
        if (isFieldEmpty(workEmailField)) {
            workEmailField.sendKeys(wMail);
        } else {
            System.out.println("Work email field is already filled. Skipping...");
        }
    }

    public void uploadPersonalPic(String picFileName) {
        WebElement picField = Hooks.driver.findElement(By.id("personal_image"));
        if (isFieldEmpty(picField)) {
            picField.sendKeys(imgPath + File.separator + picFileName);
        } else {
            System.out.println("Personal picture is already uploaded. Skipping...");
        }
    }

    public void setLinkedinURL(String linkedinURL) {
        WebElement linkedinField = Hooks.driver.findElement(By.id("linkedin"));
        if (isFieldEmpty(linkedinField)) {
            linkedinField.sendKeys(linkedinURL);
        } else {
            System.out.println("LinkedIn URL is already filled. Skipping...");
        }
    }

    public void uploadCvPdf(String CvFileName) {
        WebElement cvField = Hooks.driver.findElement(By.id("cv_image"));
        if (isFieldEmpty(cvField)) {
            cvField.sendKeys(pdfPath + File.separator + CvFileName);
        } else {
            System.out.println("CV PDF is already uploaded. Skipping...");
        }
    }

    public void uploadID(String idFileName) {
        WebElement idField = Hooks.driver.findElement(By.id("national_id_card"));
        if (isFieldEmpty(idField)) {
            idField.sendKeys(imgPath + File.separator + idFileName);
        } else {
            System.out.println("ID file is already uploaded. Skipping...");
        }
    }

    public void uploadBirth(String birthFileName) {
        WebElement birthField = Hooks.driver.findElement(By.id("birth_certificate"));
        if (isFieldEmpty(birthField)) {
            birthField.sendKeys(imgPath + File.separator + birthFileName);
        } else {
            System.out.println("Birth certificate is already uploaded. Skipping...");
        }
    }

}

*/
