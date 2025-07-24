package org.iOS.iosGesturesDemo;

import io.appium.java_client.AppiumBy;
import org.iOS.BaseComponent.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class IOSBasics extends BaseTest {
SoftAssert softAssert = new SoftAssert();

    @Test
    public void iOSBasicTest() {
        driver.findElement(AppiumBy.accessibilityId("Alert Views")).click();
      driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`label == 'Text Entry'`]")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeTextField")).sendKeys("Hello World");
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeCell")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == 'OK'`]")).click();
        driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == \"Confirm / Cancel\"`]")).click();
        String textMsg=driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeOther[`name == \"Horizontal scroll bar, 1 page\"`][3]")).getText();
        softAssert.assertEquals(textMsg,"A message should be a short, complete sentence");
        driver.findElement(AppiumBy.accessibilityId("Confirm")).click();
    }
}
