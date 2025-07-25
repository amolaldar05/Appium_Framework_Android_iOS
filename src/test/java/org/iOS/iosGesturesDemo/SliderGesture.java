package org.iOS.iosGesturesDemo;

import io.appium.java_client.AppiumBy;
import org.utils.actions.iOS.IosActions;
import org.utils.BaseComponents.iOS.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;


public class SliderGesture extends BaseTest {
    IosActions iosActions;
    WebDriverWait wait;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setupiOSActions() {
        IosActions iosActions = new IosActions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test
    public void sliderTest() throws InterruptedException {
        driver.findElement(AppiumBy.iOSNsPredicateString("name == 'Sliders'")).click();
        wait.until(driver -> driver.findElement(AppiumBy.iOSNsPredicateString("name == 'Sliders' AND label == 'Sliders'")).isDisplayed());
        //WebElement slider=driver.findElement(AppiumBy.iOSNsPredicateString("value == '42%'"));
        WebElement slider=driver.findElement(AppiumBy.xpath("//XCUIElementTypeTable/XCUIElementTypeCell[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther"));

        slider.sendKeys("0%");
        Thread.sleep(2000);
        softAssert.assertEquals(slider.getAttribute("value"), "0%", "Slider value does not match expected value");
        slider.sendKeys("100%");
        Thread.sleep(2000);
        softAssert.assertEquals(slider.getAttribute("value"), "100%", "Slider value does not match expected value");


    }
}
