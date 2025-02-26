package org.example.StepsDef;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.Pages.HomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v130.network.Network;
import org.openqa.selenium.devtools.v130.network.model.ResponseReceived;
import org.openqa.selenium.devtools.v130.log.Log;
import org.testng.Assert;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class CheckRestAssuredStepDef {

    private final HomePage homePage = new HomePage();
    private DevTools devTools;
    private final List<String> failedRequests = new CopyOnWriteArrayList<>();
    private final List<String> slowRequests = new CopyOnWriteArrayList<>();
    private final List<String> consoleErrors = new CopyOnWriteArrayList<>();
    private static final long SLOW_THRESHOLD_MS = 1000;
    private boolean devToolsEnabled = false;

    public void ensureDevToolsSetup() {
        if (!devToolsEnabled) {
            setupDevTools();
            enableNetworkMonitoring();
            devToolsEnabled = true;
        }
    }

//    @When("I click on the {string} link")
//    public void iClickOnTheLink(String pageName) {
//        System.out.printf("\n===== Testing Page: %s =====\n", pageName);
//        getPageElement(pageName);
//    }

  /*  @Then("all network requests should return Success status for {string}")
    public void allNetworkRequestsShouldReturnSuccessStatus(String pageName) {
        List<String> apiUrls = getApiUrlsForPage(pageName);

        System.out.println("Checking network responses via RestAssured...");
        for (String url : apiUrls) {
            long startTime = System.currentTimeMillis();
            Response response = RestAssured.get(url);
            double duration = System.currentTimeMillis() - startTime;

            if (response.getStatusCode() != 200) {
                failedRequests.add(formatFailure("FAILED", url, response.getStatusCode(), duration));
            }
            if (duration >= SLOW_THRESHOLD_MS) {
                slowRequests.add(formatFailure("SLOW", url, response.getStatusCode(), duration));
            }
        }

        printResults(pageName);
        assertResults();
        closeDevToolsSession();
    }
    */


    private List<String> getApiUrlsForPage(String pageName) {
        Map<String, List<String>> apiUrls = new HashMap<>();

        // Define API endpoints per page
        apiUrls.put("Roles", List.of("/api/roles", "/api/permissions"));
        apiUrls.put("Tickets", List.of("/api/tickets", "/api/ticket-status"));

        // Return API URLs for the given page, or an empty list if not found
        return apiUrls.getOrDefault(pageName, Collections.emptyList());
    }

    private void setupDevTools() {
        devTools = ((ChromeDriver) Hooks.getDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Log.enable());
    }

    private void enableNetworkMonitoring() {
        devTools.addListener(Network.responseReceived(), this::onResponseReceived);
        devTools.addListener(Log.entryAdded(), logEntry -> {
            if ("SEVERE".equals(logEntry.getLevel().toString())) {
                consoleErrors.add("Console Error: " + logEntry.getText());
            }
        });
    }

    private void onResponseReceived(ResponseReceived responseReceived) {
        int statusCode = responseReceived.getResponse().getStatus();
        String url = responseReceived.getResponse().getUrl();
        double durationMs = (double) responseReceived.getResponse().getTiming()
                .flatMap(t -> Optional.ofNullable(t.getReceiveHeadersEnd()))
                .orElse(0.0);

        if (statusCode != 200) {
            failedRequests.add(formatFailure("FAILED", url, statusCode, durationMs));
        } else if (durationMs >= SLOW_THRESHOLD_MS) {
            slowRequests.add(formatFailure("SLOW", url, statusCode, durationMs));
        }
    }
    private String formatFailure(String type, String url, int statusCode, double durationMs) {
        return String.format("[%s] Status: %d | Time: %.2fms | URL: %s", type, statusCode, durationMs, (url != null ? url : "Unknown URL"));
    }


    private void printResults(String pageName) {
        System.out.printf("\n===== Results for %s =====\n", pageName);
        failedRequests.forEach(System.out::println);
        slowRequests.forEach(System.out::println);
        consoleErrors.forEach(System.out::println);
    }

    private void assertResults() {
        Assert.assertTrue(failedRequests.isEmpty(), "Failed requests: " + failedRequests);
        Assert.assertTrue(slowRequests.isEmpty(), "Slow requests (>1s): " + slowRequests);
    }

    private void closeDevToolsSession() {
        if (devTools != null) {
            try {
                devTools.close();
            } catch (Exception ignored) {}
        }
    }

    private void getPageElement(String pageName) {
         switch (pageName) {
            case "Roles" -> homePage.getRolesPage();
            case "Tickets" -> homePage.getTicketsPage();
            default -> throw new IllegalStateException("Unexpected page: " + pageName);
        };
    }
}
