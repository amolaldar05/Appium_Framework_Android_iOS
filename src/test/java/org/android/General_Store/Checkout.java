package org.android.General_Store;

import io.appium.java_client.AppiumBy;
import org.android.BaseComponent.BaseTest_General_Store;
import org.android.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class Checkout extends BaseTest_General_Store {
    public AndroidActions androidActions;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void setupAndroidActions() {
        // Initialize AndroidActions if needed, but generally not required in this class
        androidActions = new AndroidActions(driver);
    }

    @Test(enabled = false)
    public void fillFormSuccessfully() {
        String countryName = "India";
        driver.findElement(By.id("com.androidsample.generalstore:id/spinnerCountry")).click();
        WebElement country = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + countryName + "\"));"
                )
        );
        country.click();
        driver.setClipboardText("Amol Aldar");
        driver.findElement(By.className("android.widget.EditText")).sendKeys(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
    }

    @Test(enabled = false)
    public void errorMsgTest() {
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String errorMsg = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("text");
        softAssert.assertEquals(errorMsg, "Please enter your name", "Error message does not match expected value");
    }

    @Test(enabled = false)
    public void checkoutSingleProductTest() {
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
        //driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.androidsample.generalstore:id/productAddCart\").instance(2)")).click();

        List<WebElement> productNames = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        List<WebElement> addToCartButtons = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));
       /*for(int i=0;i<productNames.size();i++){
            if(productNames.get(i).getText().equalsIgnoreCase(productName)){
                System.out.println(productNames.get(i).getText()+": "+productPrices.get(i).getText());
                addToCartButtons.get(i).click();
                break;
            }
        }*/

         /*IntStream.range(0, productNames.size())
                .filter(i -> productNames.get(i).getText().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresent(i -> {
                    System.out.println(productNames.get(i).getText() + ": " + productPrices.get(i).getText());
                    driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
                });*/

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


    }

    @Test
    public void checkoutMultiProductTest() {
        String[] productlist = {"Nike Blazer Mid '77", "Jordan 6 Rings", "PG 3"};
        driver.setClipboardText("Amol Aldar");
        driver.findElement(By.className("android.widget.EditText")).sendKeys(driver.getClipboardText());
        driver.hideKeyboard(); // Hide the keyboard after entering text
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        //new UiSelector().text("Jordan 6 Rings")
        for (String product : productlist) {
            driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().text(\"" + product + "\"));"
                    )
            );
            //driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.androidsample.generalstore:id/productAddCart\").instance(2)")).click();
            List<WebElement> productNames = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
            List<WebElement> productPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
            List<WebElement> addToCartButtons = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));
            // Using Stream API to find the product and click the add to cart button
            productNames.stream()
                    .filter(ele -> ele.getText().equalsIgnoreCase(product))
                    .findFirst()
                    .ifPresent(ele -> {
                        int index = productNames.indexOf(ele); // Get index of matched product
                        System.out.println(productNames.get(index).getText() + ": " + productPrices.get(index).getText());
                        if(!addToCartButtons.get(index).isSelected() && addToCartButtons.size()>1) {
                            addToCartButtons.get(index).click();
                        }else {
                            driver.findElement(By.xpath("//android.widget.Button[@text='ADD TO CART']")).click();
                        }
                    });
        }

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toolBar_title = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
        wait.until(ExpectedConditions.attributeContains(toolBar_title, "text", "Cart"));

        List<WebElement> addedProducts=driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        softAssert.assertEquals(addedProducts.size(), productlist.length, "Number of products in cart does not match expected count");
        for (String product : productlist) {
            boolean productFound = addedProducts.stream()
                    .anyMatch(ele -> ele.getText().equalsIgnoreCase(product));
            softAssert.assertTrue(productFound, "Product " + product + " not found in cart");
        }
        List<WebElement> addedProductPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        double sum=0.00;
        for (WebElement priceElement : addedProductPrices) {
            String priceText = priceElement.getText().replace("$", ""); // Remove dollar sign for comparison
            double price = Double.parseDouble(priceText);
            sum += price; // Calculate total price
        }
        String addedProductInCart = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        String totalPrice = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        totalPrice = totalPrice.replace("$", ""); // Remove dollar sign for comparison
        double total = Double.parseDouble(totalPrice);
        softAssert.assertEquals(sum, total, "Total price does not match the product price");
        WebElement termsButton=driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        androidActions.longPressGesture(termsButton);
        // Wait for the terms and conditions dialog to appe
        WebElement termsAndCondTitle=driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle"));
        softAssert.assertEquals(termsAndCondTitle.getText(), "Terms Of Conditions", "Terms and Conditions title does not match expected value");
        WebElement closeBtn=driver.findElement(By.id("android:id/button1"));
        closeBtn.click();
        driver.findElement(By.className("android.widget.CheckBox")).click(); // Check the checkbox to accept terms
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
        softAssert.assertAll();
    }

    @Test(enabled=false)
    public void checkoutMultiProduct2_Test() {
        String[] productList = {"Nike Blazer Mid '77", "Jordan 6 Rings", "PG 3"};

        // Enter name in text field
        driver.setClipboardText("Amol Aldar");
        driver.findElement(By.className("android.widget.EditText")).sendKeys(driver.getClipboardText());
        driver.hideKeyboard();

        // Click "Let's Shop" button
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

        for (String product : productList) {
            try {
                // Scroll until product is visible
                driver.findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiScrollable(new UiSelector().scrollable(true))" +
                                        ".scrollIntoView(new UiSelector().text(\"" + product + "\"));"
                        )
                );

                // Locate product container (parent layout)
                WebElement productContainer = driver.findElement(
                        AppiumBy.androidUIAutomator(
                                "new UiSelector().text(\"" + product + "\").fromParent(new UiSelector().resourceId(\"com.androidsample.generalstore:id/productLayout\"))"
                        )
                );

                WebElement priceElement = productContainer.findElement(By.id("com.androidsample.generalstore:id/productPrice"));
                WebElement addToCartButton = productContainer.findElement(By.id("com.androidsample.generalstore:id/productAddCart"));

                String buttonText = addToCartButton.getText();

                System.out.println("📦 Found product: " + product + " | Price: " + priceElement.getText());

                // Only add if product not already added
                if (buttonText.equalsIgnoreCase("ADD TO CART")) {
                    addToCartButton.click();
                    System.out.println("🛒 Added product to cart: " + product);
                } else {
                    System.out.println("✅ Product already added: " + product);
                }
            } catch (Exception e) {
                System.out.println("❌ Could not find product: " + product);
            }
        }

        // Proceed to Cart
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        // Wait for Cart page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toolbarTitle = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
        wait.until(ExpectedConditions.textToBePresentInElement(toolbarTitle, "Cart"));

        // Validate products in Cart
        List<WebElement> addedProducts = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(addedProducts.size(), productList.length, "❌ Number of products in cart does not match expected count");

        for (String product : productList) {
            boolean productFound = addedProducts.stream()
                    .anyMatch(ele -> ele.getText().equalsIgnoreCase(product));
            softAssert.assertTrue(productFound, "❌ Product " + product + " not found in cart");
        }

        // Validate total price
        List<WebElement> addedProductPrices = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        double calculatedSum = 0.00;
        for (WebElement priceElement : addedProductPrices) {
            String priceText = priceElement.getText().replace("$", "");
            double price = Double.parseDouble(priceText);
            calculatedSum += price;
        }

        String totalAmountText = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        totalAmountText = totalAmountText.replace("$", "");
        double displayedTotal = Double.parseDouble(totalAmountText);

        softAssert.assertEquals(calculatedSum, displayedTotal, "❌ Total price does not match sum of product prices");

        // Perform long press on Terms button
        WebElement termsButton = driver.findElement(By.id("com.androidsample.generalstore:id/termsButton"));
        androidActions.longPressGesture(termsButton);

        // Validate Terms popup
        WebElement termsTitle = driver.findElement(By.id("com.androidsample.generalstore:id/alertTitle"));
        softAssert.assertEquals(termsTitle.getText(), "Terms Of Conditions", "❌ Terms and Conditions title mismatch");
        driver.findElement(By.id("android:id/button1")).click(); // Close the popup

        // Accept Terms and Proceed
        WebElement checkBox = driver.findElement(By.className("android.widget.CheckBox"));
        checkBox.click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();

        // Assert all
        softAssert.assertAll();
    }

}
