package org.android.PageObjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.android.utils.AndroidActions;
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


    public void selectCountry(String countryName) {
        countryDropdown.click();
        WebElement country=scrollUptoUsingUIAutomator2(countryName);
        country.click();

    }

    public void enterName(String name){
        nameField.sendKeys(name);
    }
    public void selectGender(){
       if (!maleRadioButton.isSelected()){
    maleRadioButton.click();
    }else{
    femaleRadioButton.click();
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
