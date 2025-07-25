package org.utils.pageObjects.iOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AlertPage extends IosActions {

    private final IOSDriver driver;

    // === Private Locators ===

    private final String textField = "**/XCUIElementTypeTextField";
    private final String alertTitle ="**/XCUIElementTypeStaticText[`name == 'A Short Title Is Best'`]";
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`label == 'OK'`]")
    private WebElement okButtonEle;
    private final String confirmCancel = "**/XCUIElementTypeStaticText[`name == 'Confirm / Cancel'`]";
    private final String messageText = "A message should be a short, complete sentence.";
    private final String confirmBtn = "**/XCUIElementTypeButton[`name == 'Confirm'`]";

    // === Constructor ===
    public AlertPage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    // === Public Actions ===
    public void clickStaticTextByName(String name) {
        String locatorString = "type == 'XCUIElementTypeStaticText' AND name == '" + name + "'";
        driver.findElement(AppiumBy.iOSNsPredicateString(locatorString));
        By locator = AppiumBy.iOSNsPredicateString(locatorString);

        // If element is not visible, scroll to find it
        if (driver.findElements(locator).isEmpty()) {
            iOSScrollAction((WebElement) locator,"down");  // will throw exception if not found
        }

        // Element is now present — click it
        driver.findElement(locator).click();


    }
    public String enterText(String text) {
        driver.findElement(AppiumBy.iOSClassChain(textField)).sendKeys(text);
        return driver.findElement(AppiumBy.iOSClassChain(alertTitle)).getAttribute("value");
    }

    public void clickOk() {
        okButtonEle.click();
    }

    public void clickConfirmCancel() {
        driver.findElement(AppiumBy.iOSClassChain(confirmCancel)).click();
    }

    public String getPopupMessage() {
        return driver.findElement(AppiumBy.accessibilityId(messageText)).getAttribute("value");
    }

    public void clickConfirm() {
        driver.findElement(AppiumBy.iOSClassChain(confirmBtn)).click();
    }
}
