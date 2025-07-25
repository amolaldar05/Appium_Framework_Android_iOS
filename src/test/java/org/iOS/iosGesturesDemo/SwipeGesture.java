package org.iOS.iosGesturesDemo;

import io.appium.java_client.AppiumBy;
import org.utils.BaseComponents.iOS.BuiltInApps_BaseTest;
import org.utils.actions.iOS.IosActions;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SwipeGesture extends BuiltInApps_BaseTest {
    IosActions iosActions;
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public  void setupiOSActions() {
         iosActions = new IosActions(driver);
    }

    @Test
    public void swipeTest() {
       Map<String, Object> params = new HashMap<>();
       params.put("bundleId", "com.apple.mobileslideshow");
        driver.executeScript("mobile: launchApp", params);
        List<WebElement> images = driver.findElements(
                AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeImage' AND name BEGINSWITH 'Photo'")
        );
;
       System.out.println("Size====="+images.size());
       images.get(0).click();
       Iterator<WebElement> itr=images.iterator();
       while(itr.hasNext()){
           WebElement image=itr.next();
           //System.out.println("Image Name====="+image.getAttribute("name"));
           String time=driver.findElement(AppiumBy.iOSNsPredicateString("type=='XCUIElementTypeStaticText'")).getAttribute("value");
           System.out.println("Time======= "+time);
           iosActions.swipe("left");
       }
       driver.findElement(AppiumBy.accessibilityId("Close")).click();

    }
}
