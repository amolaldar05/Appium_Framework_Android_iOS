package org.android.androidGestureDemos;

import org.android.BaseComponent.BaseTest_Chrome_Browser_Native_App;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

public class ChromeBrowserNativeApp extends BaseTest_Chrome_Browser_Native_App{

    @Test
    public void browserTest(){
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.cssSelector(".navbar-toggler-icon")).click();
        List<WebElement> menuOptions=driver.findElements(By.cssSelector("ul li a.nav-link"));
        menuOptions.stream().filter(name->name.getText().equalsIgnoreCase("Products")).findFirst().ifPresent(WebElement::click);
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500);"); // Scroll down to load more products
        List<WebElement> productList=driver.findElements(By.cssSelector(".media-body.order-2.order-lg-1 a"));
        productList.stream().filter(name->name.getText().equalsIgnoreCase("Devops")).findFirst().ifPresent(WebElement::click);



    }
}
