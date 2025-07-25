package org.android.General_Store;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.utils.BaseComponents.android.BaseTest_General_Store;
import org.utils.pageObjects.android.CartPage;
import org.utils.pageObjects.android.ProductListPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.Map;

import org.utils.actions.android.AndroidActions;

public class CheckoutTest extends BaseTest_General_Store {
    SoftAssert softAssert = new SoftAssert();
    AndroidActions androidActions;
    ProductListPage productListPage;
    CartPage cartPage;

    @BeforeMethod
    public void setupActivity() {
        ((JavascriptExecutor)driver).executeScript("mobile:startActivity", ImmutableMap.of("intent","com.androidsample.generalstore/com.androidsample.generalstore.SplashActivity"));
    }
    @Parameters({"name", "countryName", "gender"})
    @Test
    public void checkoutSingleProductTest(String name,String country,String gender) {
        String productName = "Jordan 6 Rings";
//        String countryName = "Argentina";
        driver.setClipboardText(name);
        formPage.selectCountry(country);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender(gender);
        Map<String, Object> result=formPage.clickShopBtn();
        productListPage=(ProductListPage) result.get("page");
        productListPage.waitTillTitleDisplayed();
        productListPage.scrollToProduct(productName);
        productListPage.addSingleProductToCart(productName);
        cartPage= productListPage.goToCartPage();
        // Wait for the Cart page to load
        cartPage.waitTillTitleDisplayed();
        cartPage.verifyProductInCart(productName);
        double productPrice=cartPage.getSumProductPrices();
        double totalAmount=cartPage.getTotalAmount();
        softAssert.assertEquals(productPrice, totalAmount, "Total price does not match the product price");
        cartPage.clickTermsCheckbox();
        cartPage.acceptTermsAndConditions();
        cartPage.clickProceedButton();
        softAssert.assertAll();


    }

    @Test
    public void checkoutMultiProductTest() throws InterruptedException {
        String[] productlist = {"Nike Blazer Mid '77", "Jordan 6 Rings", "PG 3"};
        List<String> productNamesList= List.of(productlist);
        String countryName = "Argentina";
        driver.setClipboardText("Amol Aldar");
        formPage.selectCountry(countryName);
        formPage.enterName(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        formPage.selectGender("Male");
        Map<String, Object> result=formPage.clickShopBtn();
        productListPage=(ProductListPage) result.get("page");
        productListPage.waitTillTitleDisplayed();
        productListPage.addMultipleProductsToCart(productNamesList);
        // Proceed to Cart
        cartPage=productListPage.goToCartPage();
        // Wait for Cart page to load
        cartPage.waitTillTitleDisplayed();
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
