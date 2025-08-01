package org.android.General_Store;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.BaseComponents.android.BaseTest_General_Store;
import org.helpers.LoggerUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageObjects.android.CartPage;
import org.pageObjects.android.ProductListPage;
import org.pageObjects.android.WebViewPage;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hybrid_App_Handle extends BaseTest_General_Store {

    SoftAssert softAssert;
    ProductListPage productListPage;
    CartPage cartPage;

    private static final Logger log = LoggerUtil.getLogger(Hybrid_App_Handle.class);

    @BeforeMethod
    public void setUp() {
        softAssert = new SoftAssert();
    }

    @Test
    public void handleHybridAppTest() throws InterruptedException {

        String productName = "Jordan 6 Rings";

        log.info("Starting Hybrid App Test for product: {}", productName);

        formPage.selectCountry("Argentina");
        log.info("Country selected: Argentina");

        driver.setClipboardText("Amol Aldar");
        String userName = driver.getClipboardText();
        formPage.enterName(userName);
        log.info("Entered user name from clipboard: {}", userName);

        formPage.selectGender("Male");
        log.info("Gender selected: Male");

        driver.hideKeyboard();

        Map<String, Object> result = formPage.clickShopBtn();
        productListPage = (ProductListPage) result.get("page");

        log.info("Navigating to product list...");
        log.info("Scrolling and adding product to cart: {}", productName);
        productListPage.scrollToProduct(productName);
        productListPage.addSingleProductToCart(productName);

        log.info("Navigating to cart page...");
        cartPage = productListPage.goToCartPage();

        log.info("Verifying product and calculating total price...");
        cartPage.verifyProductInCart(productName);

        double productPrice = cartPage.getSumProductPrices();
        double totalAmount = cartPage.getTotalAmount();
        log.debug("Sum of individual product prices: {}", productPrice);
        log.debug("Displayed total amount: {}", totalAmount);

        softAssert.assertEquals(productPrice, totalAmount, "Total price does not match sum of product prices");

        log.info("Accepting terms and conditions...");
        cartPage.clickTermsCheckbox();
        cartPage.acceptTermsAndConditions();

        log.info("Proceeding to WebView checkout...");
        WebViewPage webViewpage = cartPage.clickProceedButton();

        log.info("Switching to WEBVIEW context...");
        webViewpage.handleWebContext();

        log.info("Searching Google inside WebView...");
        webViewpage.searchGoogle();

        log.info("Switching back to NATIVE_APP context...");
        webViewpage.handleNativeContext();

        log.info("Navigating back to previous screen...");
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        Thread.sleep(3000);

        log.info("Asserting all verifications...");
        softAssert.assertAll();

        log.info("✅ Hybrid App Test completed successfully.");
    }
}
