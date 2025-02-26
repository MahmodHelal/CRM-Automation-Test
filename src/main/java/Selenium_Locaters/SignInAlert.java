package Selenium_Locaters;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SignInAlert {

    private static WebDriver driver;

    public static void main(String[] args) {
        // Initialize the WebDriver
        setupDriver();

        // Credentials for authentication
        String username = "admin";
        String password = "admin";

        // Base URL without protocol
        String baseUrl = "the-internet.herokuapp.com/basic_auth";

        authenticateAndNavigate(baseUrl, username, password);

        // Validate the authentication result
        validateAuthentication();

        // Close the WebDriver
        tearDownDriver();
    }

    /**
     * Sets up the WebDriver instance.
     */
    private static void setupDriver() {
        driver = new FirefoxDriver();
    }

    /**
     * Navigates to the specified URL with basic authentication credentials.
     *
     * @param baseUrl  The base URL to navigate to.
     * @param username The username for authentication.
     * @param password The password for authentication.
     */
    private static void authenticateAndNavigate(String baseUrl, String username, String password) {
        // Construct the authenticated URL
        String authenticatedUrl = "https://" + username + ":" + password + "@" + baseUrl;

        // Navigate to the authenticated URL
        driver.get(authenticatedUrl);
    }

    /**
     * Validates if authentication was successful by checking the page content.
     */
    private static void validateAuthentication() {
        String successMessage = "Congratulations! You must have the proper credentials.";
        if (driver.getPageSource().contains(successMessage)) {
            System.out.println("Authentication successful!");
        } else {
            System.out.println("Authentication failed!");
        }
    }

    /**
     * Closes the WebDriver instance.
     */
    private static void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
