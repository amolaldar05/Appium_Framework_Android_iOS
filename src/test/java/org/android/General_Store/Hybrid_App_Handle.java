package org.android.General_Store;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.android.BaseComponent.BaseTest_General_Store;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Hybrid_App_Handle extends BaseTest_General_Store {
SoftAssert softAssert = new SoftAssert();
    @Test
    public void handleHybridAppTest() throws InterruptedException {
//        Activity activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.AllProductsActivity");
//
//        ((JavascriptExecutor)driver).executeScript("mobile:startActivity", ImmutableMap.of("intent","com.androidsample.generalstore/com.androidsample.generalstore.AllProductsActivity")); // Start the activity using Appium's mobile command
        String productName = "Jordan 6 Rings";
        driver.setClipboardText("Amol Aldar");
        driver.findElement(By.className("android.widget.EditText")).sendKeys(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        //new UiSelector().text("Jordan 6 Rings")

        driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + productName + "\"));"
                )
        );

        List<WebElement> productNames = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        List<WebElement> addToCartButtons = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));


        // Using Stream API to find the product and click the add to cart button

        productNames.stream()
                .filter(ele -> ele.getText().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresent(ele -> {
                    int index = productNames.indexOf(ele); // Get index of matched product
                    System.out.println(productNames.get(index).getText() + ": " + productPrices.get(index).getText());
                    addToCartButtons.get(index).click();
                });

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toolBar_title = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
        // String toolBar_title_text=driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).getAttribute("text");
        wait.until(ExpectedConditions.attributeContains(toolBar_title, "text", "Cart"));

        String addedProductInCart = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        softAssert.assertEquals(addedProductInCart, productName, "Product in cart does not match expected product");
        String addedProductPrice = driver.findElement(By.id("com.androidsample.generalstore:id/productPrice")).getText();
        addedProductPrice = addedProductPrice.replace("$", ""); // Remove dollar sign for comparison
        double price = Double.parseDouble(addedProductPrice);
        String totalPrice = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        totalPrice = totalPrice.replace("$", ""); // Remove dollar sign for comparison
        double total = Double.parseDouble(totalPrice);
        softAssert.assertEquals(price, total, "Total price does not match the product price");
        driver.findElement(By.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        Thread.sleep(5000);
        Set<String> contexts=driver.getContextHandles();
        contexts.forEach(context -> System.out.println("Available context: " + context));
        driver.context("WEBVIEW_com.androidsample.generalstore");
        //String currentContext = driver.getContext();
        driver.findElement(By.name("q")).sendKeys("Appium");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        driver.context("NATIVE_APP"); // Switch back to native app context
        Thread.sleep(3000);

        System.out.println("Handling hybrid app interactions...");
    }
}
