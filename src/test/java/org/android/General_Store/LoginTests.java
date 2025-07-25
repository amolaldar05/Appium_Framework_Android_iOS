package org.android.General_Store;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.utils.BaseComponents.android.BaseTest_General_Store;
import org.utils.actions.enums.MobileKeyEvent;
import org.utils.helpers.MobileKeyActionUtils;
import org.utils.pageObjects.android.ProductListPage;

import java.util.Map;

public class LoginTests extends BaseTest_General_Store {
    SoftAssert softAssert = new SoftAssert();
    ProductListPage productListPage;

    @BeforeMethod
    public void setupActivity() {
    formPage.setupActivity();
    }

    @Test(dataProvider = "testData")
    public void validFormTest(String countryName, String name,String gender) {
        driver.setClipboardText(name); // Set the clipboard text to the provided name
        formPage.selectCountry(countryName);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender(gender);
        Map<String, Object> result = formPage.clickShopBtn();
        productListPage = (ProductListPage) result.get("page");
        productListPage.waitTillTitleDisplayed();
        productListPage.pressAndroidKey("back"); // Press home key to return to the home screen


    }

    @Test
    public void invalidFormTest() {
        Map<String, Object> result = formPage.clickShopBtn();
        String toastErrorMsg = result.get("toast").toString();
        softAssert.assertEquals(toastErrorMsg, "Please enter your name", "Error message does not match expected value");
    }

    @DataProvider(name = "testData")
    public Object[][] setTestData() {
        return new Object[][]{
                {"Argentina", "Amol Aldar","Female"},
                {"China", "John Doe","Male"},
                {"Angola", "Jane Smith","Female"}
        };
    }
}
