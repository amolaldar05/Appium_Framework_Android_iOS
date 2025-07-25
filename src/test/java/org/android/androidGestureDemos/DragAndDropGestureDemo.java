package org.android.androidGestureDemos;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.android.BaseTest;
import org.utils.actions.android.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DragAndDropGestureDemo extends BaseTest {
    SoftAssert softAssert = new SoftAssert();
    AndroidActions androidActions; // Declare but don't initialize here

    @BeforeClass
    public void setUpAndroidActions() {
        // Initialize AndroidActions with the provided instance
        androidActions = new AndroidActions(driver);
    }

    @Test
    public void dragAndDropGestureTest() {
        driver.findElement(AppiumBy.accessibilityId("Views")).click();
        driver.findElement(AppiumBy.accessibilityId("Drag and Drop")).click();
        WebElement sourceElement = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_1"));
        WebElement targetElement = driver.findElement(AppiumBy.id("io.appium.android.apis:id/drag_dot_2"));
        androidActions.dragAndDropGesture(sourceElement, targetElement);
        String text=driver.findElement(By.id("io.appium.android.apis:id/drag_result_text")).getText();
        softAssert.assertEquals(text, "Dropped!", "Drag and drop action did not complete successfully");
        softAssert.assertAll();;
    }
}
