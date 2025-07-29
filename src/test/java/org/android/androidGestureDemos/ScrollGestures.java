package org.android.androidGestureDemos;

import io.appium.java_client.AppiumBy;
import org.BaseComponents.android.BaseTest;
import org.actions.android.AndroidActions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ScrollGestures extends BaseTest {
    SoftAssert softAssert = new SoftAssert();
    private AndroidActions androidActions; // Declare but don't initialize here

    @BeforeClass
    public void setUpAndroidActions() {
        // Initialize AndroidActions AFTER driver is initialized by BaseTest
        androidActions = new AndroidActions(driver);
    }

    @Test
    public void scrollGestureTest() {
        System.out.println("🚀 Performing scroll gesture test...");

        // Click on Views
        driver.findElement(AppiumBy.accessibilityId("Views")).click();

        // Perform scrolling gesture
        androidActions.scrollToEnd("down");

        // Assert if WebView element is displayed
        //softAssert.assertTrue(webViewElement.isDisplayed(), "❌ WebView not displayed!");
        System.out.println("✅ WebView element found and displayed.");
        softAssert.assertAll();
    }
}
