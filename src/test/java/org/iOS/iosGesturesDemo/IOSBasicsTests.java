package org.iOS.iosGesturesDemo;

import org.utils.BaseComponents.iOS.BaseTest;
import org.utils.pageObjects.iOS.AlertPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class IOSBasicsTests extends BaseTest {
    SoftAssert softAssert = new SoftAssert();

    @Test
    public void iOSBasicTest() {
        AlertPage alertPage=homePage.clickStaticTextByName("Alert Views");
        alertPage.clickStaticTextByName("Text Entry");
        String alertTitle=alertPage.enterText("This is a test message");
        // Verify the alert title
        softAssert.assertEquals(alertTitle, "A Short Title Is Best", "Alert title does not match expected value");
        alertPage.clickOk();
        alertPage.clickConfirmCancel();
        String popupMsg=alertPage.getPopupMessage();
        // Verify the popup message
        softAssert.assertEquals(popupMsg, "A message should be a short, complete sentence.", "Popup message does not match expected value");
        alertPage.clickConfirm();
        softAssert.assertAll();
    }
}
