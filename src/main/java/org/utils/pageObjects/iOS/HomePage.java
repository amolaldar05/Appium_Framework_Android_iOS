package org.utils.pageObjects.iOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends IosActions {

    private final IOSDriver driver;

    // === Constructor ===
    public HomePage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
    }

    // === Private Locators (Static) ===
//    private final By alertViews = AppiumBy.accessibilityId("Alert Views");

    // === Public Actions ===

    public AlertPage clickStaticTextByName(String name) {
        String locatorString = "type == 'XCUIElementTypeStaticText' AND name == '" + name + "'";
        By locator = AppiumBy.iOSNsPredicateString(locatorString);

        // If element is not visible, scroll to find it
        if (driver.findElements(locator).isEmpty()) {
            iOSScrollAction((WebElement) locator,"down");  // will throw exception if not found
        }

        // Element is now present — click it
        driver.findElement(locator).click();

        return new AlertPage(driver);
    }
}
