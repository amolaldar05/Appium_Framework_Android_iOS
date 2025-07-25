package org.utils.pageObjects.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.utils.actions.android.AndroidActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductListPage extends AndroidActions {
    WebDriverWait wait;
    WebDriver driver;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement productsTitle;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPrices;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCartButtons;
    @AndroidFindBy (xpath = "//android.widget.Button[@text='ADD TO CART']")
    private WebElement addToCartButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement cartButton;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean waitTillTitleDisplayed() {
        return waitTillTitleDispalyed(productsTitle, "Products");
    }



    public void scrollToProduct(String productName) {
        // Scroll to the specified product using UIAutomator2
        driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + productName + "\"));"
                )
        );
    }
    public void addSingleProductToCart(String productName) {
        productNames.stream()
                .filter(ele -> ele.getText().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresent(ele -> {
                    int index = productNames.indexOf(ele); // Get index of matched product
                    System.out.println(productNames.get(index).getText() + ": " + productPrices.get(index).getText());
                    addToCartButtons.get(index).click();
                });
    }

    public void addMultipleProductsToCart(List<String> productNamesList) throws InterruptedException {
        // Add multiple products to cart based on the provided list
        for (String productName : productNamesList) {
            scrollToProduct(productName);// Scroll to the product if not visible
            wait.until(ExpectedConditions.visibilityOfAllElements(productNames));
            productNames.stream()
                    .filter(ele -> ele.getText().equalsIgnoreCase(productName))
                    .findFirst()
                    .ifPresent(ele -> {
                        int index = productNames.indexOf(ele); // Get index of matched product
                        System.out.println(productNames.get(index).getText() + ": " + productPrices.get(index).getText());
                        if(!addToCartButtons.get(index).isSelected() && addToCartButtons.size()>1) {
                            addToCartButtons.get(index).click();
                        }else {
                            addToCartButton.click();
                        }
                    });
        }
    }



    public CartPage goToCartPage() {
        cartButton.click();
        return new CartPage(driver); // Return CartPage object for further actions
    }



    }






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


