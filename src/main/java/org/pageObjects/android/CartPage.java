package org.pageObjects.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.actions.android.AndroidActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.helpers.LoggerUtil;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CartPage extends AndroidActions {
    WebDriverWait wait;
    WebDriver driver;
    private static final Logger log = LoggerUtil.getLogger(CartPage.class);
    @AndroidFindBy(id = "com.androidsample.generalstore:id/toolbar_title")
    private WebElement cartTitle;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productImage")
    private List<WebElement> productImagesInCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productName")
    private List<WebElement> productNamesInCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/productPrice")
    private List<WebElement> productPricesInCart;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/totalAmountLbl")
    private WebElement totalAmountLabel;

    @AndroidFindBy(className = "android.widget.CheckBox")
    private WebElement termsCheckbox;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnProceed")
    private WebElement proceedButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.androidsample.generalstore:id/termsButton']")
    private WebElement termsAndCondLink;

    @AndroidFindBy(id = "com.androidsample.generalstore:id/alertTitle")
    private WebElement alertTitle;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement closeTermsButton;

    @FindBy(css = "div.a4bIc")
    private WebElement googleSearchButton;





    public CartPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
       wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public double getTotalPriceByMatchingProductNames(List<String> expectedProductNames) {
        double totalSum = 0.0;
        Set<String> matchedProducts = new HashSet<>();
        String lastPageSource = "";

        while (matchedProducts.size() < expectedProductNames.size()) {
            List<WebElement> visibleNames = driver.findElements(
                    AppiumBy.id("com.androidsample.generalstore:id/productName"));
            List<WebElement> visiblePrices = driver.findElements(
                    AppiumBy.id("com.androidsample.generalstore:id/productPrice"));

            for (int i = 0; i < visibleNames.size(); i++) {
                String name = visibleNames.get(i).getText().trim();
                if (expectedProductNames.contains(name) && !matchedProducts.contains(name)) {
                    String priceStr = visiblePrices.get(i).getText().replace("$", "").trim();
                    try {
                        double price = Double.parseDouble(priceStr);
                        totalSum += price;
                        matchedProducts.add(name);
                        log.info("✔ Matched: {} → ${}", name, price);
                    } catch (NumberFormatException e) {
                        log.warn("⚠ Invalid price format for {}: {}", name, priceStr);
                    }
                }
            }

            if (matchedProducts.size() == expectedProductNames.size()) break;

            String currentPageSource = driver.getPageSource();
            if (currentPageSource.equals(lastPageSource)) {
                log.info("🔚 No more scrollable content. Exiting scroll loop.");
                break;
            }

            verticalScroll(0.7, 0.3);
            waitForShortDelay();
            lastPageSource = currentPageSource;
        }

        log.info("🧾 Final Total from Matched Products: ${}", totalSum);
        return totalSum;
    }


    public void verifyProductInCart(String productName) {
        waitTillTitleDisplayed(cartTitle, "Cart");

        boolean found = false;
        int maxScrolls = 3;
        int scrolls = 0;

        while (scrolls < maxScrolls) {
            for (WebElement element : productNamesInCart) {
                if (element.getText().equalsIgnoreCase(productName)) {
                    log.info("🛒 Product '{}' found in cart", productName);
                    return;
                }
            }
            scrolls++;
            verticalScroll(0.7, 0.3);
            waitForShortDelay();
        }

        log.error("❌ Product '{}' NOT found in cart after scrolling", productName);
        throw new AssertionError("Product '" + productName + "' NOT found in cart after scrolling");
    }




    public double getSumProductPrices() {
        Set<String> seenPrices = new HashSet<>();
        double totalSum = 0.0;
        int maxScrolls = 3;
        int scrolls = 0;
        int lastSeenCount = 0;

        while (scrolls < maxScrolls) {
            List<WebElement> visiblePrices = driver.findElements(
                    AppiumBy.id("com.androidsample.generalstore:id/productPrice"));

            for (WebElement priceElement : visiblePrices) {
                String rawPrice = priceElement.getText().replace("$", "").trim();

                if (!seenPrices.contains(rawPrice)) {
                    seenPrices.add(rawPrice);
                    try {
                        totalSum += Double.parseDouble(rawPrice);
                    } catch (NumberFormatException e) {
                        log.warn("⚠ Skipping invalid price format: {}", rawPrice);
                    }
                }
            }

            if (seenPrices.size() == lastSeenCount) {
                break;
            }

            lastSeenCount = seenPrices.size();
            verticalScroll(0.7, 0.3);
            waitForShortDelay();
            scrolls++;
        }

        log.info("✅ Total collected prices: {}", seenPrices.size());
        log.info("🧾 Total Sum: ${}", totalSum);
        return totalSum;
    }

    public double getTotalAmount() {
        String totalAmount = totalAmountLabel.getText();
        String totalPrice = totalAmount.replace("$", ""); // Remove dollar sign
        double totalAmt = Double.parseDouble(totalPrice);
        log.info("🔢 Total amount label text: {}", totalAmount);
        log.info("✅ Parsed total amount: ${}", totalAmt);
        return totalAmt;
    }

    public void clickTermsCheckbox() {
        termsCheckbox.click();
        log.info("☑ Terms and conditions checkbox clicked.");
    }

    public void acceptTermsAndConditions() {
        longPressGesture(termsAndCondLink);
        log.info("📜 Long press on 'Terms and Conditions' link.");
        wait.until(ExpectedConditions.visibilityOf(alertTitle));
        log.info("✅ Alert title visible: {}", alertTitle.getText());
        closeTermsButton.click();
        log.info("❌ Closed the Terms and Conditions alert.");
    }

    public WebViewPage clickProceedButton() {
        proceedButton.click();
        log.info("➡ Proceed button clicked.");
//        pressAndroidKey("back");
//        log.info("🔙 Android 'Back' key pressed after proceeding.");
        return new WebViewPage(driver);
    }

    public int getProductCountInCart() {
        waitTillTitleDisplayed(cartTitle, "Cart");
        Set<String> uniqueProductNames = new HashSet<>();
        int scrolls = 0;
        int maxScrolls = 5;

        log.info("🛒 Checking total unique product count in cart...");

        while (scrolls < maxScrolls) {
            for (WebElement productName : productNamesInCart) {
                String name = productName.getText();
                uniqueProductNames.add(name);
                log.debug("📦 Found product in view: {}", name);
            }
            scrolls++;
            verticalScroll(0.7, 0.3);
            log.info("🔄 Scrolled down for more products. Scroll #{}", scrolls);
            waitForShortDelay();
        }

        log.info("✅ Total unique products in cart: {}", uniqueProductNames.size());
        return uniqueProductNames.size();
    }

}
