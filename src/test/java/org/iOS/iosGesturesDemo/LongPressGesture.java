package org.iOS.iosGesturesDemo;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.iOS.BaseTest;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LongPressGesture extends BaseTest {
    IosActions iosActions;
SoftAssert softAssert= new SoftAssert();
    @BeforeClass
    public void setupIosActions(){
        // This method can be used to initialize any specific iOS actions if needed
        // For example, initializing a custom action class for iOS gestures
         iosActions = new IosActions(driver);
    }
    @Test
    public void longPressTest() {
        driver.findElement(AppiumBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[13]/XCUIElementTypeOther[1]/XCUIElementTypeOther")).click();
        WebElement ele=driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeButton[`label == 'Increment'`][3]"));
        String text=driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Steppers'`]")).getText();
        softAssert.assertEquals(text,"Steppers", "Steppers text does not match expected value");
        iosActions.touchAndHold(ele,5000);
        softAssert.assertAll();
    }
}
