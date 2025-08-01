package org.pageObjects.android;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class WebViewPage {

    private WebDriver driver;
    private AndroidDriver androidDriver;

    private static final Logger log = LoggerFactory.getLogger(WebViewPage.class);

    @FindBy(name = "q")
    private WebElement googleSearchBox;

    public WebViewPage(WebDriver driver) {
        this.driver = driver;
        this.androidDriver = (AndroidDriver) driver;
        PageFactory.initElements(driver, this);
    }

    public void handleWebContext() {
        Set<String> contexts = androidDriver.getContextHandles();
        contexts.forEach(context -> log.info("Available context: {}", context));
        androidDriver.context("WEBVIEW_com.androidsample.generalstore");
    }

    public void handleNativeContext() {
        androidDriver.context("NATIVE_APP");
    }

    public void searchGoogle() {
        googleSearchBox.sendKeys("Appium features");
        googleSearchBox.sendKeys(Keys.ENTER);
        log.info("Search initiated in WebView.");
    }
}
