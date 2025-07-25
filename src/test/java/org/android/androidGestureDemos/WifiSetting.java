package org.android.androidGestureDemos;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.android.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class WifiSetting extends BaseTest {
SoftAssert softAssert= new SoftAssert();
    @Test
    public void wifiSettingsTest() {
        // This method can be used to test Wi-Fi settings in the application
        // For example, checking if Wi-Fi is enabled, connecting to a network, etc.
        driver.findElement(AppiumBy.accessibilityId("Preference")).click();
        driver.findElement(AppiumBy.accessibilityId("3. Preference dependencies")).click();
       WebElement checkbox=driver.findElement(By.id("android:id/checkbox"));
        if(!checkbox.isSelected()){
            checkbox.click(); // Check the checkbox if it is not already selected
        } else {
            System.out.println("Checkbox is already selected.");
        }
        driver.findElement(By.xpath("(//android.widget.RelativeLayout)[2]")).click();
        String titleText=driver.findElement(By.id("android:id/alertTitle")).getText();
        softAssert.assertEquals(titleText, "WiFi settings","Title does not match expected value");
        driver.findElement(By.className("android.widget.EditText")).sendKeys("Amol");
        driver.findElement(By.id("android:id/button1")).click();
        softAssert.assertAll();




        // Add code to interact with the app and perform Wi-Fi related tests
    }

    // This class can be extended to include specific tests related to Wi-Fi settings
    // For example, methods to enable/disable Wi-Fi, check connection status, etc.

    // Example method to enable Wi-Fi
    public void enableWifi() {
        // Implementation for enabling Wi-Fi on the device
        System.out.println("Enabling Wi-Fi...");
        // Add code to interact with the app and enable Wi-Fi
    }

    // Example method to disable Wi-Fi
    public void disableWifi() {
        // Implementation for disabling Wi-Fi on the device
        System.out.println("Disabling Wi-Fi...");
        // Add code to interact with the app and disable Wi-Fi
    }
}
