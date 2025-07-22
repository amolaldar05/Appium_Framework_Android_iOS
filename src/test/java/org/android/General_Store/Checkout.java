package org.android.General_Store;

import io.appium.java_client.AppiumBy;
import org.android.BaseComponent.BaseTest_General_Store;
import org.android.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.IntStream;

public class Checkout extends BaseTest_General_Store {
    public AndroidActions androidActions;
    SoftAssert softAssert = new SoftAssert();
    public void setupAndroidActions() {
        // Initialize AndroidActions if needed, but generally not required in this class
         androidActions = new AndroidActions(driver);
    }

    @Test
    public void fillFormSuccessfully(){
        String countryName="India";
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

    @Test
    public void errorMsgTest(){
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        String errorMsg = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("text");
        softAssert.assertEquals(errorMsg,"Please enter your name", "Error message does not match expected value");
    }

    @Test()
    public void checkoutSingleProductTest(){
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
        String addedProductInCart=driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();
        softAssert.assertEquals(addedProductInCart, productName, "Product in cart does not match expected product");
        String addedProductPrice=driver.findElement(By.id("com.androidsample.generalstore:id/productPrice")).getText();
        addedProductPrice=addedProductPrice.replace("$", ""); // Remove dollar sign for comparison
        double price = Double.parseDouble(addedProductPrice);
        String totalPrice = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();
        totalPrice = totalPrice.replace("$", ""); // Remove dollar sign for comparison
        double total = Double.parseDouble(totalPrice);
        softAssert.assertEquals(price, total, "Total price does not match the product price");
        driver.findElement(By.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();




    }

}
