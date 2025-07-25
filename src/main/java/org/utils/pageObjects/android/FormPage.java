package org.utils.pageObjects.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.utils.actions.android.AndroidActions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class FormPage extends AndroidActions {
    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    private WebElement countryDropdown;
    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement nameField;

    @AndroidFindBy(className = "android.widget.RadioButton")
    private WebElement radioButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioMale")
    private WebElement maleRadioButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    private WebElement femaleRadioButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    private WebElement letsShopButton;
    @AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
    private WebElement toastMessage;

    public FormPage(AndroidDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }


    public void setupActivity() {
//        ((JavascriptExecutor) driver).executeScript("mobile:startActivity", ImmutableMap.of("intent", "com.androidsample.generalstore/com.androidsample.generalstore.SplashActivity"));
        ((JavascriptExecutor) driver).executeScript(
                "mobile:startActivity",
                ImmutableMap.of(
                        "package", "com.androidsample.generalstore",
                        "activity", "com.androidsample.generalstore.SplashActivity"
                )
        );

    }

    public void selectCountry(String countryName) {
        countryDropdown.click();
        WebElement country=scrollUptoUsingUIAutomator2(countryName);
        country.click();

    }

    public void enterName(String name){
        nameField.sendKeys(name);
    }

    public WebElement getGenderRadioButton(String gender) {
        // Gender should be "Male" or "Female"
        String xpath = "//android.widget.RadioButton[@text='" + gender + "']";
        return driver.findElement(By.xpath(xpath));
    }

    public void selectGender(String gender) {
        WebElement radioButton = getGenderRadioButton(gender);
        if (!radioButton.getAttribute("checked").equals("true")) {
            radioButton.click();
        }
    }

    public Map<String, Object> clickShopBtn() {
        letsShopButton.click();
        Map<String, Object> result = new HashMap<>();
        try {
            String toast = toastMessage.getAttribute("text");
            result.put("toast", toast);
        } catch (Exception e) {
            result.put("page", new ProductListPage(driver));
        }
        return result;
    }


    public WebElement scrollUptoUsingUIAutomator2(String countryName) {
        WebElement targetElement = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + countryName + "\"));"
                )
        );
        return targetElement; // Return the element so caller can interact
    }
}
