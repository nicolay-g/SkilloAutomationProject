package factory;

import abstraction.AuthForm;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends AuthForm {


    protected WebDriver webDriver;

    protected WebDriverWait wait;

    public static final String LOGIN_PAGE_URL = "http://training.skillo-bg.com:4200/users/login";

    @FindBy(id = "defaultLoginFormUsername")
    WebElement loginUsernameInput;
    @FindBy(id = "defaultLoginFormPassword")
    WebElement loginPasswordInput;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(webDriver, this);
    }

    @Override
    public boolean isUrlLoaded() {
        return wait.until(ExpectedConditions.urlToBe(LOGIN_PAGE_URL));
    }

    public void setPassword(String value) {
        setInputValue(loginPasswordInput, value);
    }

    public void setUsername(String value) {
        setInputValue(loginUsernameInput, value);
    }

    public void clickOnSignInButton() {
        wait.until(ExpectedConditions.visibilityOf(signInButton));
        signInButton.click();
    }

    private void setInputValue(WebElement inputElement, String value) {
        wait.until(ExpectedConditions.visibilityOf(inputElement));
        inputElement.sendKeys(value);
    }

    public void navigateTo() {
        this.webDriver.get(LOGIN_PAGE_URL);
    }
}
