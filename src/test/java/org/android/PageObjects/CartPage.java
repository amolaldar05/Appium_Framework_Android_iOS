package org.android.PageObjects;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.android.utils.AndroidActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends AndroidActions {
    WebDriverWait wait;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    public WebElement cartTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    public List<WebElement> productNamesInCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    public List<WebElement> productPricesInCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    public WebElement totalAmountLabel;

    @AndroidFindBy(className = "android.widget.CheckBox")
    public WebElement termsCheckbox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    public WebElement proceedButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/termsButton']")
    public WebElement termsAndCondLink;


    @AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
    public WebElement alertTitle;

    @AndroidFindBy(id = "android:id/button1")
    public WebElement closeTermsButton;



    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void waitTillTitleDispalyed(){
        // Wait for the cart title to be displayed
        wait.until(webDriver -> cartTitle.isDisplayed());
        wait.until(ExpectedConditions.attributeContains(cartTitle, "text", "Cart"));

    }

    public void verifyProductInCart(String productName) {
        // Verify if the specified product is present in the cart
        boolean isProductInCart = productNamesInCart.stream()
                .anyMatch(ele -> ele.getText().equalsIgnoreCase(productName));
        if (!isProductInCart) {
            throw new AssertionError("Product " + productName + " not found in cart");
        }
    }

    public double getSumProductPrices(){
        // Calculate the sum of all product prices in the cart
        double totalSum = productPricesInCart.stream()
                .mapToDouble(ele -> Double.parseDouble(ele.getText().replace("$", "")))
                .sum();
        System.out.println("Total Sum of Product Prices: $" + totalSum);
        return totalSum;
    }
    public void verifyProductPriceInCart(String productName, String expectedPrice) {
        // Verify if the price of the specified product matches the expected price
        for (int i = 0; i < productNamesInCart.size(); i++) {
            if (productNamesInCart.get(i).getText().equalsIgnoreCase(productName)) {
                String actualPrice = productPricesInCart.get(i).getText();
                if (!actualPrice.equals(expectedPrice)) {
                    throw new AssertionError("Price mismatch for " + productName + ": expected " + expectedPrice + ", but got " + actualPrice);
                }
                return;
            }
        }
        throw new AssertionError("Product " + productName + " not found in cart");
    }

    public double getTotalAmount() {
        // Get the total amount displayed in the cart
       String totalAmount=totalAmountLabel.getText();
       String totalPrice = totalAmount.replace("$", ""); // Remove dollar sign for comparison
         double totalAmt = Double.parseDouble(totalPrice);
         return totalAmt;
    }

    public void clickTermsCheckbox() {
        // Click on the terms and conditions button
        termsCheckbox.click();
    }
    public void acceptTermsAndConditions() {
        longPressGesture(termsAndCondLink);
        // Wait for the alert to be displayed and then accept terms
        wait.until(ExpectedConditions.visibilityOf(alertTitle));
        closeTermsButton.click(); // Close the alert
    }
    public void clickProceedButton() {
        // Click on the proceed button to continue
        proceedButton.click();
    }

    public int getProductCount() {
        return productNamesInCart.size();
    }

    public boolean isProductInCart(String product) {
        // Check if a specific product is present in the cart
        return productNamesInCart.stream()
                .anyMatch(ele -> ele.getText().equalsIgnoreCase(product));
    }
}
