package org.utils.pageObjects.android;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.utils.actions.android.AndroidActions;
import org.utils.helpers.LoggerUtil;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class FormPage extends AndroidActions {
    private static final Logger log = LoggerUtil.getLogger(FormPage.class);

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

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    private WebElement toastMessage;

    AndroidDriver driver;

    public FormPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("Initialized FormPage with driver and elements");
    }

    public void setupActivity() {
        log.info("Launching SplashActivity via mobile:startActivity");
        ((JavascriptExecutor) driver).executeScript(
                "mobile:startActivity",
                ImmutableMap.of(
                        "package", "com.androidsample.generalstore",
                        "activity", "com.androidsample.generalstore.SplashActivity"
                )
        );
    }

    public void selectCountry(String countryName) {
        log.info("Selecting country: {}", countryName);
        countryDropdown.click();
        WebElement country = scrollAndClickUptoUsingUIAutomator2(countryName);
        country.click();
        log.debug("Country selected: {}", countryName);
    }

    public void enterName(String name) {
        log.info("Entering name: {}", name);
        nameField.sendKeys(name);
    }

    public WebElement getGenderRadioButton(String gender) {
        log.debug("Getting gender radio button for: {}", gender);
        String xpath = "//android.widget.RadioButton[@text='" + gender + "']";
        return driver.findElement(By.xpath(xpath));
    }

    public void selectGender(String gender) {
        WebElement radioButton = getGenderRadioButton(gender);
        log.info("Selecting gender: {}", gender);
        if (!radioButton.getAttribute("checked").equals("true")) {
            radioButton.click();
            log.debug("Gender selected: {}", gender);
        } else {
            log.debug("Gender already selected: {}", gender);
        }
    }

    public Map<String, Object> clickShopBtn() {
        log.info("Clicking 'Let's Shop' button");
        letsShopButton.click();
        log.info("Clicked 'Let's Shop' button");
        Map<String, Object> result = new HashMap<>();
        try {
//            wait.until(ExpectedConditions.visibilityOf(toastMessage));
//            String toastMsg=driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("text");

            String toastMsg = toastMessage.getAttribute("text");
            log.warn("Toast displayed: {}", toastMsg);
            result.put("toast", toastMsg);
        } catch (Exception e) {
            log.info("No toast found, navigating to ProductListPage");
            result.put("page", new ProductListPage(driver));
        }
        return result;
    }
}
