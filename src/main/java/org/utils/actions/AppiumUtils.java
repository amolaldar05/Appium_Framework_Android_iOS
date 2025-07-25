package org.utils.actions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class AppiumUtils {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // === Constructor ===
    public AppiumUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // === Wait for Element to Be Visible ===
    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public boolean waitTillTitleDispalyed(WebElement titleElement, String expectedTitleText) {
        try {
            return wait.until(ExpectedConditions.attributeContains(titleElement, "text", expectedTitleText));
        } catch (Exception e) {
            System.err.println("❌ Title '" + expectedTitleText + "' was not displayed within timeout.");
            return false;
        }
    }


    // === Wait for Element to Be Clickable ===
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // === Wait for Presence of All Elements ===
    public List<WebElement> waitForAllElements(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // === Convert Price String to Double ===
    public double extractPriceAsDouble(String priceText) {
        String cleanText = priceText.replaceAll("[^\\d.]", "");
        try {
            return Double.parseDouble(cleanText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unable to parse price from text: " + priceText, e);
        }
    }

    // === Scroll If Appium Mobile Driver ===
    public void scrollIfMobile(By locator) {
        if (driver instanceof AppiumDriver) {
            WebElement element = driver.findElement(locator);
            HashMap<String, Object> scrollParams = new HashMap<>();
            scrollParams.put("element", ((AppiumDriver) driver).getSessionId().toString());
            scrollParams.put("direction", "down");
            scrollParams.put("strategy", "accessibility id"); // Or "predicate string", depending on your locator type
            scrollParams.put("name", element.getAttribute("name"));
            ((JavascriptExecutor) driver).executeScript("mobile: scroll", scrollParams);
        }
    }

    // === Sleep Helper ===
    public void pause(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {}
    }
}
