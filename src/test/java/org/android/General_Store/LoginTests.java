package org.android.General_Store;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.utils.BaseComponents.android.BaseTest_General_Store;
import org.utils.BaseComponents.crossPlatforms.BaseTest_CrossPlatform;
import org.utils.actions.enums.MobileKeyEvent;
import org.utils.helpers.LoggerUtil;
import org.utils.helpers.MobileKeyActionUtils;
import org.utils.helpers.TestDataProvider;
import org.utils.pageObjects.android.ProductListPage;
import org.slf4j.Logger;

import java.util.Map;

@Listeners(org.utils.listeners.TestListener.class)
public class LoginTests extends BaseTest_General_Store {
    private static final Logger log = LoggerUtil.getLogger(LoginTests.class);

    SoftAssert softAssert = new SoftAssert();
    ProductListPage productListPage;

    @BeforeMethod
    public void setupActivity() {
        log.info("Launching SplashActivity before test");
        formPage.setupActivity();
    }

    @Test( dataProvider = "loginData", dataProviderClass = TestDataProvider.class)
    public void validFormTest(Map<String, String> data) {
        log.info("Running validFormTest with data: {}", data);

        String name = data.get("name");
        String country = data.get("country");
        String gender = data.get("gender");

        log.debug("Setting clipboard name: {}", name);
        driver.setClipboardText(name);

        log.info("Selecting country: {}", country);
        formPage.selectCountry(country);

        log.info("Entering name from clipboard");
        formPage.enterName(driver.getClipboardText());

        log.debug("Hiding keyboard");
        driver.hideKeyboard();

        log.info("Selecting gender: {}", gender);
        formPage.selectGender(gender);

        Map<String, Object> result = formPage.clickShopBtn();

        if (result.containsKey("page")) {
            productListPage = (ProductListPage) result.get("page");
            log.info("Navigated to ProductListPage. Simulating Android back key press.");
            productListPage.pressAndroidKey("back");
        } else if (result.containsKey("toast")) {
            log.warn("Unexpected toast encountered: {}", result.get("toast"));
        }
    }

    @Test
    public void invalidFormTest() {
        String toastErrorMsg = null;
        log.info("Running invalidFormTest with missing name field");
        Map<String, Object> result = formPage.clickShopBtn();
        if (result.containsKey("toast")) {
             toastErrorMsg = result.get("toast").toString();
            log.info("Captured toast message: {}", toastErrorMsg);
        }else{
            log.warn("No toast message found, expected error for missing name field");
        }
        softAssert.assertEquals(toastErrorMsg, "Please enter your name", "Error message does not match expected value");
        softAssert.assertAll();
    }

    @DataProvider(name = "testData")
    public Object[][] setTestData() {
        log.debug("Providing test data for manual @DataProvider");
        return new Object[][]{
                {"Argentina", "Amol Aldar", "Female"},
                {"China", "John Doe", "Male"},
                {"Angola", "Jane Smith", "Female"}
        };
    }
}
