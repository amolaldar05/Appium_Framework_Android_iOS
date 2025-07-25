package org.android.androidGestureDemos;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.android.BaseTest;
import org.utils.actions.android.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SwipeGestureDemo extends BaseTest {

    SoftAssert softAssert = new SoftAssert();
    AndroidActions androidActions; // Declare but don't initialize here

    @BeforeClass
    public void setUpAndroidActions() {
        // Initialize AndroidActions AFTER driver is initialized by BaseTest
        androidActions = new AndroidActions(driver);
    }

    @Test
    public void swipeGestureTest() {
        // This method can be used to test swipe gestures in the application
        // For example, swiping left or right on an element to trigger a change
        System.out.println("👉 Performing swipe gesture test...");
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Gallery")).click();
        driver.findElement(AppiumBy.accessibilityId("1. Photos")).click();
        WebElement firstImage=driver.findElement(By.xpath("//android.widget.ImageView[1]")); // Click on the first image
        softAssert.assertEquals(firstImage.getAttribute("focusable"), "true", "First image is not focusable");

        // Example of performing a swipe gesture
        androidActions.swipeGesture(firstImage, "left"); // Swipe left for 1000 milliseconds
        softAssert.assertEquals(firstImage.getAttribute("focusable"), "false", "First image is not focusable");

        // Add assertions to verify the result of the swipe gesture
        softAssert.assertTrue(true, "Swipe gesture test completed successfully.");
        softAssert.assertAll();
    }
}
