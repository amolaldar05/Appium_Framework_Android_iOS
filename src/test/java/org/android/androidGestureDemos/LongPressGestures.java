package org.android.androidGestureDemos;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.android.BaseTest;
import org.utils.actions.android.AndroidActions;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LongPressGestures extends BaseTest {
    SoftAssert softAssert= new SoftAssert();
    private AndroidActions androidActions; // Declare but don't initialize here

    @BeforeClass
    public void setUpAndroidActions() {
        // Initialize AndroidActions AFTER driver is initialized by BaseTest
        androidActions = new AndroidActions(driver);
    }
    @Test
    public void longPressGestureTest() {
        // This method can be used to test long press gestures in the application
        // For example, long pressing on an element to trigger a context menu or action
        System.out.println("Performing long press gesture test...");
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Expandable Lists")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Custom Adapter")).click();
        WebElement ele=driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='People Names']"));
        // Ensure the element is present before performing long press
        androidActions.longPressGesture(ele);
        String sampleMenuText=driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Sample menu']")).getText();
        softAssert.assertEquals(sampleMenuText, "Sample menu", "Long press did not trigger the expected menu");
        softAssert.assertTrue(!sampleMenuText.isBlank());
        System.out.println("Long press gesture test completed successfully.");
        softAssert.assertAll();
    };
          }

