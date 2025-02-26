package org.example.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages scenario-specific data storage for test execution.
 * This class acts as a key-value store, allowing tests to share data
 * between steps in a scenario.
 */
public class ScenarioContext {
    // Stores scenario-specific data
    private final Map<String, Object> context;

    /**
     * Initializes a new ScenarioContext with an empty data map.
     */
    public ScenarioContext() {
        context = new HashMap<>();
    }

    /**
     * Stores a key-value pair in the scenario context.
     *
     * @param key   A unique identifier for the stored data.
     * @param value The data to be stored (can be any object type).
     */
    public void setContext(String key, Object value) {
        context.put(key, value);
    }

    /**
     * Retrieves the value associated with the given key.
     *
     * @param key The identifier for the stored data.
     * @return The corresponding value, or null if the key is not found.
     */
    public Object getContext(String key) {
        return context.get(key);
    }

    /**
     * Checks if a key exists in the scenario context.
     *
     * @param key The identifier to check.
     * @return True if the key exists, false otherwise.
     */
    public boolean containsKey(String key) {
        return context.containsKey(key);
    }

    /**
     * Clears all stored data in the context.
     * This is useful for resetting state between test scenarios.
     */
    public void clearContext() {
        context.clear();
    }
}
