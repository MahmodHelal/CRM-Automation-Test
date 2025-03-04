package org.example.Pages;

import org.example.StepsDef.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver = Hooks.getDriver();
    private final WebDriverWait WAIT = new WebDriverWait(driver, Duration.ofSeconds(15));
    private final WebDriverWait SHORT_WAIT = new WebDriverWait(driver, Duration.ofSeconds(5));
    private final Actions actions = new Actions(driver);

    // ✅ Locators
    private  final By USERNAME_FIELD = By.id("email");
    private  final By PASSWORD_FIELD = By.xpath("//input[@type='password']");
    private  final By LOGIN_BUTTON = By.xpath("//input[@type='submit']");
    private  final By ERROR_MESSAGE = By.xpath("//h2[contains(text(), 'Unauthorised')]");

    // ✅ Navigate to Login Page
    public LoginPage navigateToLoginPage(String url) {
        driver.get(url);
        waitForPageLoad();
        return this;
    }

    // ✅ Enter Username
    public void enterUsername(String username) {
        WebElement element = WAIT.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        element.clear();
        element.sendKeys(username);
    }

    // ✅ Enter Password
    public void enterPassword(String password) {
        WebElement element = WAIT.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        element.sendKeys(Keys.CONTROL + "a"); // Select all text
        element.sendKeys(Keys.BACK_SPACE); // Delete selected text
        element.sendKeys(password); // Enter new password
    }



    // ✅ Handles any potential overlay/popups
    private void handlePotentialOverlay() {
        try {
            actions.sendKeys(Keys.ESCAPE).perform();
//            System.out.println("🔹 Overlay dismissed.");
        } catch (Exception e) {
            System.out.println("🔹 No overlay detected.");
        }
    }

    // ✅ Submit Login Form
    public void submitLogin() {
        handlePotentialOverlay();
        WebElement loginBtn = WAIT.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        String currentURL = driver.getCurrentUrl();
        loginBtn.click();
        WAIT.until(ExpectedConditions.not(ExpectedConditions.urlToBe(currentURL)));
        waitForPageLoad();
    }
    public void submitInvalidLogin() {
        handlePotentialOverlay();
        WebElement loginBtn = WAIT.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        loginBtn.click();

    }
    public void dismissFailMessage() {
        WAIT.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        SHORT_WAIT.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'OK')]")))).click();
        System.out.println("🔹 Dismissed login error message.");
    }

    // ✅ Complete Login Flow (Helper Method)
    public boolean login(String username, String password) {
        try {
            enterUsername(username);
            enterPassword(password);
            submitLogin();
            return isLoginSuccessful();
        } catch (Exception e) {
            System.err.println("❌ Login failed: " + e.getMessage());
            return false;
        }
    }

    // ✅ Get Error Message (if login fails)
    public String getErrorMessage() {
        try {
            WebElement errorElement = WAIT.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
            return errorElement.getText().trim();
        } catch (TimeoutException e) {
            return "No error message found.";
        }
    }

    // ✅ Checks if login was successful
    public boolean isLoginSuccessful() {
        return WAIT.until(ExpectedConditions.urlContains("/dashboard"));
    }

    // ✅ Waits for Full Page Load
    private void waitForPageLoad() {
        WAIT.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }
}
