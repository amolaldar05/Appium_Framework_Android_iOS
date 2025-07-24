package org.android.General_Store;

import io.appium.java_client.AppiumBy;
import org.android.BaseComponent.BaseTest_General_Store;
import org.android.PageObjects.CartPage;
import org.android.PageObjects.ProductListPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Map;

import org.android.utils.AndroidActions;

public class Checkout extends BaseTest_General_Store {
    SoftAssert softAssert = new SoftAssert();
    AndroidActions androidActions;
    ProductListPage productListPage;
    CartPage cartPage;

    @BeforeClass
    public void setAndroidActions() {
        androidActions = new AndroidActions(driver);
    }
    @Test(enabled = false)
    public void validFormTest() {
        String countryName = "Argentina";
        driver.setClipboardText("Amol Aldar");
        formPage.selectCountry(countryName);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender();
        formPage.clickShopBtn();

    }

    @Test()
    public void invalidFormTest() {
        Map<String, Object> result=formPage.clickShopBtn();
        String toastErrorMsg= result.get("toast").toString();
        softAssert.assertEquals(toastErrorMsg, "Please enter your name", "Error message does not match expected value");
    }

    @Test()
    public void checkoutSingleProductTest() {
        String productName = "Jordan 6 Rings";
        String countryName = "Argentina";
        driver.setClipboardText("Amol Aldar");
        formPage.selectCountry(countryName);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender();
        Map<String, Object> result=formPage.clickShopBtn();
        productListPage=(ProductListPage) result.get("page");
        productListPage.waitTillTitleDispalyed();
        productListPage.scrollToProduct(productName);
        productListPage.addSingleProductToCart(productName);
        cartPage= productListPage.goToCartPage();
        // Wait for the Cart page to load
        cartPage.waitTillTitleDispalyed();
        cartPage.verifyProductInCart(productName);
        double productPrice=cartPage.getSumProductPrices();
        double totalAmount=cartPage.getTotalAmount();
        softAssert.assertEquals(productPrice, totalAmount, "Total price does not match the product price");
        cartPage.clickTermsCheckbox();
        cartPage.acceptTermsAndConditions();
        cartPage.clickProceedButton();
        softAssert.assertAll();


    }

    @Test()
    public void checkoutMultiProductTest() throws InterruptedException {
        String[] productlist = {"Nike Blazer Mid '77", "Jordan 6 Rings", "PG 3"};
        List<String> productNamesList= List.of(productlist);
        String countryName = "Argentina";
        driver.setClipboardText("Amol Aldar");
        formPage.selectCountry(countryName);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender();
        Map<String, Object> result=formPage.clickShopBtn();
        productListPage=(ProductListPage) result.get("page");
        productListPage.waitTillTitleDispalyed();
        productListPage.addMultipleProductsToCart(productNamesList);
        // Proceed to Cart
        cartPage=productListPage.goToCartPage();
        // Wait for Cart page to load
        cartPage.waitTillTitleDispalyed();
        // Validate products in Cart
        softAssert.assertEquals(cartPage.getProductCount(), productlist.length, "Number of products in cart does not match expected count");
        for (String product : productlist) {
            boolean productFound = cartPage.isProductInCart(product);
            softAssert.assertTrue(productFound, "Product " + product + " not found in cart");
        }
        double sumPrices=cartPage.getSumProductPrices();
        // Validate total price
        double totalAmount = cartPage.getTotalAmount();
        softAssert.assertEquals(sumPrices, totalAmount, "Total price does not match sum of product prices");
        // Perform long press on Terms button
        cartPage.clickTermsCheckbox();
        cartPage.acceptTermsAndConditions();
        cartPage.clickProceedButton();
        softAssert.assertAll();

    }

}
