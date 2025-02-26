/*
package org.example.StepsDef;

import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.Pages.HomePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v130.network.Network;
import org.openqa.selenium.devtools.v130.network.model.*;
import org.openqa.selenium.devtools.v130.log.Log;
import org.testng.Assert;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class CheckAllPagesStepDef {
    private final HomePage homePage = new HomePage();
    private DevTools devTools;
    private final ConcurrentHashMap<RequestId, Long> requestTimestamps = new ConcurrentHashMap<>();
    private final Set<RequestId> ongoingRequests = ConcurrentHashMap.newKeySet();
    private final List<String> failedRequests = new CopyOnWriteArrayList<>();
    private final List<String> slowRequests = new CopyOnWriteArrayList<>();
    private final List<String> consoleErrors = new CopyOnWriteArrayList<>();
    private String currentPageName;

    private static final long IDLE_TIME_MS = Long.parseLong(System.getProperty("idleTime", "3000"));
    private static final long NETWORK_TIMEOUT_MS = Long.parseLong(System.getProperty("networkTimeout", "20000"));
    private static final double SLOW_THRESHOLD_MS = Double.parseDouble(System.getProperty("slowThreshold", "1000.0"));

    private final AtomicLong lastNetworkActivityTime = new AtomicLong(System.currentTimeMillis());
    private final ConcurrentHashMap<RequestId, String> requestUrls = new ConcurrentHashMap<>();

    @BeforeStep
    public void ensureDevToolsSetup() {
        if (devTools == null) {
            setupDevTools();
            enableNetworkMonitoring();
        }
    }

    @When("I click on the {string} link")
    public void iClickOnTheLink(String pageName) {
        this.currentPageName = pageName;
         getPageElement(pageName);
        System.out.printf("\n===== Testing Page: %s =====\n", pageName);
        try {
            waitForNetworkIdle(IDLE_TIME_MS, NETWORK_TIMEOUT_MS);
        } catch (RuntimeException ex) {
            System.err.println("Warning: " + ex.getMessage());
            failedRequests.add("Network idle error: " + ex.getMessage());
        }
        printResults(pageName);
    }



    private void waitForNetworkIdle(long idleTimeMs, long timeoutMs) {
        // Implement the waitForNetworkIdle method
        throw new UnsupportedOperationException("waitForNetworkIdle method not implemented");
    }

    private void printResults(String pageName) {
        // Implement the printResults method
        throw new UnsupportedOperationException("printResults method not implemented");
    }

    private boolean isXhrOrFetch(ResourceType resourceType) {
        return resourceType == ResourceType.XHR || resourceType == ResourceType.FETCH;
    }

    @Then("all network requests should return Success status for {string}")
    public void allNetworkRequestsShouldReturnSuccessStatus(String pageName) {
        try {
            waitForNetworkIdle(IDLE_TIME_MS, NETWORK_TIMEOUT_MS);
        } catch (RuntimeException ex) {
            System.err.println("Warning: " + ex.getMessage());
            failedRequests.add("Network idle error: " + ex.getMessage());
        }
        printResults(pageName);
        assertResults();
        closeDevToolsSession();
    }

    private void setupDevTools() {

        if (Hooks.getDriver() == null) {
            throw new IllegalStateException("Driver is not initialized in Hooks.");
        }
        if (!(Hooks.getDriver() instanceof ChromeDriver)) {
            throw new IllegalStateException("Driver is not an instance of ChromeDriver.");
        }
        devTools = ((ChromeDriver) Hooks.getDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Log.enable());
        lastNetworkActivityTime.set(System.currentTimeMillis());
    }

    private void enableNetworkMonitoring() {
        clearMonitoringData();

        devTools.addListener(Network.requestWillBeSent(), request -> {
            if (isXhrOrFetch(request.getType().orElse(null)) && isApiRequest(request.getRequest().getUrl())) {
                RequestId requestId = request.getRequestId();
                ongoingRequests.add(requestId);
                requestTimestamps.put(requestId, System.nanoTime());
                requestUrls.put(requestId, request.getRequest().getUrl());
                lastNetworkActivityTime.set(System.currentTimeMillis());
                logNetworkRequest("REQ_START", request.getRequest().getUrl(), request.getRequest().getMethod(), requestId);
            }
        });

        devTools.addListener(Network.responseReceived(), response -> {
            processResponse(response.getResponse(), response.getRequestId());
        });

        devTools.addListener(Network.loadingFailed(), failed -> {
            failedRequests.add(formatFailure("FAILED", requestUrls.getOrDefault(failed.getRequestId(), "Unknown URL"), 0, 0));
            cleanupRequest(failed.getRequestId());
        });

        devTools.addListener(Log.entryAdded(), logEntry -> {
            if ("SEVERE".equals(logEntry.getLevel())) {
                consoleErrors.add("Browser Error: " + logEntry.getText());
            }
        });
    }
    private boolean isXhrOrFetch(String requestType) {
        return requestType != null && (requestType.equalsIgnoreCase("XHR") || requestType.equalsIgnoreCase("Fetch"));
    }
    private boolean isApiRequest(String url) {
        return url != null && url.contains("/api/"); // Adjust pattern as per your API structure
    }


    private void logNetworkRequest(String event, String url, String method, RequestId requestId) {
        System.out.printf("[%tT][%s] %s: %s | Method: %s | ID: %s\n",
                System.currentTimeMillis(), currentPageName, event, url, method, requestId);
    }

    private void processResponse(Response res, RequestId requestId) {
        int statusCode = res.getStatus();
        double durationMs = requestTimestamps.containsKey(requestId) ? (System.nanoTime() - requestTimestamps.get(requestId)) / 1_000_000.0 : 0;

        System.out.printf("[%tT] XHR_END: %.2fms | Status: %d | URL: %s%n",
                System.currentTimeMillis(), durationMs, statusCode, res.getUrl());

        if (statusCode >= 400) {
            failedRequests.add(formatFailure("FAILED", res.getUrl() != null ? res.getUrl() : "Unknown URL", statusCode, durationMs));
        } else if (durationMs >= SLOW_THRESHOLD_MS) {
            slowRequests.add(formatFailure("SLOW", res.getUrl() != null ? res.getUrl() : "Unknown URL", statusCode, durationMs));
        }


        cleanupRequest(requestId);
    }
    private String formatFailure(String type, String url, int statusCode, double durationMs) {
        return String.format("[%s] Status: %d | Time: %.2fms | URL: %s", type, statusCode, durationMs, (url != null ? url : "Unknown URL"));
    }


    private void cleanupRequest(RequestId requestId) {
        ongoingRequests.remove(requestId);
        requestTimestamps.remove(requestId);
        requestUrls.remove(requestId);
    }

    private void clearMonitoringData() {
        ongoingRequests.clear();
        requestTimestamps.clear();
        failedRequests.clear();
        slowRequests.clear();
        consoleErrors.clear();
    }

    private void assertResults() {
        Assert.assertTrue(failedRequests.isEmpty(), "Failed requests: " + failedRequests);
        Assert.assertTrue(slowRequests.isEmpty(), "Slow requests (>1s): " + slowRequests);
        Assert.assertTrue(ongoingRequests.isEmpty(), "Pending requests: " + ongoingRequests);
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
*/
