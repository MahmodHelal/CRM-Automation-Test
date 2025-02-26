package org.example.Utils;

public class PageFactory {
    public static <T> T getInstance(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance: " + pageClass.getName(), e);
        }
    }
}
