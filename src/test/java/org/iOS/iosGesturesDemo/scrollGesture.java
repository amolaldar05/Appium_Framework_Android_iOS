package org.iOS.iosGesturesDemo;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.iOS.BaseTest;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class scrollGesture extends BaseTest {
    IosActions iosActions;
SoftAssert softAssert= new SoftAssert();
    WebDriverWait wait;
    @BeforeClass
    public void setupIosActions(){
        // This method can be used to initialize any specific iOS actions if needed
        // For example, initializing a custom action class for iOS gestures
         iosActions = new IosActions(driver);
         wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    @Test
    public void scrollTest() {
        WebElement webViewEle=driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Web View'`]"));
        iosActions.scrollToElementAndClick(webViewEle, "down");
        wait.until(driver -> driver.findElement(AppiumBy.iOSNsPredicateString("name == 'Web View' AND label == 'Web View'")).isDisplayed());
        driver.findElement(AppiumBy.iOSNsPredicateString("name == 'UIKitCatalog' AND label == 'UIKitCatalog' AND type == 'XCUIElementTypeButton'")).click();
        WebElement pickerViewEle=driver.findElement(AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'Picker View'`]"));
        pickerViewEle.click();
        WebElement picket_one=driver.findElement(AppiumBy.accessibilityId("Red color component value"));
        picket_one.sendKeys("70");
        WebElement picket_Two= driver.findElement(AppiumBy.accessibilityId("Green color component value"));
        picket_Two.sendKeys("240");
        WebElement picket_Three=driver.findElement(AppiumBy.accessibilityId("Blue color component value"));
        picket_Three.sendKeys("160");
        softAssert.assertEquals(picket_one.getText(),"70", "Picket one value does not match expected value");
        softAssert.assertEquals(picket_Two.getText(),"240", "Picket two value does not match expected value");
        softAssert.assertEquals(picket_Three.getText(),"160", "Picket three value does not match expected value");
        softAssert.assertAll();
    }

}
