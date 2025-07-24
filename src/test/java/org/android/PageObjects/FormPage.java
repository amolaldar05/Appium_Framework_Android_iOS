package org.android.PageObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class FormPage {
    @AndroidFindBy(id = "com.androidsample.generalstore:id/spinnerCountry")
    public WebElement countryDropdown;
    @AndroidFindBy(className = "android.widget.EditText")
    public WebElement nameField;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/radioFemale")
    public WebElement femaleRadioButton;
    @AndroidFindBy(id = "com.androidsample.generalstore:id/btnLetsShop")
    public WebElement letsShopButton;
    @AndroidFindBy(xpath = "(//android.widget.Toast)[1]")
    public WebElement toastMessage;

    public FormPage(AndroidDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    public void selectCountry(String countryName) {
        countryDropdown.click();

    }
    public void fillForm(String countryName, String userName) {
        countryDropdown.click();
        // Scrolling and clicking to be done in test using driver
        nameField.sendKeys(userName);
        femaleRadioButton.click();
        letsShopButton.click();
    }
}
