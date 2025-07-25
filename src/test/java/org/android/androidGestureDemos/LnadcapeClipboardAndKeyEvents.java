package org.android.androidGestureDemos;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.utils.BaseComponents.android.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class LnadcapeClipboardAndKeyEvents extends BaseTest {
SoftAssert softAssert= new SoftAssert();
    @Test
    public void wifiSettingsTest() {
        // This method can be used to test Wi-Fi settings in the application
        // For example, checking if Wi-Fi is enabled, connecting to a network, etc.
//        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
//        driver.findElement(AppiumBy.accessibilityId("3. Preference dependencies")).click();


    //        Activity activity = new Activity("io.appium.android.apis", "io.appium.android.apis.preference.PreferenceDependencies");
        // activity.start(); // Start the activity to ensure we are in the correct context but it is deprecated
        ((JavascriptExecutor)driver).executeScript("mobile:startActivity", ImmutableMap.of("intent","io.appium.android.apis/io.appium.android.apis.preference.PreferenceDependencies")); // Start the activity using Appium's mobile command

        WebElement checkbox=driver.findElement(By.id("android:id/checkbox"));
        if(!checkbox.isSelected()){
            checkbox.click(); // Check the checkbox if it is not already selected
        } else {
            System.out.println("Checkbox is already selected.");
        }
        DeviceRotation landscape = new DeviceRotation(0, 0, 90); // Rotate to landscape mode
        driver.rotate(landscape); // Apply the rotation to the driver
        System.out.println("Rotated to landscape mode.");
        driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
        String titleText=driver.findElement(By.id("android:id/alertTitle")).getText();
        softAssert.assertEquals(titleText, "WiFi settings","Title does not match expected value");
        driver.setClipboardText("Amol Aldar");
        driver.findElement(By.className("android.widget.EditText")).sendKeys(driver.getClipboardText());

        driver.findElement(By.id("android:id/button1")).click();

        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        driver.pressKey(new KeyEvent(AndroidKey.BACK)); // Press back key to return to the previous screen
        driver.pressKey(new KeyEvent(AndroidKey.HOME)); // Press home key to return to the home screen
        softAssert.assertAll();




        // Add code to interact with the app and perform Wi-Fi related tests
    }

}
