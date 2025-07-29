package org.helpers;

public class PlatformManager {

    private static String platform;

    public static void detectPlatform(Class<?> testClass) {
        if (platform != null) return;  // Already detected once

        // 1. Based on class name
        String className = testClass.getSimpleName().toLowerCase();
        if (className.contains("ios")) {
            platform = "ios";
        } else if (className.contains("android")) {
            platform = "android";
        } else if (System.getProperty("platformFromXML") != null) {
            platform = System.getProperty("platformFromXML").toLowerCase();
        } else if (System.getProperty("platformName") != null) {
            platform = System.getProperty("platformName").toLowerCase();
        } else {
            platform = ConfigReader.getOrDefault("platformName", "android").toLowerCase();
        }
    }

    public static String getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("Platform not detected. Call detectPlatform() first.");
        }
        return platform;
    }

    public static void resetPlatform() {
        platform = null;  // for test re-use or parallel retries
    }
}
