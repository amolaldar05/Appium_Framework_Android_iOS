package org.actions;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class AppiumUtils {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final Logger log = LoggerFactory.getLogger(AppiumUtils.class);

    // === Constructor ===
    public AppiumUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("Initialized AppiumUtils with WebDriver: {}", driver.getClass().getSimpleName());
    }

    // === Wait for Element to Be Visible ===
    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        log.debug("Waiting for visibility of element: {} with timeout: {}s", locator, timeoutSeconds);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean waitTillTitleDispalyed(WebElement titleElement, String expectedTitleText) {
        log.debug("Waiting for title '{}' to be displayed on element: {}", expectedTitleText, titleElement);
        try {
            boolean result = wait.until(ExpectedConditions.attributeContains(titleElement, "text", expectedTitleText));
            log.info("Title '{}' displayed: {}", expectedTitleText, result);
            return result;
        } catch (Exception e) {
            log.error("❌ Title '{}' was not displayed within timeout.", expectedTitleText);
            return false;
        }
    }

    public boolean waitTillTitleDisplayed(WebElement element, String expectedTitle) {
        return waitTillTitleDispalyed(element, expectedTitle);
    }

    // === Wait for Element to Be Clickable ===
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        log.debug("Waiting for element to be clickable: {} with timeout: {}s", locator, timeoutSeconds);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // === Wait for Presence of All Elements ===
    public List<WebElement> waitForAllElements(By locator, int timeoutSeconds) {
        log.debug("Waiting for presence of all elements located by: {}", locator);
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    // === Convert Price String to Double ===
    public double extractPriceAsDouble(String priceText) {
        log.debug("Extracting numeric price from: {}", priceText);
        String cleanText = priceText.replaceAll("[^\\d.]", "");
        try {
            double price = Double.parseDouble(cleanText);
            log.info("Extracted price: {}", price);
            return price;
        } catch (NumberFormatException e) {
            log.error("Unable to parse price from text: {}", priceText, e);
            throw new IllegalArgumentException("Unable to parse price from text: " + priceText, e);
        }
    }

    // === Scroll If Appium Mobile Driver ===
    public void scrollIfMobile(By locator) {
        log.debug("Attempting scroll on mobile if applicable for locator: {}", locator);
        if (driver instanceof AppiumDriver) {
            WebElement element = driver.findElement(locator);
            HashMap<String, Object> scrollParams = new HashMap<>();
            scrollParams.put("element", ((AppiumDriver) driver).getSessionId().toString());
            scrollParams.put("direction", "down");
            scrollParams.put("strategy", "accessibility id"); // Adjust if needed
            scrollParams.put("name", element.getAttribute("name"));

            ((JavascriptExecutor) driver).executeScript("mobile: scroll", scrollParams);
            log.info("Performed scroll on mobile for element: {}", element);
        } else {
            log.warn("Scroll skipped. Driver is not an AppiumDriver: {}", driver.getClass().getName());
        }
    }

    // === Sleep Helper ===
    public void pause(int seconds) {
        log.debug("Pausing for {} seconds", seconds);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            log.error("Thread interrupted during pause", e);
            Thread.currentThread().interrupt();
        }
    }
}
