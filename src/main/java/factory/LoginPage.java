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

    private WebDriver webDriver;

    private WebDriverWait wait;

    public static final String LOGIN_PAGE_URL = "http://training.skillo-bg.com:4200/users/login";

    @FindBy(id = "defaultLoginFormUsername")
    private WebElement loginUsernameInput;
    @FindBy(id = "defaultLoginFormPassword")
    private WebElement loginPasswordInput;
    @FindBy(xpath = "//input[@formcontrolname='rememberMe']")
    private WebElement rememberMeCheckbox;
    @FindBy(xpath = "//a[@href='/users/register']")
    private WebElement registerLink;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, Duration.ofSeconds(10));

        PageFactory.initElements(this.webDriver, this);
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

    public String getUsernameInputValue() {
        wait.until(ExpectedConditions.visibilityOf(loginUsernameInput));
        return loginUsernameInput.getAttribute("value");
    }

    public String getPasswordInputValue() {
        wait.until(ExpectedConditions.visibilityOf(loginPasswordInput));
        return loginPasswordInput.getAttribute("value");
    }

    public void clickOnSignInButton() {
        wait.until(ExpectedConditions.visibilityOf(signInButton));
        signInButton.click();
    }

    public void clickOnRegisterLink() {
        wait.until(ExpectedConditions.visibilityOf(registerLink));
        registerLink.click();
    }

    public void navigateTo() {
        this.webDriver.get(LOGIN_PAGE_URL);
    }

    public void selectRememberMeCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(rememberMeCheckbox));
        if (!rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }

    public void deSelectRememberMeCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(rememberMeCheckbox));
        if (rememberMeCheckbox.isSelected()) {
            rememberMeCheckbox.click();
        }
    }

    public boolean isRememberMeCheckboxSelected() {
        return rememberMeCheckbox.isSelected();
    }

    private void setInputValue(WebElement inputElement, String value) {
        wait.until(ExpectedConditions.visibilityOf(inputElement));
        inputElement.sendKeys(value);
    }
}
