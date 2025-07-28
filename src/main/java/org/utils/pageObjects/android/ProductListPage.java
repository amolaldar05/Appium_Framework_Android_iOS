package org.utils.pageObjects.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.utils.actions.android.AndroidActions;
import org.utils.pageObjects.android.CartPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductListPage extends AndroidActions {
    private static final Logger log = LoggerFactory.getLogger(ProductListPage.class);

    private final WebDriverWait wait;
    private final AndroidDriver driver;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement productsTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNames;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPrices;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productAddCart")
    private List<WebElement> addToCartButtons;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='ADD TO CART']")
    private WebElement addToCartButton;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/appbar_btn_cart")
    private WebElement cartButton;

    public ProductListPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addSingleProductToCart(String productName) {
        waitTillTitleDisplayed(productsTitle, "Products");
        productNames.stream()
                .filter(ele -> ele.getText().equalsIgnoreCase(productName))
                .findFirst()
                .ifPresent(ele -> {
                    int index = productNames.indexOf(ele); // Get index of matched product
                    String name = productNames.get(index).getText();
                    String price = productPrices.get(index).getText();
                    log.info("🛒 Adding product: {} | Price: {}", name, price);
                    addToCartButtons.get(index).click();
                });
    }

    public void addMultipleProductsToCart(List<String> expectedProductNames) {
        waitTillTitleDisplayed(productsTitle, "Products");
        Set<String> addedProductNames = new HashSet<>();
        String lastPageSource = "";

        log.info("🛒 Attempting to add multiple products to cart: {}", expectedProductNames);

        while (addedProductNames.size() < expectedProductNames.size()) {
            log.debug("🔄 Scrolling through product list...");
            for (int i = 0; i < productNames.size(); i++) {
                String name = productNames.get(i).getText().trim();

                if (expectedProductNames.contains(name) && !addedProductNames.contains(name)) {
                    log.info("🛍 Adding to cart: {}", name);

                    if (i < addToCartButtons.size()) {
                        addToCartButtons.get(i).click();
                        addedProductNames.add(name);
                        log.debug("✅ Added: {}", name);
                    } else {
                        log.warn("⚠ Couldn't find Add to Cart button for: {}", name);
                    }
                }
            }

            if (addedProductNames.size() == expectedProductNames.size()) {
                log.info("🎯 All expected products added.");
                break;
            }

            String currentPageSource = driver.getPageSource();
            if (currentPageSource.equals(lastPageSource)) {
                log.warn("🔚 Reached end of product list. Stopping scroll.");
                break;
            }

            verticalScroll(0.7, 0.3);
            waitForShortDelay();
            lastPageSource = currentPageSource;
        }

        log.info("✅ Products successfully added to cart: {}", addedProductNames);
    }

    public CartPage goToCartPage() {
        log.info("🛒 Navigating to cart page...");
        cartButton.click();
        return new CartPage(driver);
    }
}
