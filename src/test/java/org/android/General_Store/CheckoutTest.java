package org.android.General_Store;

import org.helpers.RetryAnalyzer;
import org.slf4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.BaseComponents.android.BaseTest_General_Store;
import org.helpers.LoggerUtil;
import org.pageObjects.android.CartPage;
import org.pageObjects.android.ProductListPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Map;
import org.actions.android.AndroidActions;

public class CheckoutTest extends BaseTest_General_Store {

    SoftAssert softAssert = new SoftAssert();
    AndroidActions androidActions;
    ProductListPage productListPage;
    CartPage cartPage;
    private static final Logger log = LoggerUtil.getLogger(CheckoutTest.class);

    @BeforeMethod
    public void setupActivity() {
        formPage.setupActivity();
    }

    @Parameters({"name", "countryName", "gender"})
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void checkoutSingleProductTest(
            @Optional("John") String name,
            @Optional("Argentina") String countryName,
            @Optional("Male") String gender
    ) {
        String productName = "Jordan 6 Rings";
        log.info("=== Starting Single Product Checkout Test ===");
        try {
            log.debug("Setting clipboard with user name: {}", name);
            driver.setClipboardText(name);

            log.info("Filling out form: country={}, gender={}", countryName, gender);
            formPage.selectCountry(countryName);
            formPage.enterName(driver.getClipboardText());
            driver.hideKeyboard();
            formPage.selectGender(gender);
            log.info("Navigating to product list page...");
            Map<String, Object> result = formPage.clickShopBtn();
            productListPage = (ProductListPage) result.get("page");
            log.info("Scrolling and adding product to cart: {}", productName);
            productListPage.scrollToProduct(productName);
            productListPage.addSingleProductToCart(productName);
            log.info("Proceeding to cart...");
            cartPage = productListPage.goToCartPage();
            log.info("Verifying product in cart and price validations...");
            cartPage.verifyProductInCart(productName);
            double productPrice = cartPage.getSumProductPrices();
            double totalAmount = cartPage.getTotalAmount();
            log.debug("Calculated sum: {}, Displayed total: {}", productPrice, totalAmount);
            softAssert.assertEquals(productPrice, totalAmount, "Total price does not match product price");
            cartPage.clickTermsCheckbox();
            cartPage.acceptTermsAndConditions();
            cartPage.clickProceedButton();
            softAssert.assertAll();

            log.info(" Single product checkout test passed");

        } catch (Exception e) {
            log.error(" Single product checkout test failed due to exception: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Test
    public void checkoutMultiProductTest() throws InterruptedException {
        String[] productList = {"Nike Blazer Mid '77", "Jordan 6 Rings", "PG 3"};
        List<String> productNamesList = List.of(productList);
        String countryName = "Chile";

        log.info("=== Starting Multi-Product Checkout Test ===");

            try {
                log.debug("Setting clipboard with user name: Amol Aldar");
                driver.setClipboardText("Amol Aldar");

                log.info("Filling out form with country={}, gender=Male", countryName);
                formPage.selectCountry(countryName);
                formPage.enterName(driver.getClipboardText());
                driver.hideKeyboard();
                formPage.selectGender("Male");

                log.info("Navigating to product list page...");
                Map<String, Object> result = formPage.clickShopBtn();
                productListPage = (ProductListPage) result.get("page");
                log.info("Adding multiple products to cart: {}", productNamesList);
                productListPage.addMultipleProductsToCart(productNamesList);
                log.info("Proceeding to cart...");
                cartPage = productListPage.goToCartPage();
                log.debug("Cart page opened, verifying product count and contents...");

                log.info("Validating product count and contents...");
                softAssert.assertEquals(cartPage.getProductCountInCart(), productList.length,
                        "Product count mismatch in cart");
                double sumPrices=cartPage.getTotalPriceByMatchingProductNames(productNamesList);
                log.debug("Sum of product prices calculated: {}", sumPrices);
                double totalAmount = cartPage.getTotalAmount();
                log.debug("Calculated sum: {}, Displayed total: {}", sumPrices, totalAmount);
                softAssert.assertEquals(sumPrices, totalAmount,
                        "Total price does not match sum of product prices");

                cartPage.clickTermsCheckbox();
                cartPage.acceptTermsAndConditions();
                cartPage.clickProceedButton();
                softAssert.assertAll();

                log.info(" Multi-product checkout test passed");

            } catch (Exception e) {
                log.error(" Multi-product checkout test failed due to exception: {}", e.getMessage(), e);
                throw e;
            }
        }
    }

