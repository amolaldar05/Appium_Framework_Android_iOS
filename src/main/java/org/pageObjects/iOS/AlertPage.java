package org.pageObjects.iOS;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.actions.iOS.IosActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AlertPage extends IosActions {

    private final IOSDriver driver;
    private static final Logger log = LoggerFactory.getLogger(AlertPage.class);

    // === Locators ===
    private final String textField = "**/XCUIElementTypeTextField";
    private final String alertTitle = "**/XCUIElementTypeStaticText[`name == 'A Short Title Is Best'`]";
    private final String confirmCancel = "**/XCUIElementTypeStaticText[`name == 'Confirm / Cancel'`]";
    private final String messageText = "A message should be a short, complete sentence.";
    private final String confirmBtn = "**/XCUIElementTypeButton[`name == 'Confirm'`]";

    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`label == 'OK'`]")
    private WebElement okButtonEle;

    // === Constructor ===
    public AlertPage(IOSDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        log.info("🔧 AlertPage initialized");
    }

    // === Public Actions ===

    public void clickStaticTextByName(String name) {
        String locatorString = "type == 'XCUIElementTypeStaticText' AND name == '" + name + "'";
        By locator = AppiumBy.iOSNsPredicateString(locatorString);

        log.debug("📍 Trying to click static text by name: {}", name);

        if (driver.findElements(locator).isEmpty()) {
            log.info("🔄 Scrolling to find element with name '{}'", name);
            iOSScrollAction((WebElement) locator, "down"); // May throw exception
        }

        driver.findElement(locator).click();
        log.info("✅ Clicked static text with name '{}'", name);
    }

    public String enterText(String text) {
        log.info("⌨️ Entering text in alert text field: '{}'", text);
        driver.findElement(AppiumBy.iOSClassChain(textField)).sendKeys(text);
        String title = driver.findElement(AppiumBy.iOSClassChain(alertTitle)).getAttribute("value");
        log.debug("🧾 Alert title after entering text: '{}'", title);
        return title;
    }

    public void clickOk() {
        log.info("☑️ Clicking OK button in alert dialog");
        okButtonEle.click();
    }

    public void clickConfirmCancel() {
        log.info("⚠️ Clicking Confirm/Cancel alert");
        driver.findElement(AppiumBy.iOSClassChain(confirmCancel)).click();
    }

    public String getPopupMessage() {
        String message = driver.findElement(AppiumBy.accessibilityId(messageText)).getAttribute("value");
        log.debug("📩 Retrieved alert message text: '{}'", message);
        return message;
    }

    public void clickConfirm() {
        log.info("🟢 Clicking Confirm button in alert");
        driver.findElement(AppiumBy.iOSClassChain(confirmBtn)).click();
    }
}
