package org.utils.pageObjects.iOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HomePage extends IosActions {

    private final IOSDriver driver;
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    // === Constructor ===
    public HomePage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        log.info("🏠 HomePage initialized.");
    }

    // === Public Actions ===
    public AlertPage clickStaticTextByName(String name) {
        String locatorString = "type == 'XCUIElementTypeStaticText' AND name == '" + name + "'";
        By locator = AppiumBy.iOSNsPredicateString(locatorString);
        log.debug("📍 Looking for static text element with name '{}'", name);

        if (driver.findElements(locator).isEmpty()) {
            log.info("🔄 Element '{}' not immediately visible — attempting to scroll.", name);
            iOSScrollAction((WebElement) locator, "down");  // May throw exception
        }

        driver.findElement(locator).click();
        log.info("✅ Clicked element with static text '{}'", name);

        return new AlertPage(driver);
    }
}
